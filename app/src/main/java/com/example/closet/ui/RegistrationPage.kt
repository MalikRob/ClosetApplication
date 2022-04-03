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
import com.example.closet.LoginPage

import com.example.closet.R
import com.example.closet.database.UserDAO
import com.example.closet.database.UserDatabase


class RegistrationPage : AppCompatActivity() {
    private lateinit var editTextUsername: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextCnfPassword: EditText
    private lateinit var buttonRegister: Button
    private lateinit var textViewLogin: TextView
    private lateinit var dataBase: UserDAO
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
                    this@RegistrationPage,
                    LoginPage::class.java
                )
            )
        })
        dataBase = Room.databaseBuilder(this, UserDatabase::class.java, "mi-database.db")
            .allowMainThreadQueries()
            .build().UserDAO!!

        buttonRegister.setOnClickListener(View.OnClickListener {
            val userName = editTextUsername.text.toString().trim { it <= ' ' }
            val email = editTextEmail.text.toString().trim { it <= ' ' }
            val password = editTextPassword.text.toString().trim { it <= ' ' }
            val passwordConf = editTextCnfPassword.text.toString().trim { it <= ' ' }
            if (password == passwordConf) {
                val user = User(userName, password, email)
                dataBase.insert(user)
                val moveToLogin = Intent(this@RegistrationPage, LoginPage::class.java)
                startActivity(moveToLogin)
            } else {
                Toast.makeText(
                    this@RegistrationPage,
                    "Password is not matching",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
