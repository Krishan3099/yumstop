package com.play.freso.foodorderingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.play.freso.foodorderingapp.viewmodels.LoginRegisterViewModel
import kotlinx.android.synthetic.main.register_activity.*
import kotlinx.android.synthetic.main.register_activity.emailText
import kotlinx.android.synthetic.main.register_activity.passwordText
import kotlinx.android.synthetic.main.register_activity.regButton

class RegisterActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Register an Account"
        val sharedPreference: SharedPreferences =  getSharedPreferences("USER_INFO", Context.MODE_PRIVATE)
        val viewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]


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
            val inputUsername = emailText.text.toString()
            val inputPassword = passwordText.text.toString()
            val reInputPassword = passwordText2.text.toString()

            if(inputUsername.isEmpty() || inputPassword.isEmpty() || reInputPassword.isEmpty()){
                Toast.makeText(this, "Please input all fields", Toast.LENGTH_SHORT).show()
            }else if(inputPassword != reInputPassword){
                Toast.makeText(this, "Passwords do not match. Please re-input.", Toast.LENGTH_SHORT).show()
                passwordText.setText("")
                passwordText2.setText("")

                passwordText.requestFocus()
            }else{
                viewModel.userid.observe(this, {

                    viewModel.signUp(inputUsername, inputPassword)

                    if(it == null){
                        Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                    }else{

                        Toast.makeText(this, "Registered", Toast.LENGTH_SHORT).show()
                        sharedPreference.edit().apply{
                            putString("uid", it)
                            apply()
                        }

                        val intent = Intent(this, CatItemsActivity::class.java).apply{
                            putExtra("user", it)
                        }

                        startActivity(intent)
                    }
                })
            }
        }

    }
}