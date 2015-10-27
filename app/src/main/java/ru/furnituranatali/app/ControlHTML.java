package ru.furnituranatali.app;

import android.content.Context;

import java.util.Date;
import java.util.List;

/**
 * Created by Vavan on 26.10.2015.
 */
public class ControlHTML {
    private static final String FN_WEB = "furnituranatali.ru";
    private List<CardData> currentCardList;
    private int updateCode;
    private Date updateTime;
    private Context context;

    public ControlHTML(Context context) {
        this.context = context;
    }

    public List<CardData> getCurrentCardList() {
        return currentCardList;
    }

    public int getUpdateCode() {
        return updateCode;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * Метод, который получает данные из Интернета
     */
    public void receiveFromWEB() {

    }
}
