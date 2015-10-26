package ru.furnituranatali.app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Splash extends AppCompatActivity {

    private static final String TAG = "FN_App: Splash";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                super.run();

// --------------- ОПИСАНИЕ ЛОГИКИ ----------------------
//       Создается / открывается БД (класс SQL_Helper или его обертка)
//       Читаются данные из таблицы (проверочный(ые) код(ы))
//       Вычисляются проверочные коды из содержимого сайта
//       Если коды совпадают или невозможно получить данные из сети, данное активити передает управление MainActivity
//       Если коды не совпадают и  сайт доступен, происходит вычисление и загрузка различающихся данных в локальную базу
//       Далее переход к MainActivity
                try {
                    synchronized (this){
                       long t_start = System.currentTimeMillis();
                        InitDB();
                        long t_delta = System.currentTimeMillis() - t_start;
                        Log.i(TAG, "OnCreate: run: InitDB(): time to init DB: " + String.valueOf(t_delta));
                        wait(5);
                    }
                }catch (InterruptedException e){} 
				finally {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
    }
	private void InitDB() {
        Context ctxt = this;
        ctxt = getApplicationContext();
        ControlSQL controlSQL = new ControlSQL(this);

    }
}
