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
    private int id;
    private int web_id;
    private int article;
    private float price;
    private float sale;
    private int count;
    private boolean isProduct;
    private int childCardsCount;

     /**
      * статический класс Builder, под названием Set_ID_Caption для удобного добавления к конструктору
      * всех параметров, особенно необязательных
      * обязательные параметры id, caption
      * остальные необязательные
     */
    public static class Set_ID_Caption {
        private String caption;
        private String captionLong = "";
        private int id = 0;
        private int web_id = 0;
        private int article = 0;
        private float price = 0;
        private float sale = 0;
        private int count = 0;
        private int childCardsCount = 0;

        public Set_ID_Caption(int id, String caption){
            this.id = id;
            this.caption = caption;
        }

        public Set_ID_Caption setCaptionLong(String captionLong) {
            this.captionLong = captionLong;
            return this;
        }

        public Set_ID_Caption setWeb_id(int web_id) {
            this.web_id = web_id;
            return this;
        }

        public Set_ID_Caption setArticle(int article) {
            this.article = article;
            return this;
        }

        public Set_ID_Caption setPrice(float price) {
            this.price = price;
            return this;
        }

        public Set_ID_Caption setSale(float sale) {
            this.sale = sale;
            return this;
        }

        public Set_ID_Caption setCount(int count) {
            this.count = count;
            return this;
        }

        public Set_ID_Caption setChildCardsCount(int childCardsCount) {
            this.childCardsCount = childCardsCount;
            return this;
        }
        public CardData build(){
            return new CardData(this);
        }
    }
     public CardData(Set_ID_Caption builder) {
         this.caption = builder.caption;
         this.captionLong = builder.captionLong;
         this.id = builder.id;
         this.web_id = builder.web_id;
         this.article = builder.article;
         this.price = builder.price;
         this.sale = builder.sale;
         this.count = builder.count;
         this.isProduct = price > 0 | sale > 0;
         this.childCardsCount = builder.childCardsCount;

     }
// следующие конструкторы возможно похерим
     public CardData(boolean isProduct, String caption) {
         this(isProduct, 0, caption, null);
    }
    public CardData( boolean isProduct, int id, String caption, String captionLong) {
        this.isProduct = isProduct;
        this.id = id;
        this.caption = caption;
        this.captionLong = captionLong;
    }
// далее идут сеттеры и геттеры для параметров
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

//    public void addChild(CardData childCard) {
//        if (childCards == null) childCards = new ArrayList<CardData>();
//        childCard.setParentCard(this);
//        childCards.add(childCard);
//
//    }

    public int getChildCount() {
        return childCardsCount;
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

//    public CardData getParentCard() {
//        return parentCard;
//    }
//
//    public List<CardData> getChildCards() {
//        return childCards;
//    }
//
    public int getID() {
        return id;
    }
}
