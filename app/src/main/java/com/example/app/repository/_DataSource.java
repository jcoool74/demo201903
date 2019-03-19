package com.example.app.repository;

import com.example.app.room.Word;

import io.reactivex.Flowable;

public interface _DataSource {
    Flowable<Word> getWord();
    void insertOrUpdateWord(Word word);
    void deleteAllWords();
}
