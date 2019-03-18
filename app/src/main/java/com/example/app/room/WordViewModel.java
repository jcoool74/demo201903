package com.example.app.room;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.example.app.Config;

import java.util.concurrent.Callable;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

public class WordViewModel extends ViewModel {
    private final UserDataSource mDataSource;
    private Word mWord;

    public WordViewModel(UserDataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    public Flowable<Word> getWordName() {
        return mDataSource.getWord();
    }

    public void updateUserName(final String userName) {
        mWord = (mWord != null) ? mWord : new Word(userName);
        Completable.fromRunnable(() -> {
                    Log.d(Config.TAG, "insertOrUpdateWord");
                    mDataSource.insertOrUpdateWord(mWord);
                    Log.d(Config.TAG, "insertOrUpdateWord");
                }
        ).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe();

    }
}
