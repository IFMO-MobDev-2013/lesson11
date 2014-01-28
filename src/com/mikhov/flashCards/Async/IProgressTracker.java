package com.mikhov.flashCards.Async;

public interface IProgressTracker {
    void onProgress(String message);
    void onComplete();
}