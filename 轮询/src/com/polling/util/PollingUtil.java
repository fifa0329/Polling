package com.polling.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.polling.PollingService;
import com.polling.global.PollingGlobal;
import com.polling.helper.PollingManagerHelper;

/**
 * 
 * @author tangjiabing
 * 
 * @see ��Դʱ�䣺2016��03��31��
 * 
 *      �ǵø��Ҹ�starŶ~
 * 
 */
public class PollingUtil {

	private static boolean mIsStartPolling = false;

	public static void start(PollingManagerHelper helper, Context appContext,
			Bundle bundle, long triggerAtTime, long interval) {
		if (helper == null)
			throw new IllegalArgumentException(
					"PollingManagerHelper is not null");

		AlarmManager alarmManager = (AlarmManager) appContext
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(appContext, PollingService.class);
		intent.putExtra(PollingGlobal.KEY_POLLING_MANAGER_HELPER, helper);
		if (bundle != null)
			intent.putExtras(bundle);
		// helper��bundle��������֮�����ѯ�������ǲ��ᱻ�ı��

		// PendingIntent��������ڴ��������������飬���Կ����Ƕ�Intent�İ�װ��ͨ��ͨ��getActivity��
		// getBroadcast��getService���õ�PendingIntent��ʵ������ǰactivity������������������������intent��
		// �������ⲿִ�� pendingIntentʱ������intent�ġ���������PendingIntent�б����е�ǰApp��Context��
		// ʹ���ⲿApp������ͬ��ǰAppһ����ִ��PendingIntent��� Intent��������ִ��ʱ��ǰApp�Ѿ��������ˣ�
		// Ҳ��ͨ������PendingIntent���Context����ִ��Intent���������Ϊ�ӳ�ִ�е�Intent��

		// PendingIntent��flags������4��ֵ��
		// 1��FLAG_ONE_SHOT����ȡ��PendingIntentֻ��ʹ��һ�Σ���ʹ�ٴ�����������������getActivity��getBroadcast��getService��
		// ���»�ȡ����ʹ��PendingIntentҲ��ʧ�ܡ�
		// 2��FLAG_NO_CREATE�������������PendingIntent�Ѿ��������ˣ�Ȼ��ͷ���null�����Ǵ�������
		// 3��FLAG_CANCEL_CURRENT�����������PendingIntent�Ѿ����ڣ����ڲ����µ�PendingIntent֮ǰ����ȡ������ǰ�ġ�
		// �����ֻ����ı�Intent�еĶ������ݵĻ��������ʹ����ȥ�����µ�PendingIntent��ͨ��ȡ����ǰ��PendingIntent��ȷ��ʵ���ܹ�Ӧ���µĶ������ݡ�
		// 4��FLAG_UPDATE_CURRENT�����������PendingIntent�Ѿ����ڣ�����ά�������滻�µ�Intent�Ķ������ݡ���������ڴ���һ��ֻ�Ƕ������ݲ�ͬ��
		// Intentʱ������ʹ�������ҽ�����ǰPendingIntent��ʵ������Ӧ���µĶ������ݡ�

		PendingIntent pendingIntent = PendingIntent.getService(appContext,
				PollingGlobal.REQUEST_CODE, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		// ������4�ֿ��õ�Alarm���ͣ�
		// 1��RTC_WAKEUP����ָ����ʱ�份���豸��������PendingIntent��
		// 2��RTC����ָ����ʱ��㼤��PendingIntent�����ǲ��ỽ���豸��
		// 3��ELAPSED_REALTIME�������豸����֮�󾭹���ʱ�伤��PendingIntent�����ǲ��ỽ���豸��������ʱ������豸���ߵ�����ʱ�䡣
		// 4��ELAPSED_REALTIME_WAKEUP�����豸����������ָ����ʱ��֮�����豸�ͼ���PendingIntent

		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + triggerAtTime, interval,
				pendingIntent);

		mIsStartPolling = true;
	}

	public static void cancel(Context appContext) {
		AlarmManager alarmManager = (AlarmManager) appContext
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(appContext, PollingService.class);
		PendingIntent pendingIntent = PendingIntent.getService(appContext,
				PollingGlobal.REQUEST_CODE, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);

		mIsStartPolling = false;
	}

	public static boolean isStartPolling() {
		return mIsStartPolling;
	}

}
