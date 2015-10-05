package ru.furnituranatali.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_splash);
        Thread splashThread = new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    synchronized (this){
                        wait(5000);
                    }
                }catch (InterruptedException e){}
                finally {
                    Intent intent = new Intent();
                    intent.setClass(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
    }
}
