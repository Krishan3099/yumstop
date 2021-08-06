package com.play.freso.foodorderingapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.play.freso.foodorderingapp.viewmodels.LoginRegisterViewModel
import kotlinx.android.synthetic.main.login_activity.*
import androidx.lifecycle.ViewModelProvider
import com.play.freso.foodorderingapp.viewmodels.CatItemListViewModel


class LoginActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        supportActionBar?.title = "Account Login"
        val viewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]


        loginButton.setOnClickListener{
            val inputUsername = emailText.text.toString()
            val inputPassword = passwordText.text.toString()

            if(inputUsername.isEmpty() || inputPassword.isEmpty()){

                Toast.makeText(this, "Please input username and password", Toast.LENGTH_SHORT).show()

            }else{

                viewModel.userid.observe(this, {

                    viewModel.signIn(inputUsername, inputPassword)

                    if(it == null){

                        emailText.setText("")
                        passwordText.setText("")
                        passwordText.clearFocus()
                        Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show()

                    }else{

                        val intent = Intent(this, CatItemsActivity::class.java).apply{
                            putExtra("user", it)
                        }

                        startActivity(intent)
                    }
                })
            }
        }

        regButton.setOnClickListener{
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()

        val viewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]
        viewModel.userid.observe(this, {
            if(it != null){
                // reload();
            }
        })

    }

}
