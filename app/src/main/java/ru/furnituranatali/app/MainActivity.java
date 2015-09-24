package ru.furnituranatali.app;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

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
            return false;
        }
    });

    main_toolbar.inflateMenu(R.menu.toolbar_menu);

}

    private void InitNavView() {
        drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);

    }

}

