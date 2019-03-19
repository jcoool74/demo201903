package com.example.app;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.app.apple.AppleActivity;
import com.example.app.room.Injection;
import com.example.app.room.ViewModelFactory;
import com.example.app.room.WordViewModel;
import com.example.app.tab.ActivityTabLayout;
import com.example.app.ui.TestActivity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "_MainActivity_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (true) {
            if (false) {
                startActivity(new Intent(this, ListActivity.class));
            } else {
                startActivity(new Intent(this, TestActivity.class));
            }
        } else {
            if (false) {
                startActivity(new Intent(this, ActivityTabLayout.class));
            } else {
                if (false) {
                    startActivity(new Intent(this, DetailActivity.class));
                } else {
                    startActivity(new Intent(this, AppleActivity.class));
                }
            }
        }

        testRoom();
    }

    private void testRoom() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();

        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(this);
        WordViewModel wordViewModel = ViewModelProviders.of(this, viewModelFactory).get(WordViewModel.class);


//        Disposable subscribe2 =
                wordViewModel.updateUserName("jay"); //.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
//                    Log.d(Config.TAG, "ok-here: " + "done");
//                }, err -> {
//                    Log.d(Config.TAG, "err-here: " + err.getMessage());
//                }
//        );

        if (true) {
            Disposable subscribe = wordViewModel.getWordName().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(user -> {
                Log.d(Config.TAG, "ok-here: " + user.getWord());
            }, error -> {
                Log.d(Config.TAG, "err-here: " + error.getMessage());
            });
            compositeDisposable.add(subscribe);
        }

    }

}
