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
import kotlinx.coroutines.tasks.await


class UserOrderViewModel() : ViewModel() {
    private val _userProfile = MutableLiveData<User>()
    private val _order = MutableLiveData<MutableMap<String, ArrayList<String>>>()
    val userProfile: LiveData<User> = _userProfile
    val order:LiveData<MutableMap<String, ArrayList<String>>> = _order
    private val _orderItems = MutableLiveData<List<OrderItem>>()
    val orderItems: LiveData<List<OrderItem>> = _orderItems


    fun loadCart(userid: String) {
        viewModelScope.launch {
            _userProfile.value = FirebaseService.getProfileData(userid)

            if(_userProfile.value?.orderNumber ?: null != null){

                _order.value = FirebaseService.getOrderData(_userProfile.value!!.orderNumber)
            }
        }
    }

//    fun loadUser(userid: String) {
//        viewModelScope.launch {
//            _userProfile.value = FirebaseService.getProfileData(userid)
//            Log.d("pichu", "${_userProfile.value?.userid}")
//            Log.d("pichu", "${_userProfile.value?.orderNumber}")
//            //_posts.value = FirebaseService.getPosts()
//        }
//    }

    fun loadCartItems(userid: String){
        viewModelScope.launch {
            _userProfile.value = FirebaseService.getProfileData(userid)

            if(_userProfile.value?.orderNumber ?: null != null){

                _order.value = FirebaseService.getOrderData(_userProfile.value!!.orderNumber)
                _orderItems.value = _order.value?.let { FirebaseService.getOrderListItems(it) }
            }
        }
    }



    fun addToOrder(food_id: String, quantity: Int) {
        val db = FirebaseFirestore.getInstance()
        Log.d("treeco", "${_userProfile.value?.orderNumber}")
        if(_userProfile.value?.orderNumber ?: null != null){
            Log.d("wtfman", _order.value.toString())
            var arr: ArrayList<String> = arrayListOf("/food_items/$food_id", ((_order.value?.get(food_id)?.get(1)?.toInt() ?: 0) + quantity).toString())
            _order.value!!.put(food_id, arr)

            db.collection("orders")
                .document(_userProfile.value!!.orderNumber)
                .set(_order.value!!)

        }else{

        }

    }

    fun updateFirestore() {

    }
}
