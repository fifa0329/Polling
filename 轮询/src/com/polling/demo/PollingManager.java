package com.polling.demo;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.polling.R;
import com.polling.bean.SimpleNotifyBean;
import com.polling.helper.PollingManagerHelper;
import com.polling.util.PollingUtil;

public class PollingManager extends PollingManagerHelper {

	private static int mCount = 0;

	@Override
	public Bundle requestNetworkData(Bundle oldBundle) {
		int count = oldBundle.getInt("count", -1);
		count = ++mCount + count;
		Bundle newBundle = new Bundle();
		newBundle.putInt("count", count);
		return newBundle;
	}

	@Override
	public boolean handleScreenOn(final Context context, Bundle newBundle) {
		Log.i("My", "��Ļ����");
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(context, "�������߳���", Toast.LENGTH_SHORT).show();
			}
		});
		return false;
	}

	@Override
	public boolean handleWakeUpAndUnlock(Context context, Bundle newBundle) {
		Log.i("My", "������Ļ������");
		Intent intent = new Intent(context, DialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("count", newBundle.getInt("count", -1));
		context.startActivity(intent);
		return false;
	}

	@Override
	public Intent targetActivityByClickNotify(Context context, Bundle newBundle) {
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.putExtra("count", newBundle.getInt("count", -1));
		return intent;
	}

	@Override
	public SimpleNotifyBean getSimpleNotifyBean(Resources resources,
			Bundle newBundle) {
		int count = newBundle.getInt("count", -1);
		Bitmap largeIcon = BitmapFactory.decodeResource(resources,
				R.drawable.ic_launcher);
		SimpleNotifyBean bean = new SimpleNotifyBean(R.drawable.ic_launcher,
				"������ϢŶ", "contentTitle��" + count, "contentText��" + count,
				largeIcon);
		return bean;
	}

	@Override
	public void clickedNotification() {
		Log.i("My", "�����֪ͨ");
	}

	@Override
	public void updatePolling(Context context) {
		if (mCount == 6) {
			Log.i("My", "������ѯʱ��");
			Bundle bundle = new Bundle();
			bundle.putInt("count", 100);
			PollingUtil.start(new PollingManager(), context, bundle, 15 * 1000,
					15 * 1000);
		}
	}

}
