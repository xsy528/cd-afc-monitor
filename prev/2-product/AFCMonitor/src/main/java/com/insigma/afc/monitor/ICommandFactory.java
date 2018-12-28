package com.insigma.afc.monitor;

import java.util.List;

import com.insigma.afc.dic.AFCCmdLogType;
import com.insigma.afc.topology.MetroDevice;
import com.insigma.commons.application.IService;
import com.insigma.commons.dic.IDefinition;
import com.insigma.commons.framework.function.form.Form;

public interface ICommandFactory extends IService {

	public static interface ICommandFilter {

		boolean validate(Command command, MetroDevice node);
	}

	public static class Command {
		int id;
		String text;
		IDefinition definition;
		Form form;
		ICommandFilter filter;
		short cmdType = AFCCmdLogType.LOG_DEVICE_CMD.shortValue();
		/**
		 * Radio Button是否被设置为选中
		 */
		boolean selected = false;

		public Command(int id, String text) {
			this.id = id;
			this.text = text;
		}

		public Command(int id, String text, IDefinition definition) {
			this(id, text);
			this.definition = definition;
		}

		public Command(int id, String text, Form form) {
			this(id, text);
			this.form = form;
		}

		public Command(int id, String text, Object object) {
			this(id, text);
			if (object instanceof Form) {
				this.form = (Form) object;
			} else {
				this.form = new Form(object);
			}
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public IDefinition getDefinition() {
			return definition;
		}

		public void setDefinition(IDefinition definition) {
			this.definition = definition;
		}

		public Form getForm() {
			return form;
		}

		public void setForm(Form form) {
			this.form = form;
		}

		public ICommandFilter getFilter() {
			return filter;
		}

		public void setFilter(ICommandFilter filter) {
			this.filter = filter;
		}

		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}

		public short getCmdType() {
			return cmdType;
		}

		public void setCmdType(short cmdType) {
			this.cmdType = cmdType;
		}

	}

	public List<Command> getCommands();
}
