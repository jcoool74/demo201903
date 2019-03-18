package com.example.app.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

//https://github.com/googlesamples/android-architecture-components/tree/master/BasicRxJavaSample
/**
 * The Room database that contains the Users table
 */
@Database(entities = {Word.class}, version = 1)
public abstract class UsersDatabase extends RoomDatabase {

    private static volatile UsersDatabase INSTANCE;

    public abstract WordDao userDao();

    public static UsersDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UsersDatabase.class, "word_database.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
