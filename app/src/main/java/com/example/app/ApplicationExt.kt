package com.example.app

import android.app.Application
import dagger.internal.DaggerCollections

class ApplicationExt : Application() {
    var myComponent: MyComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
//        myComponent = createMyComponent()
    }

    private fun createMyComponent(): MyComponent {
//        return DaggerMyComponent.builder()
//                .myModule(MyModule())
//                .build()
        return createMyComponent()
    }

}
