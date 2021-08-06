package com.play.freso.foodorderingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.play.freso.foodorderingapp.datasource.FirebaseService
import com.play.freso.foodorderingapp.models.User
import kotlinx.coroutines.launch

class CatItemListViewModel: ViewModel() {
    private val _cats = MutableLiveData<List<String>>()
    val cats: LiveData<List<String>> = _cats


    //private val _posts = MutableLiveData<List<Post>>()
    //val posts: LiveData<List<Post>> = _posts

    init {
        viewModelScope.launch {
            _cats.value = FirebaseService.getCategories()
        }
    }
}