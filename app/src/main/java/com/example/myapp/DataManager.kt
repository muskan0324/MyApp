package com.example.myapp

import androidx.compose.runtime.mutableStateOf

object DataManager {
    var currentPage = mutableStateOf(Pages.FIRST)

    enum class Pages{
        FIRST,
        SECOND,
        THIRD
    }
}