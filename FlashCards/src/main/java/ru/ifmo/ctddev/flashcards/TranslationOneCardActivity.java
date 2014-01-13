package ru.ifmo.ctddev.flashcards;

import ru.ifmo.ctddev.flashcards.cards.Card;
import ru.ifmo.ctddev.flashcards.cards.Language;

public class TranslationOneCardActivity extends OneCardActivity {
    @Override
    void onBeginState(Card card) {
        getWord().setText(card.getTitle(Constants.NATIVE_LANGUAGE));
        getTranslation().setText("");
        setImage(card.getTitle(Language.ENG).toLowerCase());
    }

    @Override
    void onEndState(Card card) {
        getTranslation().setText(card.getTitle(Constants.LEARNING_LANGUAGE));
    }
}
