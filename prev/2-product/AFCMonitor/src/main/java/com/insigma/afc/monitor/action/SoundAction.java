package com.insigma.afc.monitor.action;

import com.insigma.afc.monitor.thread.BeepThread;
import com.insigma.commons.editorframework.Action;
import com.insigma.commons.editorframework.ActionHandlerAdapter;
import com.insigma.commons.editorframework.IActionHandler;
import com.insigma.commons.ui.MessageForm;

public class SoundAction extends Action {

	private boolean soundEnable;

	private BeepThread beepThread;

	public boolean isSoundEnable() {
		return soundEnable;
	}

	public void setSoundEnable(boolean soundEnable) {
		this.soundEnable = soundEnable;
	}

	public SoundAction() {
		super("声音警报");
		setImage("/com/insigma/commons/editorframework/images/sound.png");
		IActionHandler handler = new ActionHandlerAdapter() {
			public void perform(Action action) {
				soundEnable = !soundEnable;
				if (soundEnable) {
					if (null != beepThread) {
						beepThread.setBeep(false);
					}
					setImage("/com/insigma/commons/editorframework/images/nosound.png");
					MessageForm.Message("声音警报关闭");
				} else {
					if (null != beepThread) {
						beepThread.setBeep(true);
					}
					setImage("/com/insigma/commons/editorframework/images/sound.png");
					MessageForm.Message("声音警报打开");
				}
			}
		};
		setHandler(handler);
	}

	public void setBeepThread(BeepThread beepThread) {
		this.beepThread = beepThread;
	}
}
