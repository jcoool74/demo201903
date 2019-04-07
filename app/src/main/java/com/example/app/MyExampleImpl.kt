package com.example.app

import java.util.Date

internal class MyExampleImpl : MyExample {

    override val date: Long

    init {
        date = Date().time
    }

}