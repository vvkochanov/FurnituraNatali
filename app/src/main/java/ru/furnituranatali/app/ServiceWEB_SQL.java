package ru.furnituranatali.app;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Задачи данного сервиса и логика работы:
 * Основная задача - загрузка данных из Интернета и их синхронизация с локальной БД
 * Сервис получает запрос от текущей активити при помощи StartService и  putExtra:
 *   Значения extra:
 *   extraStartDownLvl = Start Download Level,
 *      передаваемое значение: массив значений int от 0 и выше, где 1й эл. массива - уровень каталога,
 *      2й - номер элемента каталога верхнего уровня (для значений уровня каталога от 1 и выше)
 *      возвращаемое значение: Broadcast Message with Extra String:
 *      EXTRA_STATUS_SVC,
 *      может содержать значения:
 *      <string name="svcInProgress">Service in progress</string>
 *      <string name="svcFinishError">Service finish with error</string>
 *      <string name="svcFinishSuccess">Service finish successful</string>
 *   extraStartDownImage = Start Download image,
 *      передаваемое значение: int, артикул товара, для которого требуется загрузить полноразмерное изображение
 *      возвращаемое значение: Broadcast Message with Extra Bitmap
 * В методе OnStartCommand проверяются переданные вызывающим активити данные:
 *      При Start Download Level и переданном уровне  >= 0, выполняется проверка соответствия данным в БД
 *      данным на сайте furnituranatali.ru и, если необходимо, подгружается структура сайта (в AsyncDownload) и далее
 *      загружаются изображения в асинхронных потоках (ImageDownload), для указанного уровня
 *      При Start Download Image выполняется проверка соответствия (AsyncDownload) и загрузка полноразмерного изображения
 *      для указанного товара (ImageDownload)
 */
public class ServiceWEB_SQL extends Service {
    /**
     * private fields
     */
    private static final String TAG = "FN_App: ";

    private ArrayList<CardData> curCatalog;
    private ControlSQL controlSQL;

    /**
     * public fields
     */
    public static final String INTENT_FINISH_ACT = "ru.furnituranatali.app.FINISH";
    public static final String INTENT_IN_PROGRESS_ACT = "ru.furnituranatali.app.IN_PROGRESS";
    public static final String EXTRA_STATUS_SVC = "ru.furnituranatali.app.Service status";
    public static final String EXTRA_BITMAP_RET = "ru.furnituranatali.app.Return Bitmap";
    public static final String TASK_JOB_SITE = "ru.furnituranatali.app.Parsing WebSite";
    public static final String TASK_JOB_IMAGES = "ru.furnituranatali.app.Download Images";

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
        /**
         * Запуск асинхронных потоков для загрузки и сохранения данных из Инета, если необходимо
         */

        // TODO: Определяем необходимость обновить структуру сайта. Проверяем соответствие с БД
        if (controlSQL == null) controlSQL = new ControlSQL(this);
        if (controlSQL.getUpdateCode() == 0) {
            // TODO: Необходимо усложнить проверку Update Code (в дальнейшем)
            // Если структура сайта и БД различаются, то загружаем структуру сайта и все текстовые данные сохраняем в базу

            /**
             * при запуске задачи на загрузку и парсинг структуры сайта передаются следующие параметры:vavant   Pod537geN
             *
             * 0-я строка, указывает на неободимость загрузки структуры сайта
             * 1-я строка, содержит адрес сайта
             * 2-я строка, содержит число, отражающее переданный уровень каталога
             */
            int lvl = intent.getIntExtra(getString(R.string.extraStartDownLvl), 0);
//            siteDownload.execute(TASK_JOB_SITE, getString(R.string.str_web_root_http), String.valueOf(lvl));
        }
        //TODO: далее производим загрузку избражений элементов того уровня каталога, который был передан при старте сервиса
        /**
         * при запуске задачи на скачивание изображений передаются следующие параметры:
         * - массив строк, содержащий все адреса картинок для загрузки.
         * 0й элемент массива - строка, указывающая на необходимость загрузки изображений
          */
        String imagesLinks[] = new String[10];
        imagesLinks[0] = TASK_JOB_IMAGES;
        //TODO: заполнить оставшиеся элементы массива адресами изображений в соответсвии с уровнем
        /**
         * Уведомляем вызвавший даннный сервис процесс о завершении работы
         */
        Intent intentFinish = new Intent();
        intentFinish.setAction(INTENT_FINISH_ACT);
        intentFinish.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(intentFinish);
        return Service.START_NOT_STICKY;//super.onStartCommand(intent, flags, startId);
    }

    /**
     * private methods and inner classes
     */

}
