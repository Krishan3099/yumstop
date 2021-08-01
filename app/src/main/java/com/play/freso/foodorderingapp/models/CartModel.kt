package com.play.freso.foodorderingapp.models

data class CartModel (
    var key:String?= null,
    var name:String?= null,
    var image:String?= null,
    var price:String?= null,
    var quantity: Int = 0,
    var totalPrice: Float = 0f
){

}