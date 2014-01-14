package ru.ifmo.ctddev.flashcards;

import ru.ifmo.ctddev.flashcards.cards.Language;


public class WordToTranslationActivity extends FourVariantsCardActivity {
    @Override
    Language getTitleLanguage() {
        return Constants.LEARNING_LANGUAGE;
    }

    @Override
    Language getQuestionableLanguage() {
        return Constants.NATIVE_LANGUAGE;
    }

    @Override
    String getImageSubFolder() {
        return Constants.BLURED_IMAGE_SUBFOLDER;
    }
}
