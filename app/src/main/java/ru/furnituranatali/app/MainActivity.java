package ru.furnituranatali.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vavan on 20.09.2015.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MAIN_LAYOUT = R.layout.activity_main;
    private Toolbar main_toolbar;
    private DrawerLayout  drawerLayout;
    private RecyclerView mRecycleView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutMgr;
    private List<CardData> catalog;
//    private RecyclerView.ItemAnimator mItemAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

// инициализируем внутренний класс, который содержит всю необходимую инфу по каталожным и товарным карточкам
        InitCardData();
// инициализация ActionBar
        InitToolbar();
// инициализация Navigation Drawer. При этом передаются данные для левого меню из глоб. перем. catalog
        InitNavView();
// инициализация вывода карточек корневого каталога, данные берутся из catalog
        InitGridList();
       // Toast.makeText(getApplicationContext(), "data: last elem. capt: " + data.get(data.size()-1).getCaption(), Toast.LENGTH_SHORT).show();
    }

    private void InitCardData() {
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
        catalog = new ArrayList<>();
        for (int i = 0; i < cats.length; i++){
            catalog.add(new CardData.Set_ID_Caption(i, cats[i]).setChildCardsCount(2).build());
        }
    }

    private void InitToolbar() {

        main_toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        main_toolbar.setTitle(R.string.app_name);
        main_toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Context context = getApplicationContext();
                CharSequence disp_text = "Call Activity - " + item.getTitle();
                Toast.makeText(context, disp_text, Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        main_toolbar.inflateMenu(R.menu.toolbar_menu);
        Menu menuActionBar = main_toolbar.getMenu();
        menuActionBar.add("Оплата и доставка");
        menuActionBar.add("Контактная информация");
        menuActionBar.add("Документы на ИП");
        menuActionBar.add("Скидки");

    }

//инициализация NavigationView - выплывающего меню слева
    private void InitNavView() {
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, main_toolbar, R.string.toggle_open, R.string.toggle_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.leftNav);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();
//                далее будет вызов активити, которое отобразит соответсвующие подкатегории товаров либо товары соответствующей категории/подкатегории
//                mAdapter.bindViewHolder();
                Context context = getApplicationContext();
                CharSequence disp_text = "Выбран - " + menuItem.getTitle();
                Toast.makeText(context, disp_text, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        Menu leftNavMenu = navigationView.getMenu();
        MenuItem menuItem = leftNavMenu.getItem(0).setTitle(catalog.get(0).getCaption());//"Лента атласная");

        for (int i = 1; i < catalog.size(); i++){
            leftNavMenu.add(catalog.get(i).getCaption());
            leftNavMenu.getItem(i).setIcon(R.mipmap.ic_checkbox_blank_circle);
        }
    }
// Инициализация Grid List состоящим из карточек категорий
    private void InitGridList() {
        String[] textsDataSet= {
          "Адын!", "Дыва!", "Тыры!", "Читыры!", "Пать!", "Шест!", "Сэм!", "Восэм!"
        };
        mRecycleView = (RecyclerView) findViewById(R.id.recycle_grid_layout);
        mRecycleView.setHasFixedSize(true);

        mLayoutMgr = new GridLayoutManager(this, 2);
        mRecycleView.setLayoutManager(mLayoutMgr);

        mAdapter = new MainAdapter(catalog);
        mRecycleView.setAdapter(mAdapter);
    }
//    обработчик событий OnClick на карточку
    public void sendCardMsg(View v){
        Toast.makeText(getApplicationContext(), "Click on Card", Toast.LENGTH_SHORT).show();
    }
    //    обработчик событий OnClick на нижний фрейм в карточке
    public void sendFrmMsg(View v){
        Toast.makeText(getApplicationContext(), "Click on Frame", Toast.LENGTH_SHORT).show();
    }


}

