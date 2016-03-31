package com.polling;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.polling.global.PollingGlobal;
import com.polling.helper.PollingManagerHelper;
import com.polling.util.WakeUpUtil;

/**
 * 
 * @author tangjiabing
 * 
 * @see ��Դʱ�䣺2016��03��31��
 * 
 *      �ǵø��Ҹ�starŶ~
 * 
 */
public class ClickBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent clickIntent) {

		WakeUpUtil wakeUpUtil = WakeUpUtil.getInstance();
		if (wakeUpUtil != null)
			wakeUpUtil.release();

		PollingManagerHelper helper = (PollingManagerHelper) clickIntent
				.getSerializableExtra(PollingGlobal.KEY_POLLING_MANAGER_HELPER);
		helper.clickedNotification();

		Intent realIntent = clickIntent.getParcelableExtra("realIntent");
		context.startActivity(realIntent);

	}

}
