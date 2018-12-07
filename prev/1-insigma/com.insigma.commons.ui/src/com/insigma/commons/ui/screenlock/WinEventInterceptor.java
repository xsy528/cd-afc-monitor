/* 
 * 日期：2012-11-16
 *  
 * 版权所有：浙江浙大网新众合轨道交通工程有限公司
 */
package com.insigma.commons.ui.screenlock;

import org.eclipse.swt.internal.win32.OS;
import org.sf.feeling.swt.win32.extension.Win32;
import org.sf.feeling.swt.win32.extension.hook.Keyboard_LLHook;
import org.sf.feeling.swt.win32.extension.hook.Mouse_LLHook;
import org.sf.feeling.swt.win32.extension.hook.data.Keyboard_LLHookData;
import org.sf.feeling.swt.win32.extension.hook.data.Mouse_LLHookData;
import org.sf.feeling.swt.win32.extension.hook.interceptor.InterceptorFlag;
import org.sf.feeling.swt.win32.extension.hook.interceptor.Keyboard_LLHookInterceptor;
import org.sf.feeling.swt.win32.extension.hook.interceptor.Mouse_LLHookInterceptor;
import org.sf.feeling.swt.win32.extension.registry.RegistryKey;
import org.sf.feeling.swt.win32.extension.registry.RegistryValue;
import org.sf.feeling.swt.win32.extension.registry.RootKey;
import org.sf.feeling.swt.win32.extension.registry.ValueType;
import org.sf.feeling.swt.win32.extension.shell.ApplicationBar;

/**
 * Ticket:屏蔽系统快捷键，供锁屏使用(添加屏蔽任务管理器，禁用鼠标事件,隐藏任务栏)
 * 
 * @author 郑淦
 */
public class WinEventInterceptor {
	private static boolean altPressed = false;

	private static Keyboard_LLHookInterceptor keyboard_LLHookInterceptor;

	private static Mouse_LLHookInterceptor mouse_LLHookInterceptor;

	static {
		keyboard_LLHookInterceptor = new Keyboard_LLHookInterceptor() {
			@Override
			public InterceptorFlag intercept(Keyboard_LLHookData hookData) {
				int vkCode = hookData.vkCode();
				if (vkCode == OS.VK_LMENU || vkCode == OS.VK_RMENU) {
					if (hookData.getFlags() == 0x80) {
						altPressed = false;
					} else
						altPressed = true;
				}
				boolean isCtrlPressed = OS.GetKeyState(OS.VK_CONTROL) < 0 ? true : false;
				// 屏蔽windows键
				if (vkCode == 91 || vkCode == 92) {
					return InterceptorFlag.FALSE;
				}
				// 屏蔽ALT+ESC
				if (altPressed && vkCode == OS.VK_ESCAPE) {
					return InterceptorFlag.FALSE;
				}
				// 屏蔽CTRL+ESC
				if (isCtrlPressed && vkCode == OS.VK_ESCAPE) {
					return InterceptorFlag.FALSE;
				}
				// 屏蔽ALT+TAB
				if (altPressed && vkCode == OS.VK_TAB) {
					return InterceptorFlag.FALSE;
				}
				// 屏蔽ALT+F4
				if (altPressed && vkCode == OS.VK_F4) {
					return InterceptorFlag.FALSE;
				}
				return InterceptorFlag.TRUE;
			}
		};
		mouse_LLHookInterceptor = new Mouse_LLHookInterceptor() {
			@Override
			public InterceptorFlag intercept(Mouse_LLHookData hookData) {
				return InterceptorFlag.FALSE;
			}
		};
	}

	/**
	 * 鼠标恢复
	 */
	public static void resetMouse() {
		if (Mouse_LLHook.isInstalled())
			Mouse_LLHook.unInstallHook();
	}

	/**
	 * 将鼠标设为不可用
	 */
	public static void setMouseDisable() {
		if (!Mouse_LLHook.isInstalled()) {
			Mouse_LLHook.addHookInterceptor(mouse_LLHookInterceptor);
			Mouse_LLHook.installHook();
		}
	}

	/**
	 * 自定义添加禁用哪些鼠标事件
	 * 
	 * @param mouseEvents
	 */
	public static void setMouseDisable(final MOUSE_EVENT... mouseEvents) {
		if (!Mouse_LLHook.isInstalled()) {
			Mouse_LLHook.addHookInterceptor(new Mouse_LLHookInterceptor() {
				@Override
				public InterceptorFlag intercept(Mouse_LLHookData hookData) {
					if (mouseEvents == null) {
						return InterceptorFlag.FALSE;
					}
					MOUSE_EVENT event = null;
					if (hookData.getWParam() == 512) {
						event = MOUSE_EVENT.MOVE;
					} else if (hookData.getWParam() == 513 || hookData.getWParam() == 514) {
						event = MOUSE_EVENT.LEFT_PRESS;
					} else if (hookData.getWParam() == 516 || hookData.getWParam() == 517) {
						event = MOUSE_EVENT.RIGHT_PRESS;
					} else if (hookData.getWParam() == 519 || hookData.getWParam() == 520) {
						event = MOUSE_EVENT.MIDDLE_PRESS;
					} else if (hookData.getWParam() == 522 && hookData.getMouseData() > 0) {
						event = MOUSE_EVENT.WHEEL_UP;
					} else if (hookData.getWParam() == 522 && hookData.getMouseData() < 0) {
						event = MOUSE_EVENT.WHEEL_DOWN;
					}
					for (MOUSE_EVENT mouse_event : mouseEvents) {
						if (mouse_event == event) {
							return InterceptorFlag.FALSE;
						}
					}
					return InterceptorFlag.TRUE;
				}
			});
			Mouse_LLHook.installHook();
		}
	}

	/**
	 * 键盘恢复
	 */
	public static void resetKeyboard() {
		if (Keyboard_LLHook.isInstalled())
			Keyboard_LLHook.unInstallHook();
	}

	/**
	 * 禁用键盘
	 */
	public static void setKeyDisable() {
		if (!Keyboard_LLHook.isInstalled()) {
			Keyboard_LLHook.addHookInterceptor(new Keyboard_LLHookInterceptor() {
				@Override
				public InterceptorFlag intercept(Keyboard_LLHookData hookData) {
					return InterceptorFlag.FALSE;
				}
			});
			Keyboard_LLHook.installHook();
		}
	}

	/**
	 * 屏蔽键盘（非组合键）,例如vkCode是49,50，则屏蔽的键盘的1和2键；具体键盘对应请查询ascII码表
	 * 
	 * @param vkCode
	 *            (要屏蔽的键，非组合键)
	 */
	public static void setKeyDisable(final int... vkCode) {
		if (!Keyboard_LLHook.isInstalled()) {
			Keyboard_LLHook.addHookInterceptor(new Keyboard_LLHookInterceptor() {
				@Override
				public InterceptorFlag intercept(Keyboard_LLHookData hookData) {
					if (vkCode == null)
						return InterceptorFlag.FALSE;
					for (int code : vkCode) {
						if (code == hookData.vkCode())
							return InterceptorFlag.FALSE;
					}
					return InterceptorFlag.TRUE;
				}
			});
			Keyboard_LLHook.installHook();
		}
	}

	/**
	 * 禁用几个常用快捷键，windows键，ALT+ESC，CTRL+ESC，ALT+TAB，ALT+F4
	 */
	public static void setCommonUseKeyDisable() {
		if (!Keyboard_LLHook.isInstalled()) {
			Keyboard_LLHook.addHookInterceptor(keyboard_LLHookInterceptor);
			Keyboard_LLHook.installHook();
		}
	}

	/**
	 * 任务管理器恢复
	 */
	public static void resetTaskmgr() {
		RootKey currentUser = RootKey.HKEY_CURRENT_USER;
		RegistryKey key = new RegistryKey(currentUser,
				"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System");
		if (!key.exists()) {
			key.create();
		}
		RegistryValue value = new RegistryValue();
		value.setType(ValueType.REG_DWORD);
		value.setData(0);
		value.setName("DisableTaskmgr");
		key.setValue(value);
	}

	/**
	 * 禁用任务管理器
	 */
	public static void setTaskmgrDisable() {
		RootKey currentUser = RootKey.HKEY_CURRENT_USER;
		RegistryKey key = new RegistryKey(currentUser,
				"Software\\Microsoft\\Windows\\CurrentVersion\\Policies\\System");
		if (!key.exists()) {
			key.create();
		}
		RegistryValue value = new RegistryValue();
		value.setType(ValueType.REG_DWORD);
		value.setData(1);
		value.setName("DisableTaskmgr");
		key.setValue(value);
	}

	enum MOUSE_EVENT {
		MOVE, LEFT_PRESS, RIGHT_PRESS, MIDDLE_PRESS, WHEEL_UP, WHEEL_DOWN
	}

	/**
	 * 设置快捷键，按下此快捷键，程序的几个常用快捷键，windows键，ALT+ESC，CTRL+ESC，ALT+TAB，ALT+F4将重新被禁用
	 * 
	 * @param code
	 *            快捷键的编码，例如F10可设为121,即OS.VK_F10
	 */
	public static void restartHook(int code) {
		if (!Keyboard_LLHook.isInstalled()) {
			Keyboard_LLHook.addHookInterceptor(new Keyboard_LLHookInterceptor() {
				@Override
				public InterceptorFlag intercept(Keyboard_LLHookData hookData) {
					int vkCode = hookData.vkCode();
					if (vkCode == OS.VK_F10) {
						Keyboard_LLHook.unInstallHook();
						setCommonUseKeyDisable();
						setTaskmgrDisable();
					}
					return InterceptorFlag.TRUE;
				}
			});
			Keyboard_LLHook.installHook();
		}
	}

	/**
	 * 隐藏任务栏
	 * 
	 * @param handle
	 *            句柄,如shell.handle
	 */
	public static void setTaskBarHide(int handle) {
		ApplicationBar.setAppBarState(handle, Win32.STATE_AUTOHIDE_ALWAYSONTOP);
	}

	/**
	 * 恢复任务栏
	 * 
	 * @param handle
	 *            句柄,如shell.handle
	 */
	public static void resetTaskBar(int handle) {
		ApplicationBar.setAppBarState(handle, Win32.STATE_ALWAYSONTOP);
	}
}
