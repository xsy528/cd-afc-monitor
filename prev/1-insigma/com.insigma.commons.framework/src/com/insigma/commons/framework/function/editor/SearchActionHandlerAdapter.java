package com.insigma.commons.framework.function.editor;

import java.util.List;

import com.insigma.commons.framework.ActionHandlerAdapter;
import com.insigma.commons.framework.Request;
import com.insigma.commons.framework.Response;
import com.insigma.commons.framework.function.search.SearchRequest;
import com.insigma.commons.framework.function.search.SearchResponse;
import com.insigma.commons.framework.function.table.TableGrid;

public class SearchActionHandlerAdapter<T> extends ActionHandlerAdapter {

	protected Object getSelection(Request request) {
		if (request instanceof SearchRequest) {
			SearchRequest<T> req = (SearchRequest) request;
			if (req.getSelection().size() > 0) {
				Object item = req.getSelection().get(0);
				return item;
			}
		}
		return null;
	}

	protected List<T> getSeletions(Request request) {
		if (request instanceof SearchRequest) {
			SearchRequest<T> req = (SearchRequest<T>) request;
			if (req.getSelectionData().size() > 0) {
				List<T> sectionForm = req.getSelectionData();
				return sectionForm;
			}
		}
		return null;
	}

	public final Response perform(Request request) {
		SearchRequest<T> req = (SearchRequest<T>) request;
		SearchResponse resp = new SearchResponse();

		TableGrid grid = new TableGrid();
		int pageNum = 0;
		grid.setPageSize(req.getPageSize());

		grid.setCurrentPage(req.getPage());
		pageNum = req.getPage() - 1;

		req.setPage(pageNum);
		resp.setData(grid);

		if (before(req, resp) == 0) {
			try {
				logger.debug("查询第" + req.getPage() + "页");
				perform(req, resp);
				if (resp.getData().getContent() == null || resp.getData().getContent().size() == 0) {
					grid.setCurrentPage(1);
					grid.setTotalSize(0);
					grid.setPageSize(req.getPageSize());
				}
			} catch (Exception e) {
				resp.addError("查询错误。");
				logger.error("查询错误", e);
			}
		}
		return resp;
	}

	@Override
	public final boolean prepare(Request request) {
		if (request instanceof SearchRequest) {
			return prepare((SearchRequest<T>) request);
		}
		return false;
	}

	public boolean prepare(SearchRequest<T> request) {
		return true;
	}

	public void perform(SearchRequest<T> request, SearchResponse response) {
	};

	public int before(SearchRequest<T> request, SearchResponse response) {
		return 0;
	}
}
