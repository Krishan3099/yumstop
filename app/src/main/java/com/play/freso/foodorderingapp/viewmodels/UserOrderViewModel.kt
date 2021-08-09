package com.play.freso.foodorderingapp.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.play.freso.foodorderingapp.datasource.FirebaseService
import com.play.freso.foodorderingapp.models.OrderItem
import com.play.freso.foodorderingapp.models.User
import kotlinx.coroutines.launch


class UserOrderViewModel() : ViewModel() {
    private val _userProfile = MutableLiveData<User>()
    private val _order = MutableLiveData<MutableMap<String, ArrayList<String>>>()
    val userProfile: LiveData<User> = _userProfile
    val order:LiveData<MutableMap<String, ArrayList<String>>> = _order
    private val _orderItems = MutableLiveData<ArrayList<OrderItem>>()
    val orderItems: LiveData<ArrayList<OrderItem>> = _orderItems


    fun loadCart(userid: String) {
        viewModelScope.launch {
            _userProfile.value = FirebaseService.getProfileData(userid)

            if(_userProfile.value?.orderNumber != null){

                _order.value = FirebaseService.getOrderData(_userProfile.value!!.orderNumber)
            }
        }
    }

    fun loadCartItems(userid: String){
        viewModelScope.launch {
            _userProfile.value = FirebaseService.getProfileData(userid)

            if(_userProfile.value?.orderNumber != null){

                _order.value = FirebaseService.getOrderData(_userProfile.value!!.orderNumber)
                _orderItems.value = _order.value?.let { FirebaseService.getOrderListItems(it) }
            }
        }
    }



    fun addToOrder(food_id: String, quantity: Int) {
        val db = FirebaseFirestore.getInstance()
        Log.d("treeco", "${_userProfile.value?.orderNumber}")
        if(_userProfile.value?.orderNumber != null){
            Log.d("wtfman", _order.value.toString())
            var arr: ArrayList<String> = arrayListOf("/food_items/$food_id", ((_order.value?.get(food_id)?.get(1)?.toInt() ?: 0) + quantity).toString())
            _order.value!![food_id] = arr

            db.collection("orders")
                .document(_userProfile.value!!.orderNumber)
                .set(_order.value!!)

        }else{

        }

    }


    //UPDATE _ORDER TOO !!!

    fun plusOrderItem(position: Int){
        val foodId = _orderItems.value!![position].key
        val quant = _orderItems.value!![position].quantity + 1
        _orderItems.value!![position].quantity += 1
        _orderItems.value!![position].totalPrice = _orderItems.value!![position].quantity* _orderItems.value!![position].price.toFloat()
        _order.value!![foodId]!![1] = quant.toString()
    }

    fun minusOrderItem(position: Int){
        if(_orderItems.value!![position].quantity > 1)
        {
            val foodId = _orderItems.value!![position].key
            val quant = _orderItems.value!![position].quantity - 1
            _orderItems.value!![position].quantity -= 1
            _orderItems.value!![position].totalPrice = _orderItems.value!![position].quantity * _orderItems.value!![position].price.toFloat()
            _order.value!![foodId]!![1] = quant.toString()
        }
    }

    fun deleteOrderItem(position: Int) {
        val foodId = _orderItems.value!![position].key
        _orderItems.value!!.removeAt(position)
        _order.value!!.remove(foodId)
    }

    fun updateFirestoreOrder() {
        val db = FirebaseFirestore.getInstance()
        if(!_orderItems.value.isNullOrEmpty()){
            _orderItems.value!!.forEach { _ ->
                db.collection("orders")
                    .document(_userProfile.value!!.orderNumber)
                    .set(_order.value!!)
            }
        }
    }
}
