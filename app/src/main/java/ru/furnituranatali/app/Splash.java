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
	private String contentXML;
	
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
						contentXML = InitCardData();
                        Log.i(TAG, String.format("OnCreate: run: contentXML.size = %d", contentXML.length()));
                        long t_delta = System.currentTimeMillis() - t_start;
                        Log.i(TAG, "OnCreate: run: controlXML.getContentXML(): time to write: " + String.valueOf(t_delta));
                        wait(5);
                    }
                }catch (InterruptedException e){} 
				finally {
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.str_extra_content_xml), contentXML);
                    intent.setClass(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        splashThread.start();
    }
	
	private String InitCardData()  {
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
            for (int j = 0; j < 11; j++) {
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
	/**
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
    }*/
}
