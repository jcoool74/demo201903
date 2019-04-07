package com.example.app

import com.example.app.MyExample
import com.example.app.MyExampleImpl

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

@Module
class MyModule {
    @Provides
    @Singleton
    internal fun provideMyExample(): MyExample {
        return MyExampleImpl()
    }
}
