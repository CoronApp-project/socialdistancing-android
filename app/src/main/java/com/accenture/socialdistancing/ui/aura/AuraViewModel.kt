package com.accenture.socialdistancing.ui.aura

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuraViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is aura Fragment"
    }
    val text: LiveData<String> = _text
}