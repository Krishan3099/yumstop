package com.play.freso.foodorderingapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.play.freso.foodorderingapp.datasource.FirebaseService
import com.play.freso.foodorderingapp.models.FoodItem
import kotlinx.coroutines.launch

class FoodItemListViewModel: ViewModel() {
    private val _items = MutableLiveData<List<FoodItem>>()
    val items: LiveData<List<FoodItem>> = _items

    fun getItems(category: String){
        viewModelScope.launch {
            _items.value = FirebaseService.getFoodItems(category)
        }
    }
}