package ru.ifmo.ctddev.flashcards.cards;

public enum Language {
    ENG {
        @Override
        public String getName() {
            return "en";
        }
    },
    FRA {
        @Override
        public String getName() {
            return "fr";
        }
    },
    RUS {
        @Override
        public String getName() {
            return "ru";
        }
    };

    public abstract String getName();
}
