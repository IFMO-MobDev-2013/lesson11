package ru.ifmo.ctddev.flashcards;

import java.util.Locale;

import ru.ifmo.ctddev.flashcards.cards.Language;

public class Constants {

    public static final int START_RATING = 3;
    public static final int MAX_RATING = 7;
    public static final String BLURED_IMAGE_SUBFOLDER = "blured";
    public static final String SMALL_IMAGE_SUBFOLDER = "small";
    public static final String INTENT_CATEGORY = "category";
    public static final String IMAGE_ASSETS = "images";
    public static final String CATEGORY_ASSETS = "categories";
    public static final Language NATIVE_LANGUAGE;
    public static Language LEARNING_LANGUAGE = Language.FRA;

    static {
        Language lang = Language.ENG;
        for (Language l : Language.values()) {
            if (Locale.getDefault().getLanguage().equals(l.getName())) {
                lang = l;
            }
        }
        NATIVE_LANGUAGE = lang;
    }
}
