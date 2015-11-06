package ru.furnituranatali.app;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class ServiceWEB_SQL extends Service {
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
        return Service.START_NOT_STICKY;//super.onStartCommand(intent, flags, startId);
    }
}
