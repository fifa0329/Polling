package com.polling.helper;

import java.io.Serializable;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;

import com.polling.bean.SimpleNotifyBean;

/**
 * 
 * @author tangjiabing
 * 
 * @see ��Դʱ�䣺2016��03��31��
 * 
 *      �ǵø��Ҹ�starŶ~
 * 
 */
public abstract class PollingManagerHelper implements Serializable {

	public abstract Bundle requestNetworkData(Bundle oldBundle);

	public abstract boolean handleScreenOn(Context context, Bundle newBundle);

	public abstract boolean handleWakeUpAndUnlock(Context context,
			Bundle newBundle);

	public abstract Intent targetActivityByClickNotify(Context context,
			Bundle newBundle);

	public abstract SimpleNotifyBean getSimpleNotifyBean(Resources resources,
			Bundle newBundle);

	public abstract void clickedNotification();

	public abstract void updatePolling(Context context);

}
