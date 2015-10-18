package ru.furnituranatali.app;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static ru.furnituranatali.app.Common.catalog;

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
                try {
                    synchronized (this){
                       long t_start = System.currentTimeMillis();
//                        String s = InitCardData();
                        long t_delta = System.currentTimeMillis() - t_start;
                        Log.i(TAG, "OnCreate: run: controlXML.getContentXML(): time to write: " + String.valueOf(t_delta));
                        wait(5);
                    }
                }catch (InterruptedException e){} finally {
                    Intent intent = new Intent();
                   // intent.putParcelableArrayListExtra("TestParcel",TestProc());
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
    }
    private String InitCardData() throws IOException {
        String[] cats = {
                "Лента атласная",
                "Лента атласная с рисунком",
                "Ленты репсовые",
                "Лента декоративная",
                "Органза",
                "Парча",
                "Тейп ленты",
                "Тычинки",
                "Термоаппликации",
                "Кружево",
                "Кабошоны-серединки",
                "Бусины-полубусины",
                "Стразы",
                "Фоамиран",
                "Флористика",
                "Фетр",
                "Пенопластовые основы",
                "Шнуры и нити.",
                "Аксессуары для волос",
                "Металлофурнитура и пластик",
                "Инструмент для рукоделия",
                "Изделия ручной работы"
        };
        ControlXML controlXML = new ControlXML(getApplicationContext());
        Log.i(TAG, "InitCardData: controlXML.getContentXML():\n" + controlXML.getContentXML());
        catalog = new ArrayList<>();
        for (int i = 0; i < cats.length; i++){
            catalog.add(new CardData(false, i, cats[i]));
            for (int j = 0; j < 10; j++) {
                CardData prod = new CardData(false, j, String.format("ПодКаталог %d", j));
                catalog.get(i).addChild(prod);
                for (int k = 0; k < 50; k++){
                    prod.addChild(new CardData(true, String.format("Товар %d", k)));
                }
             }
        }
        controlXML.setCatalog(catalog, true);
        return  controlXML.getContentXML();
    }
    ArrayList<TestParcelable> TestProc(){
        ArrayList<TestParcelable> parcebleList = new ArrayList<>();
        for (int i = 0; i < 1; i++){

            String s1 = String.valueOf(i);
            parcebleList.add(new TestParcelable("Caption " + s1, i, false));
            for (int j = 0; j < 1; j++) {
                String s2 = String.valueOf(j);
                parcebleList.get(i).addChildCards(new TestParcelable("SubCapt " + s2, j, true));
            }
        }
        return parcebleList;
    }
}
