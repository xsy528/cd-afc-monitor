/* 
 * 日期：Aug 13, 2009
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.task;

import java.util.concurrent.LinkedBlockingQueue;

import org.eclipse.swt.widgets.Display;

import com.insigma.commons.thread.EnhancedThread;

public class TaskManager extends EnhancedThread {

	protected LinkedBlockingQueue<Task<?>> tasks;

	public TaskManager() {
		tasks = new LinkedBlockingQueue<Task<?>>();
	}

	public void submit(Task<?> task) {
		tasks.offer(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		while (true) {
			try {
				final Task<?> task = tasks.take();
				final Object result = task.execute();
				final TaskCallback callback = task.getCallback();
				if (callback != null) {
					Display.getDefault().asyncExec(new Runnable() {
						public void run() {
							callback.finish(result);
						}
					});
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
