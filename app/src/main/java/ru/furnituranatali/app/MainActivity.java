package ru.furnituranatali.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by Vavan on 20.09.2015.
 */
public class MainActivity extends Activity {

    private Toolbar main_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitToolbar();
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

}

