package ru.furnituranatali.app;

import android.content.Context;
import android.os.Bundle;
import android.support.design.internal.NavigationMenuView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by Vavan on 20.09.2015.
 */
public class MainActivity extends AppCompatActivity {

    private static final int MAIN_LAYOUT = R.layout.activity_main;
    private Toolbar main_toolbar;
    private DrawerLayout  drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppDefault);
        super.onCreate(savedInstanceState);
        setContentView(MAIN_LAYOUT);

        InitToolbar();
        InitNavView();
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
//                далее будет вызов активити, которое отобразит соответсвующие подкатегории товаров либо товары соотсетствующей категории/подкатегории

                Context context = getApplicationContext();
                CharSequence disp_text = "Выбран - " + menuItem.getTitle();
                Toast.makeText(context, disp_text, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        Menu leftNavMenu = navigationView.getMenu();
        leftNavMenu.getItem(0).setTitle("ЛЕНТА АТЛАСНАЯ");
        leftNavMenu.add("ЛЕНТА АТЛАСНАЯ С РИСУНКОМ");
        leftNavMenu.add("ЛЕНТЫ РЕПСОВЫЕ");
        leftNavMenu.add("ЛЕНТА ДЕКОРАТИВНАЯ");
        leftNavMenu.add("ОРГАНЗА");
        leftNavMenu.add("ПАРЧА");
        leftNavMenu.add("ТЕЙП ЛЕНТЫ");
        leftNavMenu.add("ТЫЧИНКИ");
        leftNavMenu.add("ТЕРМОАППЛИКАЦИИ");
        leftNavMenu.add("КРУЖЕВО");
        leftNavMenu.add("КАБОШОНЫ-СЕРЕДИНКИ");
        leftNavMenu.add("БУСИНЫ-ПОЛУБУСИНЫ");
        leftNavMenu.add("СТРАЗЫ");
        leftNavMenu.add("ФОАМИРАН");
        leftNavMenu.add("ФЛОРИСТИКА");
        leftNavMenu.add("ФЕТР");
        leftNavMenu.add("ПЕНОПЛАСТОВЫЕ ОСНОВЫ");
        leftNavMenu.add("ШНУРЫ И НИТИ.");
        leftNavMenu.add("АКСЕССУАРЫ ДЛЯ ВОЛОС");
        leftNavMenu.add("МЕТАЛЛОФУРНИТУРА И ПЛАСТИК");
        leftNavMenu.add("ИНСТРУМЕНТ ДЛЯ РУКОДЕЛИЯ");
        leftNavMenu.add("ИЗДЕЛИЯ РУЧНОЙ РАБОТЫ");
        for (int i = 0; i < leftNavMenu.size(); i++){
            leftNavMenu.getItem(i).setIcon(R.mipmap.ic_checkbox_blank_circle);
        }
    }

}

