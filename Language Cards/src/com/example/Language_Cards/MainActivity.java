package com.example.Language_Cards;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    private static final String KEY_ITEM = "item";
    private static final String KEY_OK = "ok";
    public static final String KEY_FOR_WORKACTIVITY = "key_for_work_activity";
    private DataBase dataBase;

    private int languageLeft = -1;
    private int languageRight = -1;
    private ImageView frameLeft, frameRight;
    private int systemLanguage;
    private boolean[] isSelect = new boolean[11];

    private TextView item1, item2, item3, item4, item5, item6, item7, item8, item9, item10;
    private TextView ok1, ok2, ok3, ok4, ok5, ok6, ok7, ok8, ok9, ok10;
    private Button button;
    private TextView modeText;
    private TextView[] mode_text = new TextView[6];

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //deleteDatabase(DataBase.DATABASE_NAME);
        dataBase = new DataBase(this);
        dataBase.open();
        frameRight = (ImageView) findViewById(R.id.frame_right); frameRight.setImageResource(R.drawable.frame);
        frameLeft = (ImageView) findViewById(R.id.frame_left); frameLeft.setImageResource(R.drawable.frame);
        ImageView arrows = (ImageView) findViewById(R.id.arrows); arrows.setImageResource(R.drawable.arrows);
        ImageView russianFlag = (ImageView) findViewById(R.id.image_russian); russianFlag.setImageResource(R.drawable.russia);
        ImageView englishFlag = (ImageView) findViewById(R.id.image_english); englishFlag.setImageResource(R.drawable.england);
        ImageView chineseFlag = (ImageView) findViewById(R.id.image_chinese); chineseFlag.setImageResource(R.drawable.china);
        item1 = (TextView) findViewById(R.id.item1); ok1 = (TextView) findViewById(R.id.ok1);
        item2 = (TextView) findViewById(R.id.item2); ok2 = (TextView) findViewById(R.id.ok2);
        item3 = (TextView) findViewById(R.id.item3); ok3 = (TextView) findViewById(R.id.ok3);
        item4 = (TextView) findViewById(R.id.item4); ok4 = (TextView) findViewById(R.id.ok4);
        item5 = (TextView) findViewById(R.id.item5); ok5 = (TextView) findViewById(R.id.ok5);
        item6 = (TextView) findViewById(R.id.item6); ok6 = (TextView) findViewById(R.id.ok6);
        item7 = (TextView) findViewById(R.id.item7); ok7 = (TextView) findViewById(R.id.ok7);
        item8 = (TextView) findViewById(R.id.item8); ok8 = (TextView) findViewById(R.id.ok8);
        item9 = (TextView) findViewById(R.id.item9); ok9 = (TextView) findViewById(R.id.ok9);
        item10 = (TextView) findViewById(R.id.item10); ok10 = (TextView) findViewById(R.id.ok10);
        button = (Button) findViewById(R.id.button);
        modeText = (TextView) findViewById(R.id.mode_text);
        mode_text[0] = (TextView) findViewById(R.id.mode_text_1);
        mode_text[1] = (TextView) findViewById(R.id.mode_text_11);
        mode_text[2] = (TextView) findViewById(R.id.mode_text_2);
        mode_text[3] = (TextView) findViewById(R.id.mode_text_22);
        mode_text[4] = (TextView) findViewById(R.id.mode_text_3);
        mode_text[5] = (TextView) findViewById(R.id.mode_text_33);



        systemLanguage = dataBase.getSystemLanguage();
        TextView globalLanguage = (TextView) findViewById(R.id.global_language);
        if (systemLanguage == 1) globalLanguage.setText("<<< Русский язык >>>");
        if (systemLanguage == 2) globalLanguage.setText("<<< English language >>>");
        if (systemLanguage == 3) globalLanguage.setText("<<< 中国的 >>>");

        globalLanguage.setOnClickListener(new View.OnClickListener() {
            TextView globalLanguage = (TextView) findViewById(R.id.global_language);
            @Override
            public void onClick(View v) {
                systemLanguage++;
                if (systemLanguage == 4) systemLanguage = 1;
                if (systemLanguage == 1) globalLanguage.setText("<<< Русский язык >>>");
                if (systemLanguage == 2) globalLanguage.setText("<<< English language >>>");
                if (systemLanguage == 3) globalLanguage.setText("<<< 中国的 >>>");
                dataBase.updateLanguage(systemLanguage);
                setLanguage(systemLanguage);
            }
        });

        for (int i = 0; i < 11; i++)
            isSelect[i] = false;

        russianFlag.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageLeft == 1 || languageRight == 1) return;
                if (languageLeft == -1) {
                    languageLeft = 1;
                    frameLeft.setImageResource(R.drawable.russia);
                } else {
                    languageRight = 1;
                    frameRight.setImageResource(R.drawable.russia);
                }
            }
        });

        englishFlag.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageLeft == 2 || languageRight == 2) return;
                if (languageLeft == -1) {
                    languageLeft = 2;
                    frameLeft.setImageResource(R.drawable.england);
                } else {
                    languageRight = 2;
                    frameRight.setImageResource(R.drawable.england);
                }
            }
        });

        chineseFlag.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageLeft == 3 || languageRight == 3) return;
                if (languageLeft == -1) {
                    languageLeft = 3;
                    frameLeft.setImageResource(R.drawable.china);
                } else {
                    languageRight = 3;
                    frameRight.setImageResource(R.drawable.china);
                }
            }
        });

        arrows.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageRight == -1) frameLeft.setImageResource(R.drawable.frame);
                if (languageRight == 1) frameLeft.setImageResource(R.drawable.russia);
                if (languageRight == 2) frameLeft.setImageResource(R.drawable.england);
                if (languageRight == 3) frameLeft.setImageResource(R.drawable.china);

                if (languageLeft == -1) frameRight.setImageResource(R.drawable.frame);
                if (languageLeft == 1) frameRight.setImageResource(R.drawable.russia);
                if (languageLeft == 2) frameRight.setImageResource(R.drawable.england);
                if (languageLeft == 3) frameRight.setImageResource(R.drawable.china);

                int tmp = languageLeft;
                languageLeft = languageRight;
                languageRight = tmp;
            }
        });

        frameLeft.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageLeft = -1;
                frameLeft.setImageResource(R.drawable.frame);
            }
        });

        frameRight.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageRight = -1;
                frameRight.setImageResource(R.drawable.frame);
            }
        });

        setLanguage(systemLanguage);

        mode_text[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_text[1].setText("OK");
                mode_text[3].setText("--");
            }
        });

        mode_text[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mode_text[3].setText("OK");
                mode_text[1].setText("--");
            }
        });


        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok1.getText().equals("OK")) {
                    ok1.setText("--");
                    isSelect[1] = false;
                } else {
                    ok1.setText("OK");
                    isSelect[1] = true;
                }
                dataBase.changeOK("Животные");
            }
        });

        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok2.getText().equals("OK")) {
                    ok2.setText("--");
                    isSelect[2] = false;
                } else {
                    ok2.setText("OK");
                    isSelect[2] = true;
                }
                dataBase.changeOK("В доме");
            }
        });

        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok3.getText().equals("OK")) {
                    ok3.setText("--");
                    isSelect[3] = false;
                } else {
                    ok3.setText("OK");
                    isSelect[3] = true;
                }
                dataBase.changeOK("Покупки");
            }
        });

        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok4.getText().equals("OK")) {
                    ok4.setText("--");
                    isSelect[4] = false;
                } else {
                    ok4.setText("OK");
                    isSelect[4] = true;
                }
                dataBase.changeOK("Спорт и здоровье");
            }
        });

        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok5.getText().equals("OK")) {
                    ok5.setText("--");
                    isSelect[5] = false;
                } else {
                    ok5.setText("OK");
                    isSelect[5] = true;
                }
                dataBase.changeOK("Продукты");
            }
        });

        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok6.getText().equals("OK")) {
                    ok6.setText("--");
                    isSelect[6] = false;
                } else {
                    ok6.setText("OK");
                    isSelect[6] = true;
                }
                dataBase.changeOK("Любовь и отношения");
            }
        });

        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok7.getText().equals("OK")) {
                    ok7.setText("--");
                    isSelect[7] = false;
                } else {
                    ok7.setText("OK");
                    isSelect[7] = true;
                }
                dataBase.changeOK("В городе");
            }
        });

        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok8.getText().equals("OK")) {
                    ok8.setText("--");
                    isSelect[8] = false;
                } else {
                    ok8.setText("OK");
                    isSelect[8] = true;
                }
                dataBase.changeOK("Путешествия");
            }
        });

        item9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok9.getText().equals("OK")) {
                    ok9.setText("--");
                    isSelect[9] = false;
                } else {
                    ok9.setText("OK");
                    isSelect[9] = true;
                }
                dataBase.changeOK("В ресторане");
            }
        });

        item10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ok10.getText().equals("OK")) {
                    ok10.setText("--");
                    isSelect[10] = false;
                } else {
                    ok10.setText("OK");
                    isSelect[10] = true;
                }
                dataBase.changeOK("Работа");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (languageLeft == -1 || languageRight == -1) {
                    Toast toast = Toast.makeText(MainActivity.this, getSMS(systemLanguage), 3000);
                    toast.show();
                    return;
                }
                boolean flag = false;
                for (int i = 1; i <= 10; i++)
                    if (isSelect[i] == true)
                        flag = true;
                if (flag == false) {
                    Toast toast = Toast.makeText(MainActivity.this, getSMS2(systemLanguage), 3000);
                    toast.show();
                    return;
                }
                Intent intent;
                if (mode_text[1].getText().toString().equals("OK"))
                    intent = new Intent(MainActivity.this, WorkActivity.class);
                else
                    intent = new Intent(MainActivity.this, WorkActivity2.class);

                intent.putExtra("left", new Integer(languageLeft).toString());
                intent.putExtra("right", new Integer(languageRight).toString());
                intent.putExtra("language", new Integer(systemLanguage).toString());

                startActivity(intent);
            }
        });
    }

    private String getSMS(int n) {
        if (n == 1) return "Выберите языки для обучения!";
        else if (n == 2) return "Select the languages ​​to learning!";
        else return "选择的语言来学习！";
    }

    private String getSMS2(int n) {
        if (n == 1) return "Выберите темы для изучения!";
        else if (n == 2) return "Select topics for study!";
        else return "选择课题研究！";
    }

    private void setLanguage(int systemLanguage) {
        TextView russiaName = (TextView) findViewById(R.id.russia_text_view);
        TextView englandName = (TextView) findViewById(R.id.english_text_view);
        TextView chinaName = (TextView) findViewById(R.id.chinese_text_view);
        TextView selectItems = (TextView) findViewById(R.id.select_items);

        if (systemLanguage == 1) {
            russiaName.setText("Россия");
            englandName.setText("Великобритания");
            chinaName.setText("Китай");
            selectItems.setText("Выберите темы для обучения");
            item1.setText("Животные");
            item2.setText("В доме");
            item3.setText("Покупки");
            item4.setText("Спорт и здоровье");
            item5.setText("Продукты");
            item6.setText("Любовь и отношения");
            item7.setText("В городе");
            item8.setText("Путешествия");
            item9.setText("В ресторане");
            item10.setText("Работа");
            button.setText("Начать обучение");
            modeText.setText("Выберите режим обучения");
            mode_text[0].setText("1 картинка - 1 слово"); mode_text[1].setText("--");
            mode_text[2].setText("1 картинка - 4 слова"); mode_text[3].setText("--");
        } else
        if (systemLanguage == 2) {
            russiaName.setText("Russia");
            englandName.setText("United Kingdom");
            chinaName.setText("China");
            selectItems.setText("Select the topics for training");
            item1.setText("Animals");
            item2.setText("House");
            item3.setText("Buy");
            item4.setText("Sport and Wellness");
            item5.setText("Products");
            item6.setText("Love and Relationships");
            item7.setText("In City");
            item8.setText("Travel");
            item9.setText("In Restaurant");
            item10.setText("Job");
            button.setText("Start learning");
            modeText.setText("Select learning mode");
            mode_text[0].setText("1 Picture - 1 word"); mode_text[1].setText("--");
            mode_text[2].setText("1 Picture - 4 words"); mode_text[3].setText("--");
        } else
        if (systemLanguage == 3) {
            russiaName.setText("俄國");
            englandName.setText("聯合王國");
            chinaName.setText("中國");
            selectItems.setText("選擇主題培訓");
            item1.setText("动物");
            item2.setText("房子");
            item3.setText("购物");
            item4.setText("体育与健康");
            item5.setText("产品展示");
            item6.setText("爱情和关系");
            item7.setText("在城市");
            item8.setText("旅游");
            item9.setText("餐厅");
            item10.setText("工作");
            button.setText("开始学习");
            modeText.setText("选择学习模式");
            mode_text[0].setText("图片1 - 1个字"); mode_text[1].setText("--");
            mode_text[2].setText("图片1 - 4个字"); mode_text[3].setText("--");
        }
        if (dataBase.isEmptyItemsTable()) {
            dataBase.insertItem("Животные", "Animals", "动物");
            dataBase.insertItem("В доме", "House", "房子");
            dataBase.insertItem("Покупки", "Buy", "购物");
            dataBase.insertItem("Спорт и здоровье", "Sport and Wellness","体育与健康");
            dataBase.insertItem("Продукты", "Products", "产品展示");
            dataBase.insertItem("Любовь и отношения", "Love and Relationships", "爱情和关系");
            dataBase.insertItem("В городе", "In City", "在城市");
            dataBase.insertItem("Путешествия", "Travel", "旅游");
            dataBase.insertItem("В ресторане", "In Restaurant", "餐厅");
            dataBase.insertItem("Работа", "Job", "工作");
        }

        if (dataBase.getOK("Животные").equals("OK")) isSelect[1] = true;
        if (dataBase.getOK("В доме").equals("OK")) isSelect[2] = true;
        if (dataBase.getOK("Покупки").equals("OK")) isSelect[3] = true;
        if (dataBase.getOK("Спорт и здоровье").equals("OK")) isSelect[4] = true;
        if (dataBase.getOK("Продукты").equals("OK")) isSelect[5] = true;
        if (dataBase.getOK("Любовь и отношения").equals("OK")) isSelect[6] = true;
        if (dataBase.getOK("В городе").equals("OK")) isSelect[7] = true;
        if (dataBase.getOK("Путешествия").equals("OK")) isSelect[8] = true;
        if (dataBase.getOK("В ресторане").equals("OK")) isSelect[9] = true;
        if (dataBase.getOK("Работа").equals("OK")) isSelect[10] = true;

        ok1.setText(dataBase.getOK("Животные"));
        ok2.setText(dataBase.getOK("В доме"));
        ok3.setText(dataBase.getOK("Покупки"));
        ok4.setText(dataBase.getOK("Спорт и здоровье"));
        ok5.setText(dataBase.getOK("Продукты"));
        ok6.setText(dataBase.getOK("Любовь и отношения"));
        ok7.setText(dataBase.getOK("В городе"));
        ok8.setText(dataBase.getOK("Путешествия"));
        ok9.setText(dataBase.getOK("В ресторане"));
        ok10.setText(dataBase.getOK("Работа"));

        if (dataBase.isEmptyWordsTable()) {
            dataBase.insertWord("NULL", "+", "-", "-");

            dataBase.insertWord("Животные", "Кот", "Cat", "猫");
            dataBase.insertWord("Животные", "Собака", "Dog", "狗");
            dataBase.insertWord("Животные", "Крокодил", "Crocodile", "鳄鱼");
            dataBase.insertWord("Животные", "Корова", "Cow", "牛");
            dataBase.insertWord("Животные", "Свинья", "Pig", "猪");
            dataBase.insertWord("Животные", "Лошадь", "Horse", "马");
            dataBase.insertWord("Животные", "Хомяк", "Hamster", "仓鼠");
            dataBase.insertWord("Животные", "Тигр", "Tiger", "虎");
            dataBase.insertWord("Животные", "Бегемот", "Hippopotamus", "河马");
            dataBase.insertWord("Животные", "Слон", "Elephant", "象");

            dataBase.insertWord("В доме", "Камин", "Fireplace", "壁炉");
            dataBase.insertWord("В доме", "Кухня", "Kitchen", "厨房");
            dataBase.insertWord("В доме", "Мебель", "Furniture", "家具");
            dataBase.insertWord("В доме", "Кровать", "Bed", "床");
            dataBase.insertWord("В доме", "Телевизор", "TV", "电视");
            dataBase.insertWord("В доме", "Окно", "Window", "窗口");
            dataBase.insertWord("В доме", "Гостинная", "Living", "活");
            dataBase.insertWord("В доме", "Вешалка", "Hanger", "衣架");
            dataBase.insertWord("В доме", "Ванна", "Bath", "浴");
            dataBase.insertWord("В доме", "Шкаф", "Cupboard", "橱柜");

            dataBase.insertWord("Покупки", "Сумочка", "Handbag", "手提包");
            dataBase.insertWord("Покупки", "Кошелек", "Purse", "钱包");
            dataBase.insertWord("Покупки", "Платье", "Dress", "连衣裙");
            dataBase.insertWord("Покупки", "Рубашка", "Shirt", "衬衫");
            dataBase.insertWord("Покупки", "Духи", "Perfume", "香水");
            dataBase.insertWord("Покупки", "Кружка", "Mug", "杯");
            dataBase.insertWord("Покупки", "Шарф", "Scarf", "围巾");
            dataBase.insertWord("Покупки", "Фотоаппарат", "Camera", "相机");
            dataBase.insertWord("Покупки", "Букет цветов", "Bouquet of flowers", "鲜花花束");
            dataBase.insertWord("Покупки", "Компьютер", "Computer", "电脑");

            dataBase.insertWord("Спорт и здоровье", "Футбол", "Football", "足球");
            dataBase.insertWord("Спорт и здоровье", "Баскетбол", "Basketball", "篮球");
            dataBase.insertWord("Спорт и здоровье", "Плаванье", "Swimming", "泳");
            dataBase.insertWord("Спорт и здоровье", "Спортивная ходьба", "Heel-and-toe walk", "脚跟和脚尖行走");
            dataBase.insertWord("Спорт и здоровье", "Фитнес", "Fitness", "健身");
            dataBase.insertWord("Спорт и здоровье", "Йога", "Yoga", "瑜伽");
            dataBase.insertWord("Спорт и здоровье", "Лыжный спорт", "Skiing", "滑雪");
            dataBase.insertWord("Спорт и здоровье", "Здоровое питание", "Healthy Eating", "健康饮食");
            dataBase.insertWord("Спорт и здоровье", "Режим дня", "Day regimen", "天疗程");
            dataBase.insertWord("Спорт и здоровье", "Бег", "Running", "运行");

            dataBase.insertWord("Продукты", "Молоко", "Milk", "牛奶");
            dataBase.insertWord("Продукты", "Фрукты", "Fruit", "水果");
            dataBase.insertWord("Продукты", "Рыба", "Fish", "鱼");
            dataBase.insertWord("Продукты", "Хлеб", "Bread", "面包");
            dataBase.insertWord("Продукты", "Яйца", "Eggs", "鸡蛋");
            dataBase.insertWord("Продукты", "Крупа", "Groats", "粹粒");
            dataBase.insertWord("Продукты", "Сахар", "Sugar", "糖");
            dataBase.insertWord("Продукты", "Чай", "Tea", "茶");
            dataBase.insertWord("Продукты", "Сыр", "Cheese", "奶酪");
            dataBase.insertWord("Продукты", "Мясо", "Meat", "肉");

            dataBase.insertWord("Любовь и отношения", "Пара", "Couple", "一对");
            dataBase.insertWord("Любовь и отношения", "Любовь", "Love", "爱");
            dataBase.insertWord("Любовь и отношения", "Дружба", "Friendship", "友谊");
            dataBase.insertWord("Любовь и отношения", "Доверие", "Trust", "信任");
            dataBase.insertWord("Любовь и отношения", "Ревность", "Jealousy", "妒忌");
            dataBase.insertWord("Любовь и отношения", "Семья", "Family", "家庭");
            dataBase.insertWord("Любовь и отношения", "Свадьба", "Wedding", "婚礼");
            dataBase.insertWord("Любовь и отношения", "Родители", "Parents", "父母");
            dataBase.insertWord("Любовь и отношения", "Забота", "Care", "关怀");
            dataBase.insertWord("Любовь и отношения", "Ребёнок", "Child", "孩子");

            dataBase.insertWord("В городе", "Светофор", "Traffic light", "灯火");
            dataBase.insertWord("В городе", "Улица", "Street", "街头");
            dataBase.insertWord("В городе", "Перекрёсток", "Crossroads", "十字路口");
            dataBase.insertWord("В городе", "Мост", "Bridge", "桥");
            dataBase.insertWord("В городе", "Магазин", "Shop", "店");
            dataBase.insertWord("В городе", "Памятник", "Monument", "纪念碑");
            dataBase.insertWord("В городе", "Парк", "Park", "公园");
            dataBase.insertWord("В городе", "Карта", "Map", "地图");
            dataBase.insertWord("В городе", "Гостиница", "Hotel", "旅馆");
            dataBase.insertWord("В городе", "Больница", "Hospital", "医院");

            dataBase.insertWord("Путешествия", "Перелет", "Flight", "飞行");
            dataBase.insertWord("Путешествия", "Автостоп", "Hitch-hiking", "搭便车远足");
            dataBase.insertWord("Путешествия", "Шоссе", "Highway", "公路");
            dataBase.insertWord("Путешествия", "Пляж", "Beach", "海滩");
            dataBase.insertWord("Путешествия", "Горы", "Mountains", "山");
            dataBase.insertWord("Путешествия", "Море", "Sea", "海");
            dataBase.insertWord("Путешествия", "Лайнер", "Liner", "衬垫");
            dataBase.insertWord("Путешествия", "Палатка", "Tent", "帐篷");
            dataBase.insertWord("Путешествия", "Привал", "Halt", "停止");
            dataBase.insertWord("Путешествия", "Маршрут", "Route", "路线");

            dataBase.insertWord("В ресторане", "Официант", "Waiter", "服务员");
            dataBase.insertWord("В ресторане", "Блюдо", "Dish", "菜");
            dataBase.insertWord("В ресторане", "Заказ", "Order", "顺序");
            dataBase.insertWord("В ресторане", "Счет", "Account", "帐户");
            dataBase.insertWord("В ресторане", "Чаевые", "Tip", "尖");
            dataBase.insertWord("В ресторане", "Барная стойка", "Bar counter", "吧台");
            dataBase.insertWord("В ресторане", "Коктейль", "Cocktail", "鸡尾酒");
            dataBase.insertWord("В ресторане", "Напитки", "Drinks", "饮料");
            dataBase.insertWord("В ресторане", "Меню", "Menu", "菜单");
            dataBase.insertWord("В ресторане", "Вино", "Wine", "酒");

            dataBase.insertWord("Работа", "Рабочий", "Worker", "工作的");
            dataBase.insertWord("Работа", "Завод", "Plant", "厂");
            dataBase.insertWord("Работа", "Компания", "Company", "公司");
            dataBase.insertWord("Работа", "Коллектив", "Collective", "集体");
            dataBase.insertWord("Работа", "Заработная плата", "Wages", "工资");
            dataBase.insertWord("Работа", "Экономист", "Economist", "经济学家");
            dataBase.insertWord("Работа", "Программист", "Programmer", "程序员");
            dataBase.insertWord("Работа", "Стажер", "Trainee", "实习生");
            dataBase.insertWord("Работа", "Генеральный директор", "General manager", "总经理");
            dataBase.insertWord("Работа", "Начальник", "Head", "头");

        }
    }
}
