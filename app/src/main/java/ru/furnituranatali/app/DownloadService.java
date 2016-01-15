package ru.furnituranatali.app;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class DownloadService extends IntentService {

    public final static String ACTION_RESPONSE_INPROGRESS = "ru.furnituranatali.app.action.response.INPROGRESS";
    public final static String ACTION_RESPONSE_FINISH = "ru.furnituranatali.app.action.response.FINISH";
    public final static String ACTION_RESPONSE_ERROR = "ru.furnituranatali.app.action.response.ERROR";
    public final static String EXTRA_NAME_NORMAL= "ru.furnituranatali.app.extra.name.NORMAL";

    private final DownloadServiceBinder mBinder = new DownloadServiceBinder();

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * Запуск сервиса, после его присоединения командой bindService, для действий по обновлению БД
     * @see IntentService
     */
    public static void startUpdate(Context context, String updateTarget, long codeOfElement) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.setAction(Const.ACTION_UPDATE);
        intent.putExtra(Const.EXTRA_NAME_UPD_TARGET, updateTarget);
        intent.putExtra(Const.EXTRA_NAME_CODE_OF_ELEM, codeOfElement);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (Const.ACTION_UPDATE.equals(action)) {
//                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
//                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
//                handleActionFoo(param1, param2);

            }
        }
    }

    /**
     * Class used when service id binding .
     */
    public class DownloadServiceBinder extends Binder{
        DownloadService getService (){ return DownloadService.this;}
    }
}
