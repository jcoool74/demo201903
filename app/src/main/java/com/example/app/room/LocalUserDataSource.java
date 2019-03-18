package com.example.app.room;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class LocalUserDataSource implements UserDataSource {
    private WordDao wordDao;

    public LocalUserDataSource(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @Override
    public Flowable<Word> getWord() {
        return wordDao.getWord();
    }

    @Override
    public void insertOrUpdateWord(Word word) {
        wordDao.insert(word);
    }

    @Override
    public void deleteAllWords() {
    }
}
