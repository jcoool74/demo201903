package com.example.app.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

//https://developer.android.com/training/data-storage/room/accessing-data#java
//https://developer.android.com/topic/libraries/architecture/room
@Dao
    public interface WordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Word word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWords(Word... words);

    @Query("DELETE FROM word_table")
    void deleteAll();

    @Query("SELECT * from word_table LIMIT 1")
    Flowable<Word> getWord();

    @Query("SELECT * from word_table ORDER BY word ASC")
    List<Word> getAllWords();

//    @Query("SELECT * from word_table ORDER BY word ASC")
//    LiveData<List<Word>> getAllWords();
}