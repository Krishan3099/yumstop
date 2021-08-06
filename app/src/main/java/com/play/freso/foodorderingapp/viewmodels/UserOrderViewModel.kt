package com.play.freso.foodorderingapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.play.freso.foodorderingapp.datasource.FirebaseService
import com.play.freso.foodorderingapp.models.FoodItem
import com.play.freso.foodorderingapp.models.OrderItem
import com.play.freso.foodorderingapp.models.User
import kotlinx.coroutines.launch


class UserOrderViewModel() : ViewModel() {
    private val _userProfile = MutableLiveData<User>()
    private val _order = MutableLiveData<MutableMap<String, Array<String>>>()
    val userProfile: LiveData<User> = _userProfile
    val order:LiveData<MutableMap<String, Array<String>>> = _order
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems


    fun loadCart(userid: String) {
        viewModelScope.launch {
            if(_userProfile.value?.orderNumber ?: null != null){
                _order.value = FirebaseService.getOrderData(_userProfile.value!!.orderNumber)
            }
        }
    }
//Rest of your viewmodel

    fun loadUser(userid: String) {
        viewModelScope.launch {
            _userProfile.value = FirebaseService.getProfileData(userid)
            //_posts.value = FirebaseService.getPosts()
        }
    }

    fun loadCartItems(userid: String){
        loadCart(userid)
        if(_userProfile.value?.orderNumber ?: null != null){
            viewModelScope.launch {
                _orderItems.value = _order.value?.let { FirebaseService.getOrderListItems(it) }
                //_posts.value = FirebaseService.getPosts()
            }
        }

    }


//    fun editOrder() {
//        if(){
//
//        }else{
//
//        }
//    }


    fun addToOrder(food_id: String, quantity: Int) {
        val db = FirebaseFirestore.getInstance()
        if(_userProfile.value?.orderNumber ?: null != null){
            _order.value!![food_id]?.set(0, "/food_items/$food_id")
            _order.value!![food_id]?.set(1, ((_order.value!![food_id]?.get(1)?.toInt() ?: 0) + quantity).toString())

            db.collection("orders")
                .document(_userProfile.value!!.orderNumber)
                .update(_order.value!! as Map<String, Any>)
                .addOnSuccessListener {
                    Log.d("add_to_cart", "DocumentSnapshot successfully written!")
                }
                .addOnFailureListener{
                    Log.d("add_to_cart", "fail to cart!")
                }

        }else{



        }





    }

    fun updateFirestore() {

    }
}
