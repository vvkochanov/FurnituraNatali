package ru.furnituranatali.app;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.concurrent.TimeUnit;

public class ServiceWEB_SQL extends Service {
    /**
     * private fields
     */
    private static final String TAG = "FN_App: ";
    /**
     * public fields
     */
    public static final String INTENT_FINISH_ACT = "ru.furnituranatali.app.FINISH";
    public static final String INTENT_IN_PROGRESS_ACT = "ru.furnituranatali.app.IN_PROGRESS";

    /**
     * public methods
     */
    public ServiceWEB_SQL() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO: Запуск асинхронных потоков для загрузки и сохранения данных из Инета, если необходимо
        AsyncDownload asyncDownload = new AsyncDownload();
//        asyncDownload.execute();
        Intent intentFinish = new Intent();
        intentFinish.setAction(INTENT_FINISH_ACT);
        intentFinish.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(intentFinish);
        return Service.START_NOT_STICKY;//super.onStartCommand(intent, flags, startId);
    }

    /**
     * private methods and inner classes
     */
    private class AsyncDownload extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                Log.e(TAG + this.getClass().getName(), "Sleep interrupted");
            }
            return null;
        }
    }
}
