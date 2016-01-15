package ru.furnituranatali.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {
    /**
     * Активность-заставка
     * Во время её работы происходит загрузка первоначального контента из Интернета в локальную базу
     *      mCountSvcMsgs - отсчет кол-ва сообщений об успешной работе сервиса
     *      MAX_SVC_MSGS - макс. кол-во сообщений об успешной работе сервиса (необходимо для ограничения максимального времени ожидания сервиса)
     *      WAIT_SVC_TIME - время ожидания ответа о состоянии сервиса
     */
    private static final String TAG = "FN_App: Splash";
    private static final int SPLASH_LAYOUT = R.layout.activity_splash;
    private static final long WAIT_SVC_TIME = 10000;
    private static final long MAX_SVC_MSGS = 5;

    private String serviceRunIs; // флаговая переменная определяющая состояние сервиса: завершен удачно, не удачно и в процессе выполнения
    private ControlSQL controlSQL;
    private ControlHTML controlHTML;
    private ProgressBar progressBar;
    private int mCountSvcMsgs;

    private Timer mTimer;
    private WaitServiceTimerTask mWaitServiceTT;

    private void startActivityStringExtra(Context ctx, Class<?> cls, String extraName, String extraVal){
        Intent intentMainActivity = new Intent(ctx, cls);
        intentMainActivity.putExtra(extraName, extraVal);
        startActivity(intentMainActivity);
    }
    private BroadcastReceiver splashBrReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case DownloadService.ACTION_RESPONSE_FINISH:
                    if(mTimer != null){
                        mTimer.cancel();
                        mTimer.purge();
                        mTimer = null;
                    }
                    startActivityStringExtra(context, MainActivity.class, Const.EXTRA_NAME_SVC_FINISH, Const.EXTRA_VAL_SUCCESS);
                    finish();
                    return;
                case DownloadService.ACTION_RESPONSE_INPROGRESS:
                    if(mCountSvcMsgs < MAX_SVC_MSGS){
                        if(mTimer != null){
                            mTimer.cancel();
                            mTimer.purge();
                        }else{
                            mTimer = new Timer();
                        }
                        mWaitServiceTT = new WaitServiceTimerTask();
                        mTimer.schedule(mWaitServiceTT, WAIT_SVC_TIME);
                        mCountSvcMsgs++;
                    }
                    return;
                case Const.ACTION_TIMER_FINISH:
                    startActivityStringExtra(context, MainActivity.class, Const.EXTRA_NAME_SVC_WAITTIME, Const.EXTRA_VAL_FINISH);
                    finish();
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        progressBar.setVisibility(ProgressBar.INVISIBLE);
    }

    @Override
/**
 *  --------------- ОПИСАНИЕ ЛОГИКИ ----------------------
 *  Создается / открывается БД (класс ControlSQL)
 *  Читаются данные из таблицы (проверочный(ые) код(ы))
 *  Вычисляются проверочные коды из содержимого сайта
 *  Если коды совпадают или невозможно получить данные из сети, данное активити передает управление MainActivity
 *  Если коды не совпадают и  сайт доступен, происходит вычисление и загрузка различающихся данных в локальную базу
 *  Далее переход к MainActivity
 */
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        setContentView(SPLASH_LAYOUT);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        progressBar.setVisibility(ProgressBar.VISIBLE);
        /**
         * запускаем сервис
         * устанавливаем переменную serviceRunIs в состояние finish error
         * далее запускаем поток с таймером на 1 сек.
         * после окончания таймера проверяется знач. перем. serviceRunIs
         * если данная переменная изменила свое состояние на In Service, то повторяем запуск таймера
         */
/**
 * старая реализация
        Intent intentToSVC = new Intent(this, ServiceWEB_SQL.class);
        startService(intentToSVC.putExtra (getString(R.string.extraStartDownLvl), 0));
		serviceRunIs = getString(R.string.svcInProgress);

        Thread splashThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    synchronized (this){
                        Log.i(TAG, "try: Start splashThread " + this.getClass().getSimpleName());
                        while (serviceRunIs == getString(R.string.svcInProgress)) {
                            serviceRunIs = getString(R.string.svcFinishError);
                            wait(1000);
                        }
                    }
                }catch (InterruptedException e){}
				finally {
                    Log.i(TAG, "finally: Finish splashThread");
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    intent.putExtra(getString(R.string.extraSplashResult), serviceRunIs);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
*/
        /**
         * новая реализация
         */
        Intent svcIntent = new Intent(this, DownloadService.class).putExtra(Const.EXTRA_NAME_UPD_TARGET, Const.EXTRA_VAL_ALL);
        startService(svcIntent);
        mTimer = new Timer();
        mWaitServiceTT = new WaitServiceTimerTask();
        mTimer.schedule(mWaitServiceTT, WAIT_SVC_TIME);
        /**
         * регистрируем фильтры для сообщений о результатах выполнения сервиса и о промежуточных результатах
         */
        IntentFilter mIntentFilterTimerFinish = new IntentFilter(Const.ACTION_TIMER_FINISH);
        mIntentFilterTimerFinish.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(splashBrReceiver, mIntentFilterTimerFinish);

        IntentFilter mIntentFilterSvcInProgress = new IntentFilter(DownloadService.ACTION_RESPONSE_INPROGRESS);
        mIntentFilterSvcInProgress.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(splashBrReceiver, mIntentFilterSvcInProgress);

        IntentFilter mIntentFilterSvcFinish = new IntentFilter(DownloadService.ACTION_RESPONSE_FINISH);
        mIntentFilterSvcFinish.addCategory(Intent.CATEGORY_DEFAULT);
        LocalBroadcastManager.getInstance(this).registerReceiver(splashBrReceiver, mIntentFilterSvcFinish);

    }

    @Override
    protected void onStop() {
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(splashBrReceiver);
        super.onStop();
    }

    // Внутренние приватные методы и классы
	private void InitDB() {

        controlSQL = new ControlSQL(this);
        controlHTML = new ControlHTML(this);
        if (controlHTML.getUpdateTime().compareTo(controlSQL.getUpdateTime()) == 1){
            /**
			 * время последнего обновления сайта больше, чем время обновления в БД, 
			 * значит получаем обновление из Инета и сохраняем их в БД
			 */
            controlHTML.receiveFromWEB(0);
            for (CardData cardData: controlHTML.getCurrentCardList()){
                controlSQL.prepareAllForDelete();
                for (CardData data : controlSQL.getCurrentCardList()) {
                    if (cardData.getWebId() == data.getWebId()) {
                        data.modifyData(cardData);
                    }
                }
                controlSQL.addCard(cardData);
            }
        }
    }

    private class WaitServiceTimerTask extends TimerTask{
        @Override
        public void run() {
            Log.v(TAG, "Start TimerTask");
            Intent intent = new Intent(Const.ACTION_TIMER_FINISH);
            LocalBroadcastManager.getInstance(getParent()).sendBroadcast(intent);
        }
    }
}
