package ru.furnituranatali.app;

import android.content.Context;

import java.util.Date;
import java.util.List;
import java.util.*;

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
	 * временно прописываем в нем статические данные
	 * для заполнения currentCardList
     */
    public void receiveFromWEB(int level) {
		receiveFromWEB(level, 0);
    }
	public void receiveFromWEB(int level, int idxParent){
        String[][] cats = {{
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
        },{
			"http://furnituranatali.ru/static/img/0000/0004/3330/43330851.8w8yut0rtl.156x120.png",
			"http://furnituranatali.ru/static/img/0000/0003/3442/33442102.ts8lp969pz.156x120.png",
			"http://furnituranatali.ru/static/img/0000/0003/3441/33441666.h4uzk2xqgr.156x120.png",
			"http://furnituranatali.ru/static/img/0000/0002/7353/27353803.es7f52b9cz.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/0419/30419335.6azipkgs3g.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0002/7353/27353834.mk4tfuxrvg.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/6854/36854566.a7x1vevvn1.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/6057/36057529.jwpbbog4i1.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/3442/33442174.9ojobc0pq4.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/6725/36725212.3w30sum0dr.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/5552/35552945.422ymijnka.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0002/7354/27354128.p8fpg7pu95.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0002/7354/27354228.3pmktq13yz.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/5545/35545138.g9pvofy823.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0004/2601/42601724.83yew9wjd5.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/0045/30045357.799bygblx6.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/7433/37433403.h4nlfk4pys.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/4075/34075904.gso16u4u7j.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/4488/34488267.emb3iyhtbw.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0002/7354/27354442.4xq5ud34z7.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/3690/33690174.bconew3fc9.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0003/4648/34648029.hryp6qd9ar.156x120.png", 
			"http://furnituranatali.ru/static/img/0000/0004/1507/41507577.c77913wxmq.156x120.png"
		}};
        currentCardList = new ArrayList<>();
        for (int i = 0; i < cats.length; i++){
            currentCardList.add(new CardData.Set_ID_Caption(i, cats[0][i]).setImageLink(cats[1][i]).setChildCardsCount(2).build());
        }
    }
}
