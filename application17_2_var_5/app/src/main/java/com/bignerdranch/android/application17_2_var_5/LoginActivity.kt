package com.bignerdranch.android.application17_2_var_5

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.bignerdranch.android.application17_2_var_5.R

class LoginActivity : AppCompatActivity() {
    lateinit var login: EditText
    lateinit var password: EditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        login = findViewById(R.id.login)
        password = findViewById(R.id.password)
    }

    fun view_next_screen(view: View) {

        if (login.text.toString().isNullOrEmpty() || password.text.toString().isNullOrEmpty())
        {
            val alert = AlertDialog.Builder(this)
            alert.setTitle("Ошибка")
            alert.setMessage("В текстовые поля ничего не введено")
            alert.setPositiveButton("ОК",null)
            alert.create()
            alert.show()

        }
        else
        {
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }
    }
}