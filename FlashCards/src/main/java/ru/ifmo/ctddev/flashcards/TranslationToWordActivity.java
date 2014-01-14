package ru.ifmo.ctddev.flashcards;

import ru.ifmo.ctddev.flashcards.cards.Language;

public class TranslationToWordActivity extends FourVariantsCardActivity {

    @Override
    Language getTitleLanguage() {
        return Constants.NATIVE_LANGUAGE;
    }

    @Override
    Language getQuestionableLanguage() {
        return Constants.LEARNING_LANGUAGE;
    }

    @Override
    String getImageSubFolder() {
        return "";
    }
}
