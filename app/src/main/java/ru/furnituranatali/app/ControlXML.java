package ru.furnituranatali.app;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;

import org.htmlcleaner.TagNode;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.*;


// определяем класс, который будет связывать XML-файл структуры каталога с остальной программой
public class ControlXML extends Object{
    private static final String XML_FILENAME = "catalog.xml";
    private static final String TAG = "FN_App: ControlXML";
// Определение тегов в XML-файле
    // основные элементы
    private static final String PROLOG_ENCODING = "UTF-8";
    private static final String ROOT_PREF_HTML = "html";
    private static final String ROOT_NS_HTML = "http://www.w3.org/TR/html4/";
    private static final String TAG_ROOT = "RootCatalog";
    private static final String TAG_CATALOG_ITEM = "CatalogItem";
    private static final String TAG_GOODS_ITEM = "GoodsItem";
    private static final String TAG_IMAGE_ITEM = "ImageItem";
    // эелементы, используемые только внутри основных
    private static final String TAG_PATH = "path";
    private static final String TAG_NAME = "name";
    private static final String TAG_WIDTH = "width";
    private static final String TAG_HEIGHT = "height";
    private static final String TAG_CAPTION = "caption";
    // аттрибуты элементов
    private static final String ATTR_ID = "id";
    private static final String ATTR_TYPE = "type";
    private static final String ATTR_W_H_MEASURE = "measure";
    // значения аттрибутов
    private static final String ATTR_PATH_TYPE_REL = "relative";
    private static final String ATTR_PATH_TYPE_ABS = "absolute";
    private static final String ATTR_CAPT_SHORT = "short";
    private static final String ATTR_CAPT_LONG = "long";
    private static final String ATTR_CAPT_NORM = "normal";
    private static final String ATTR_IMAGE_TYPE_PREV = "preview";
    private static final String ATTR_IMAGE_TYPE_NORM = "normal";
    private static final String ATTR_IMAGE_TYPE_BIG = "big";
    private static final String ATTR_W_H_MEASURE_PIXELS = "px";
    private static final String ATTR_W_H_MEASURE_POINTS = "pt";
    private static final String ATTR_W_H_MEASURE_DIPS = "dp";
    // html-теги остаются как есть, с добавлением ATTR_ROOT_NS_HTML
    private static final String HTML_TAG_a = "a";
    private static final String HTML_TAG_li = "li";
    private static final String HTML_TAG_span = "span";
    private static final String HTML_TAG_img = "span";
    //html-атрибуты
    private static final String HTML_ATTR_HREF = "href";
    private static final String HTML_ATTR_CLASS = "class";
    private static final String HTML_ATTR_ALT = "alt";
    private static final String HTML_ATTR_SRC = "src";
    private static final String HTML_ATTR_W = "width";
    private static final String HTML_ATTR_H = "height";
    private static final String HTML_ATTR_TITLE = "title";


    private String contentXML;
    private XmlPullParser parser;
    private List<CardData> catalog;
    private Context context;
    private static ParseHTML parseHTML;

//    Public methods
    // конструктор ищет файл catalog.xml и если находит, читает его, иначе использует файл из res/xml
    public ControlXML(Context context) {
        this.context = context;
        // далее следует инициализация поля catalog единственным элементом, которое содержит
        // единственный параметр: caption = содержимое R.string.str_item0
        catalog = new ArrayList<>();
        catalog.add(new CardData(false, context.getString(R.string.str_item0)));

        // теперь читаем данные из файла и, если вылетаем с ошибкой, то создаем новый XML-файл,
        // записывая в него начальные данные
        try {
            fromFile();
		} catch (XmlPullParserException e) {} 
		catch (IOException e) {
            Log.e(TAG, "constructor ControlXML: read from file", e);
            try {
                toFile();
            } catch (IOException e1) {
                Log.e(TAG, "constructor ControlXML: write to file", e1);
            }
        }

    }
    // сохраняем данные в XML-файл, если он не существует - создаем
    public void toFile() throws IOException {
        FileOutputStream outputXML;
        StringWriter swr = new StringWriter();
        XmlSerializer xmlSerializer = Xml.newSerializer();

        outputXML = context.openFileOutput(XML_FILENAME, Context.MODE_PRIVATE);

        xmlSerializer.setOutput(outputXML, PROLOG_ENCODING);
//        xmlSerializer.setOutput(swr);

        xmlSerializer.startDocument(PROLOG_ENCODING, Boolean.TRUE);
        xmlSerializer.text("\n");
        xmlSerializer.setPrefix(ROOT_PREF_HTML, ROOT_NS_HTML);
        xmlSerializer.startTag(null, TAG_ROOT);
        xmlSerializer.text("\n");
        xmlSerializer.text("  ");
            xmlSerializer.startTag(ROOT_NS_HTML, HTML_TAG_a);
            xmlSerializer.attribute(null, HTML_ATTR_HREF, context.getString(R.string.str_web_site_http));
            xmlSerializer.text(context.getString(R.string.str_web_site_text));
            xmlSerializer.endTag(ROOT_NS_HTML, HTML_TAG_a);
            xmlSerializer.text("\n\n");
        // далее теги из поля catalog
        if (catalog != null){
            CardData item;
            boolean hasChild;
            String tagName;
            StringBuffer leftMargin;
            int n = catalog.size();
            for (int i = 0; i < n; i++){
                leftMargin = new StringBuffer(6);
                int level = 0;
                item = catalog.get(i);
                do {
                    int len = leftMargin.length() - 2;
                    len = len > 0 ? len : 0;
                    leftMargin.setLength(len);
                    do {
                        tagName = item.isProduct() ? TAG_GOODS_ITEM : TAG_CATALOG_ITEM;
                        leftMargin.append("  ");
                        if ( !item.isHasClosedTAG() ) break;

                        xmlSerializer.text(leftMargin.toString());
                        xmlSerializer.startTag(null, tagName);
                        if (item.getID() > 0) xmlSerializer.attribute(null, ATTR_ID, String.valueOf(item.getID()));
                        xmlSerializer.text("\n");

                        xmlSerializer.text(leftMargin.toString());
                        xmlSerializer.startTag(null, TAG_CAPTION);
                        xmlSerializer.attribute(null, ATTR_TYPE, ATTR_CAPT_NORM);
                        xmlSerializer.text(item.getCaption());
                        xmlSerializer.endTag(null, TAG_CAPTION);
                        xmlSerializer.text("\n");

                        item.setClosedTAG(false);
                        hasChild = item.getChildCount() > 0;
                        if (hasChild){
                            item = item.getChildCards().get(0);
                            level++;
                        }
                    } while (hasChild);

                    xmlSerializer.text(leftMargin.toString());
                    xmlSerializer.endTag(null, tagName);
                    xmlSerializer.text("\n");
                    item.setClosedTAG(true);
                    int itemIdx;
                    if (item.getParentCard() != null) {
                        itemIdx = item.getParentCard().getChildCards().indexOf(item);
                        itemIdx++;
                        if (itemIdx < item.getParentCard().getChildCount()){
                            item = item.getParentCard().getChildCards().get(itemIdx);
                        }else {
                            item = item.getParentCard();
                            level--;
                            len = leftMargin.length() - 2;
                            len = len > 0 ? len : 0;
                            leftMargin.setLength(len);
                        }
                    }else{
                        level--;
                    }

                }while (level >= 0);
            }
        }
        xmlSerializer.endTag(null, TAG_ROOT);
        xmlSerializer.endDocument();

//        contentXML = swr.toString();

//        outputXML.write(contentXML.getBytes());
        outputXML.close();

    }
    // парсинг XML
    public void parsingXML(XmlPullParser pullParser){

    }
    // получаем данные из XML-файла и парсим их, сохраняя данные в поле catalog
    public void fromFile() throws IOException, XmlPullParserException {
        FileInputStream inputXML;

        inputXML = context.openFileInput(XML_FILENAME);
        InputStreamReader sr = new InputStreamReader(inputXML);
// создаем буфер для чтения файла
        BufferedReader reader = new BufferedReader(sr);
        StringBuffer buffer = new StringBuffer();
// читаем данные в буфер
        String s;
        while ((s = reader.readLine())!= null) {buffer.append(s + "\n");}
        contentXML = buffer.toString();
        inputXML.close();
		XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XmlPullParser parser = factory.newPullParser();
		parsingXML(parser);
    }

    public String getContentXML() {
        return contentXML;
    }

    public void setCatalog(List<CardData> catalog, boolean needSaveToFile) {
        this.catalog.clear();
        this.catalog.addAll(catalog);// = catalog;
        if (needSaveToFile) try {
            toFile();
        } catch (IOException e) {
            Log.e(TAG, "setCatalog: writeToFile ", e);
        }
    }

    public void setCatalog(List<CardData> catalog) {
        setCatalog(catalog, false);
    }

    public void setContentXML(String content) {
        contentXML = content;
        // далее следует код создания (если необходимо) и заполнения поля catalog
    }
    // внутренний класс для парсинга html
    private class ParseHTML {
        private TagNode rootNode;

        public ParseHTML(URL url) throws IOException {

        }
    }
}
