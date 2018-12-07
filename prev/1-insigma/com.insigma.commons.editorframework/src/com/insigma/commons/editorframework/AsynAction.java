/**
 * 
 */
package com.insigma.commons.editorframework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.swt.widgets.Display;

import com.insigma.commons.exception.ApplicationException;
import com.insigma.commons.ui.MessageForm;

/**
 * @author DingLuofeng
 *
 */
public class AsynAction<Paramter, Result> extends Action {

	protected Log logger = LogFactory.getLog(getClass());

	private static final ExecutorService execService = Executors.newCachedThreadPool();;

	public AsynAction(String name) {
		this(name, null);
	}

	public AsynAction(String name, IActionHandler handler) {
		super(name, handler);
	}

	@SuppressWarnings("unchecked")
	protected void asynExecute(BackgroundWorkor<Paramter> backgroundWorkor, IActionCallback<Result> actionCallback) {
		FutureTask<Object> futureTask = new FutureTask<Object>(backgroundWorkor);
		execService.execute(futureTask);
		try {
			Object result = futureTask.get();
			if (result instanceof Exception) {
				if (actionCallback == null) {
					Exception e = (Exception) result;
					logger.error("执行后台线程失败", e);
					throw new ApplicationException("执行后台线程失败。", e);
				}
				errorBackCall(actionCallback, (Exception) result);
			} else {
				callBack(actionCallback, (Result) result);
			}
		} catch (Exception e) {
			if (actionCallback == null) {
				logger.error("执行后台线程失败", e);
				throw new ApplicationException("执行后台线程失败。", e);
			}
			errorBackCall(actionCallback, e);
		}
	}

	protected void asynExecute(BackgroundWorkor<Paramter> backgroundWorkor) throws Exception {
		FutureTask<Object> futureTask = new FutureTask<Object>(backgroundWorkor);
		execService.execute(futureTask);
		try {
			Object result = futureTask.get();
			if (result instanceof Exception) {
				throw (Exception) result;
			}
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("rawtypes")
	private void errorBackCall(final IActionCallback actionCallback, final Exception ex) {
		if (actionCallback == null) {
			logger.error("执行后台线程失败", ex);
		} else {
			if (Display.getDefault().getThread().getId() == Thread.currentThread().getId()) {
				try {
					actionCallback.error(ex);
				} catch (Exception e) {
					MessageForm.Message(e);
				}
			} else {
				Display.getDefault().asyncExec(new Runnable() {
					public void run() {
						try {
							actionCallback.error(ex);
						} catch (Exception e) {
							MessageForm.Message(e);
						}
					}
				});
			}
		}
	}

	private void callBack(final IActionCallback<Result> actionCallback, final Result result) {
		if (actionCallback == null) {
			return;
		}
		if (Display.getDefault().getThread().getId() == Thread.currentThread().getId()) {
			try {
				actionCallback.callback(result);
			} catch (Exception e) {
				actionCallback.error(e);
			}
		} else {
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					try {
						actionCallback.callback(result);
					} catch (Exception e) {
						actionCallback.error(e);
					}
				}
			});
		}
	}

	public abstract class BackgroundWorkor<V> implements Callable<Object> {

		private V paramter;

		public BackgroundWorkor(V paramter) {
			super();
			this.paramter = paramter;
		}

		public Object call() throws Exception {
			try {
				return execute(paramter);
			} catch (Exception e) {
				return e;
			}
		}

		public abstract Object execute(V paramter);

	}

}
