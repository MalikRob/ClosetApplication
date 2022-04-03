package com.example.closet


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room

import com.example.closet.database.UserDAO
import com.example.closet.database.UserDatabase
import com.example.closet.ui.RegistrationPage
import com.example.closet.ui.User


class LoginPage : AppCompatActivity() {
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView
    private lateinit var db: UserDAO
    private lateinit var dataBase: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        //Email == Username
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)
        dataBase = Room.databaseBuilder(this, UserDatabase::class.java, "mi-database.db")
            .allowMainThreadQueries()
            .build()
        db = dataBase.UserDAO!!
        textViewRegister.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@LoginPage,
                    RegistrationPage::class.java
                )
            )
        })
        buttonLogin.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.text.toString().trim { it <= ' ' }
            val password = editTextPassword.text.toString().trim { it <= ' ' }
            val user: User? = db.getUser(email, password)
            if (user != null) {
                val i = Intent(this@LoginPage, MainClosetDisplayPage::class.java)
                i.putExtra("User", user)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(
                    this@LoginPage,
                    "Unregistered user, or incorrect",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
