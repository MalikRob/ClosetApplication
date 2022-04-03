package com.example.closet.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

import com.example.closet.R
import com.example.closet.database.UserDAO
import com.example.closet.database.UserDatabase


class RegisterActivity : AppCompatActivity() {
    var editTextUsername: EditText? = null
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var editTextCnfPassword: EditText? = null
    var buttonRegister: Button? = null
    var textViewLogin: TextView? = null
    private var userDao: UserDAO? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_page)
        editTextUsername = findViewById(R.id.editTextUsername)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextCnfPassword = findViewById(R.id.editTextCnfPassword)
        buttonRegister = findViewById(R.id.buttonRegister)
        textViewLogin = findViewById(R.id.textViewLogin)
        textViewLogin.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@RegisterActivity,
                    LoginPage::class.java
                )
            )
        })
        userDao = Room.databaseBuilder(this, UserDatabase::class.java, "mi-database.db")
            .allowMainThreadQueries()
            .build().getUserDao()
        buttonRegister.setOnClickListener(View.OnClickListener {
            val userName = editTextUsername.getText().toString().trim { it <= ' ' }
            val email = editTextEmail.getText().toString().trim { it <= ' ' }
            val password = editTextPassword.getText().toString().trim { it <= ' ' }
            val passwordConf = editTextCnfPassword.getText().toString().trim { it <= ' ' }
            if (password == passwordConf) {
                val user = User(userName, password, email)
                userDao.insert(user)
                val moveToLogin = Intent(this@RegisterActivity, LoginPage::class.java)
                startActivity(moveToLogin)
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "Password is not matching",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
