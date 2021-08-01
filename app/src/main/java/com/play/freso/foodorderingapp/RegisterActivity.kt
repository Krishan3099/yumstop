package com.play.freso.foodorderingapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.play.freso.foodorderingapp.datasource.FoodDataSource
import com.play.freso.foodorderingapp.datasource.UserDataSource
import kotlinx.android.synthetic.main.register_activity.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth


    private val creds = UserDataSource.createDataSet()

//    private val username by lazy{
//        findViewById<EditText>(R.id.editTextTextPersonName)
//    }
//    private val password by lazy{
//        findViewById<EditText>(R.id.editTextTextPassword)
//    }
//
//    private val re_password by lazy{
//        findViewById<EditText>(R.id.editTextTextPassword2)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register an Account"

        auth = Firebase.auth


        emailText.addTextChangedListener(object:TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()){
                    regButton.isEnabled = true
                }else{
                    regButton.isEnabled = false
                    emailText.error = "Invalid Email"
                }
            }

        })


        regButton.setOnClickListener{
            val input_username = emailText.text.toString()
            val input_password = passwordText.text.toString()
            val re_input_password = passwordText2.text.toString()

            if(input_username.isEmpty() || input_password.isEmpty() || re_input_password.isEmpty()){
                Toast.makeText(this, "Please input all fields", Toast.LENGTH_SHORT).show()
            }else if(input_password != re_input_password){
                Toast.makeText(this, "Passwords do not match. Please re-input.", Toast.LENGTH_SHORT).show()
                passwordText.setText("")
                passwordText2.setText("")

                passwordText.requestFocus()
            }else{
                createAccount(input_username, input_password)
            }
        }

    }

    private fun createAccount(username: String, pass: String){
        auth.createUserWithEmailAndPassword(username, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("user_auth", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, CatItemsActivity::class.java).apply{
//                        putExtra(user.)
                    }
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("user_auth", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}