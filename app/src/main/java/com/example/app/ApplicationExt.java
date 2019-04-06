package com.example.app;

import android.app.Application;

public class ApplicationExt extends Application {
    private MyComponent mMyComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        mMyComponent = createMyComponent();
    }

    public MyComponent getMyComponent() {
        return mMyComponent;
    }

    private MyComponent createMyComponent() {
        return DaggerMyComponent
                .builder()
                .myModule(new MyModule())
                .build();
    }

}
