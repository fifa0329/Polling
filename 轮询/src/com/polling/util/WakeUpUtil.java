package com.polling.util;

import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.content.Context;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

/**
 * 
 * @author tangjiabing
 * 
 * @see ��Դʱ�䣺2016��03��31��
 * 
 *      �ǵø��Ҹ�starŶ~
 * 
 */
public class WakeUpUtil {

	private static WakeUpUtil mInstance = null;
	private PowerManager mPowerManager = null;
	private KeyguardLock mKeyguardLock = null;
	private WakeLock mWakeLock = null;
	private boolean mIsCallWakeUp = false;

	private WakeUpUtil(Context context) {
		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		mKeyguardLock = keyguardManager.newKeyguardLock("logcat");
		mPowerManager = (PowerManager) context
				.getSystemService(Context.POWER_SERVICE);
		// ���һ��������LogCat���õ�Tag
		mWakeLock = mPowerManager.newWakeLock(
				PowerManager.ACQUIRE_CAUSES_WAKEUP
						| PowerManager.SCREEN_DIM_WAKE_LOCK, "logcat");
	}

	public static WakeUpUtil getInstance(Context context) {
		if (mInstance != null)
			mInstance.release(); // �������ͷ�֮ǰ�ģ�����ڶ�����Ч
		mInstance = new WakeUpUtil(context);
		return mInstance;
	}

	public static WakeUpUtil getInstance() {
		return mInstance;
	}

	public boolean isScreenOn() {
		return mPowerManager.isScreenOn();
	}

	public void wakeUpAndUnlock() {
		mKeyguardLock.disableKeyguard(); // ����
		mWakeLock.acquire(); // ������Ļ
		mIsCallWakeUp = true;
	}

	public void release() {
		if (mIsCallWakeUp == true) {
			mKeyguardLock.reenableKeyguard(); // ������Ҫ������ɶԳ��֣�����ڶ����޷�����
			mWakeLock.release(); // �ͷţ�֮ǰ������ù�acquire��������������쳣
		}
		mInstance = null;
		mPowerManager = null;
		mKeyguardLock = null;
		mWakeLock = null;
	}

}
