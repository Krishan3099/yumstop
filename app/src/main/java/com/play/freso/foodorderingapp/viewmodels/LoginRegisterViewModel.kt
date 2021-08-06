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

            //_posts.value = FirebaseService.getPosts()
        }
    }



    fun signUp(user: String, pass: String){
        viewModelScope.launch {
            _userid.value = FireAuth.signupUser(user, pass)

            //_posts.value = FirebaseService.getPosts()
        }


    }

    //        auth.signInWithEmailAndPassword(user, pass)
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    // Sign in success, update UI with the signed-in user's information
//                    Log.d("user_auth", "signInWithEmail:success")
//                    val userid = auth.currentUser?.uid
//                    val intent = Intent(this, CatItemsActivity::class.java).apply{
//                        putExtra("user", userid)
//                    }
//                    startActivity(intent)
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Log.w("user_auth", "signInWithEmail:failure", task.exception)
//                    //fail
//                    emailText.setText("")
//                    passwordText.setText("")
//                    passwordText.clearFocus()
//                    Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
//                }
//            }




//    auth.createUserWithEmailAndPassword(username, pass)
//    .addOnCompleteListener(this) { task ->
//        if (task.isSuccessful) {
//            // Sign in success, update UI with the signed-in user's information
//            Log.d("user_auth", "createUserWithEmail:success")
//            val user = auth.currentUser
//            Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, CatItemsActivity::class.java).apply{
////                        putExtra(user.)
//            }
//            startActivity(intent)
//        } else {
//            // If sign in fails, display a message to the user.
//            Log.w("user_auth", "createUserWithEmail:failure", task.exception)
//            Toast.makeText(baseContext, "Authentication failed.",
//                Toast.LENGTH_SHORT).show()
//        }
//    }
}