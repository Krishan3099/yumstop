package com.play.freso.foodorderingapp.datasource

import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.CatItemsActivity
import com.play.freso.foodorderingapp.models.User
import com.play.freso.foodorderingapp.models.User.Companion.toUser
import kotlinx.coroutines.tasks.await
import java.lang.Exception

object FireAuth {
    private const val TAG = "FirebaseAuth"
    
    
    suspend fun signinUser(user: String, pass:String): String? {
        val auth = Firebase.auth
        return try{
            auth.signInWithEmailAndPassword(user, pass).await()
            auth.currentUser?.uid
        } catch (e: Exception){
            Log.e(TAG, "Error authenticating user", e)
            null
        }
    }
    
    suspend fun signupUser(user: String, pass: String): String? {
        val auth = Firebase.auth


        return try{
            auth.createUserWithEmailAndPassword(user, pass).await()
            auth.currentUser?.uid
        } catch (e: Exception){
            Log.e(TAG, "Error authenticating user", e)
            null
        }
    }

    //maybe this or maybe just have another dao for orders
    suspend fun getOrderData(userId: String): User? {
        TODO()
    }


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