package com.play.freso.foodorderingapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.login_activity.*


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        supportActionBar?.title = "Account Login"

        auth = Firebase.auth


        loginButton.setOnClickListener{
            val inputUsername = emailText.text.toString()
            val inputPassword = passwordText.text.toString()

            if(inputUsername.isEmpty() || inputPassword.isEmpty()){
                Toast.makeText(this, "Please input username and password", Toast.LENGTH_SHORT).show()
            }else{
                signIn(inputUsername, inputPassword)
            }
        }

        regButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            // reload();

        }
    }


    private fun signIn(user: String, pass: String){
        auth.signInWithEmailAndPassword(user, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("user_auth", "signInWithEmail:success")
                    val userid = auth.currentUser?.uid
                    val intent = Intent(this, CatItemsActivity::class.java).apply{
                        putExtra("user", userid)
                    }
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("user_auth", "signInWithEmail:failure", task.exception)
                    //fail
                    emailText.setText("")
                    passwordText.setText("")
                    passwordText.clearFocus()
                    Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
