package com.accenture.socialdistancing.ui.trustedsources

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrustedSourcesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is trusted sources Fragment"
    }
    val text: LiveData<String> = _text
}