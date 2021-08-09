package com.play.freso.foodorderingapp.datasource

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
    
}