package com.rorpage.purtyweather.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {
    private val mText: MutableLiveData<String> = MutableLiveData()
    val text: LiveData<String>
        get() = mText

    fun setText(text: String) {
        mText.value = text
    }

    init {
        mText.value = "This is home fragment"
    }
}