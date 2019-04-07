package com.example.app

import com.example.app.ui.MainActivity

import javax.inject.Singleton

import dagger.Component

@Singleton
@Component(modules = [MyModule::class])
interface MyComponent {
    fun inject(mainActivity: MainActivity)
}
