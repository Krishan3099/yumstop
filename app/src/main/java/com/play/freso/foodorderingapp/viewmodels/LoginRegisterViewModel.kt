package com.play.freso.foodorderingapp.viewmodels

import androidx.lifecycle.*
import com.play.freso.foodorderingapp.datasource.FireAuth
import kotlinx.coroutines.launch

class LoginRegisterViewModel : ViewModel() {

    private val _userid = MutableLiveData<String>()
    val userid: LiveData<String> = _userid


    fun signIn(user: String, pass: String){
        viewModelScope.launch {
            _userid.value = FireAuth.signinUser(user, pass)

        }
    }


    fun signUp(user: String, pass: String){
        viewModelScope.launch {
            _userid.value = FireAuth.signupUser(user, pass)

        }


    }
}