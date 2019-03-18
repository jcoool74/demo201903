package com.example.app.room;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public interface  UserDataSource {
    Flowable<Word> getWord();
    void insertOrUpdateWord(Word word);
    void deleteAllWords();
}
