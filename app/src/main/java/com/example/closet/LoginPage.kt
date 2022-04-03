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
import com.example.closet.ui.RegisterActivity
import com.example.closet.ui.User


class MainActivity : AppCompatActivity() {
    var editTextEmail: EditText? = null
    var editTextPassword: EditText? = null
    var buttonLogin: Button? = null
    var textViewRegister: TextView? = null
    var db: UserDAO? = null
    var dataBase: UserDatabase? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)
        dataBase = Room.databaseBuilder(this, UserDatabase::class.java, "mi-database.db")
            .allowMainThreadQueries()
            .build()
        db = dataBase.getUserDAO()
        textViewRegister.setOnClickListener(View.OnClickListener {
            startActivity(
                Intent(
                    this@MainActivity,
                    RegisterActivity::class.java
                )
            )
        })
        buttonLogin.setOnClickListener(View.OnClickListener {
            val email = editTextEmail.getText().toString().trim { it <= ' ' }
            val password = editTextPassword.getText().toString().trim { it <= ' ' }
            val user: User? = db?.getUser(email, password)
            if (user != null) {
                val i = Intent(this@MainActivity, MainClosetDisplayPage::class.java)
                i.putExtra("User", user)
                startActivity(i)
                finish()
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "Unregistered user, or incorrect",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
