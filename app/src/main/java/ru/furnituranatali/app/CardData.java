package ru.furnituranatali.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vavan on 02.10.2015.
 * Класс, являющийся внутренним отображением карточек товаров или категорий товаров
 */
public class CardData {
    private String caption;
    private String captionLong;
    private int article;
    private float price;
    private float sale;
    private int count;
    private boolean isProduct;
    private CardData parentCard;
    private List<CardData> childCards;

    public CardData(boolean isProduct) {
        this.isProduct = isProduct;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
    public void setCaption(String caption, String captionLong) {
        setCaption(caption);
        this.captionLong = captionLong;
    }
    public void setProduct(int article, float price, float sale, int count){
        this.article = article;
        this.price = price;
        this.sale = sale;
        this.count = count;
    }

    public void setParentCard(CardData parentCard) {
        this.parentCard = parentCard;
    }

    public void addChild(CardData childCard) {
        if (childCards == null) childCards = new ArrayList<CardData>();
        childCards.add(childCard);
    }

    public int getChildCount() {
        return childCards.size();
    }

    public String getCaption() {
        return caption;
    }

    public String getCaptionLong() {
        return captionLong;
    }

    public int getArticle() {
        return article;
    }

    public float getPrice() {
        return price;
    }

    public float getSale() {
        return sale;
    }

    public int getCount() {
        return count;
    }

    public boolean isProduct() {
        return isProduct;
    }

    public CardData getParentCard() {
        return parentCard;
    }

    public List<CardData> getChildCards() {
        return childCards;
    }
}
