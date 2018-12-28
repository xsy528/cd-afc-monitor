package com.insigma.commons.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Ticket:
 * 
 * @author 华思远
 * 
 *         描述:
 */
public class CSVWriter {

	private Writer rawWriter;

	private PrintWriter pw;

	private char separator;

	private char quotechar;

	private char escapechar;

	private String lineEnd;

	/** The character used for escaping quotes. */
	public static final char DEFAULT_ESCAPE_CHARACTER = '"';

	/** The default separator to use if none is supplied to the constructor. */
	public static final char DEFAULT_SEPARATOR = ',';

	/**
	 * The default quote character to use if none is supplied to the
	 * constructor.
	 */
	public static final char DEFAULT_QUOTE_CHARACTER = '"';

	/** The quote constant to use when you wish to suppress all quoting. */
	public static final char NO_QUOTE_CHARACTER = '\u0000';

	/** The escape constant to use when you wish to suppress all escaping. */
	public static final char NO_ESCAPE_CHARACTER = '\u0000';

	/** Default line terminator uses platform encoding. */
	public static final String DEFAULT_LINE_END = "\n";

	private static final SimpleDateFormat TIMESTAMP_FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * Constructs CSVWriter using a comma for the separator.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 */
	public CSVWriter(Writer writer) {
		this(writer, DEFAULT_SEPARATOR);
	}

	/**
	 * Constructs CSVWriter with supplied separator.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries.
	 */
	public CSVWriter(Writer writer, char separator) {
		this(writer, separator, DEFAULT_QUOTE_CHARACTER);
	}

	/**
	 * Constructs CSVWriter with supplied separator and quote char.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 */
	public CSVWriter(Writer writer, char separator, char quotechar) {
		this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER);
	}

	/**
	 * Constructs CSVWriter with supplied separator and quote char.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escapechar
	 *            the character to use for escaping quotechars or escapechars
	 */
	public CSVWriter(Writer writer, char separator, char quotechar, char escapechar) {
		this(writer, separator, quotechar, escapechar, DEFAULT_LINE_END);
	}

	/**
	 * Constructs CSVWriter with supplied separator and quote char.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param lineEnd
	 *            the line feed terminator to use
	 */
	public CSVWriter(Writer writer, char separator, char quotechar, String lineEnd) {
		this(writer, separator, quotechar, DEFAULT_ESCAPE_CHARACTER, lineEnd);
	}

	/**
	 * Constructs CSVWriter with supplied separator, quote char, escape char and
	 * line ending.
	 * 
	 * @param writer
	 *            the writer to an underlying CSV source.
	 * @param separator
	 *            the delimiter to use for separating entries
	 * @param quotechar
	 *            the character to use for quoted elements
	 * @param escapechar
	 *            the character to use for escaping quotechars or escapechars
	 * @param lineEnd
	 *            the line feed terminator to use
	 */
	public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
		rawWriter = writer;
		pw = new PrintWriter(writer);
		this.separator = separator;
		this.quotechar = quotechar;
		this.escapechar = escapechar;
		this.lineEnd = lineEnd;
	}

	/**
	 * Writes the entire list to a CSV file. The list is assumed to be a
	 * String[]
	 * 
	 * @param allLines
	 *            a List of String[], with each String[] representing a line of
	 *            the file.
	 */
	@SuppressWarnings("unchecked")
	public void writeAll(List allLines) {

		for (Iterator iter = allLines.iterator(); iter.hasNext();) {
			String[] nextLine = (String[]) iter.next();
			writeNext(nextLine);
		}

	}

	/**
	 * @param metadata
	 * ResultSet 的源数据
	 * @param colNameMap
	 * 列名的映射表其中列名必须大写
	 * @throws SQLException
	 */
	private void writeColumnNames(ResultSetMetaData metadata, Map<String, String> colNameMap) throws SQLException {

		int columnCount = metadata.getColumnCount();

		String[] nextLine = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			nextLine[i] = metadata.getColumnName(i + 1).toUpperCase();
			if (colNameMap != null && colNameMap.get(nextLine[i]) != null) {
				nextLine[i] = colNameMap.get(nextLine[i]);
			}
		}
		writeNext(nextLine);
	}

	/**
	 * @param rs
	 * 需要导出的ResultSet
	 * @param includeColumnNames
	 * 是否包括列名
	 * @param colNameMap
	 * 列别名列名必须大写
	 * @param colValPattern
	 * 列值的特殊处理列名必须大写
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	public long writeAll(ResultSet rs, boolean includeColumnNames, Map<String, String> colNameMap,
			Map<String, ColumnValuePattern> colValPattern) throws SQLException, IOException {
		ResultSetMetaData metadata = rs.getMetaData();

		if (includeColumnNames) {
			writeColumnNames(metadata, colNameMap);
		}

		int columnCount = metadata.getColumnCount();

		long rowCount = 0;

		while (rs.next()) {

			rowCount++;

			String[] nextLine = new String[columnCount];

			for (int i = 0; i < columnCount; i++) {
				String colName = metadata.getColumnName(i + 1);
				String upperColName = colName.toUpperCase();
				if (colValPattern != null && colValPattern.get(upperColName) != null) {
					ColumnValuePattern c = colValPattern.get(upperColName);
					nextLine[i] = c.computeColumnValue(rs.getObject(colName));
				} else {
					nextLine[i] = getColumnValue(rs, metadata.getColumnType(i + 1), i + 1);
				}
			}

			writeNext(nextLine);
		}
		return rowCount;
	}

	/**
	 * Writes the entire ResultSet to a CSV file.
	 * 
	 * The caller is responsible for closing the ResultSet.
	 * 
	 * @param rs
	 *            the recordset to write
	 * @param includeColumnNames
	 *            true if you want column names in the output, false otherwise
	 * 
	 */
	public long writeAll(java.sql.ResultSet rs, boolean includeColumnNames) throws SQLException, IOException {

		ResultSetMetaData metadata = rs.getMetaData();

		if (includeColumnNames) {
			writeColumnNames(metadata, null);
		}

		int columnCount = metadata.getColumnCount();

		long rowCount = 0;

		while (rs.next()) {

			rowCount++;

			String[] nextLine = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				nextLine[i] = getColumnValue(rs, metadata.getColumnType(i + 1), i + 1);
			}

			writeNext(nextLine);
		}
		return rowCount;
	}

	private static String getColumnValue(ResultSet rs, int colType, int colIndex) throws SQLException, IOException {

		String value = "";

		switch (colType) {
		case Types.BIT:
			Object bit = rs.getObject(colIndex);
			if (bit != null) {
				value = String.valueOf(bit);
			}
			break;
		case Types.BOOLEAN:
			boolean b = rs.getBoolean(colIndex);
			if (!rs.wasNull()) {
				value = Boolean.valueOf(b).toString();
			}
			break;
		case Types.CLOB:
			Clob c = rs.getClob(colIndex);
			if (c != null) {
				value = read(c);
			}
			break;
		case Types.BIGINT:
		case Types.DECIMAL:
		case Types.DOUBLE:
		case Types.FLOAT:
		case Types.REAL:
		case Types.NUMERIC:
			BigDecimal bd = rs.getBigDecimal(colIndex);
			if (bd != null) {
				value = "" + bd.doubleValue();
			}
			break;
		case Types.INTEGER:
		case Types.TINYINT:
		case Types.SMALLINT:
			int intValue = rs.getInt(colIndex);
			if (!rs.wasNull()) {
				value = "" + intValue;
			}
			break;
		case Types.JAVA_OBJECT:
			Object obj = rs.getObject(colIndex);
			if (obj != null) {
				value = String.valueOf(obj);
			}
			break;
		case Types.DATE:
			java.sql.Date date = rs.getDate(colIndex);
			if (date != null) {
				value = DATE_FORMATTER.format(date);
				;
			}
			break;
		case Types.TIME:
			Time t = rs.getTime(colIndex);
			if (t != null) {
				value = t.toString();
			}
			break;
		case Types.TIMESTAMP:
			Timestamp tstamp = rs.getTimestamp(colIndex);
			if (tstamp != null) {
				value = TIMESTAMP_FORMATTER.format(tstamp);
			}
			break;
		case Types.LONGVARCHAR:
		case Types.VARCHAR:
		case Types.CHAR:
			value = rs.getString(colIndex);
			break;
		default:
			value = "";
		}

		if (value == null) {
			value = "";
		}

		return value;

	}

	private static String read(Clob c) throws SQLException, IOException {
		StringBuffer sb = new StringBuffer((int) c.length());
		Reader r = c.getCharacterStream();
		char[] cbuf = new char[2048];
		int n = 0;
		while ((n = r.read(cbuf, 0, cbuf.length)) != -1) {
			if (n > 0) {
				sb.append(cbuf, 0, n);
			}
		}
		return sb.toString();
	}

	/**
	 * Writes the next line to the file.
	 * 
	 * @param nextLine
	 *            a string array with each comma-separated element as a separate
	 *            entry.
	 */
	public void writeNext(String[] nextLine) {

		if (nextLine == null) {
			return;
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < nextLine.length; i++) {

			if (i != 0) {
				sb.append(separator);
			}

			String nextElement = nextLine[i];
			if (nextElement == null) {
				continue;
			}
			if (quotechar != NO_QUOTE_CHARACTER) {
				sb.append(quotechar);
			}
			for (int j = 0; j < nextElement.length(); j++) {
				char nextChar = nextElement.charAt(j);
				if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
					sb.append(escapechar).append(nextChar);
				} else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
					sb.append(escapechar).append(nextChar);
				} else {
					sb.append(nextChar);
				}
			}
			if (quotechar != NO_QUOTE_CHARACTER) {
				//导出时按文本格式导出
				sb.append(quotechar + "\t");
			}
		}

		sb.append(lineEnd);
		pw.write(sb.toString());

	}

	/**
	 * Flush underlying stream to writer.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 */
	public void flush() throws IOException {

		pw.flush();

	}

	/**
	 * Close the underlying stream writer flushing any buffered content.
	 * 
	 * @throws IOException
	 *             if bad things happen
	 * 
	 */
	public void close() throws IOException {
		pw.flush();
		pw.close();
		rawWriter.close();
	}

}
