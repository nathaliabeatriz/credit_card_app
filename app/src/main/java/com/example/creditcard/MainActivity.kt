package com.example.creditcard

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        formatCardNumber()
        validateName()
        formatDate()
        validateCvc()
    }

    fun formatCardNumber(){
        val field = findViewById<EditText>(R.id.numero_cartao)

        field.addTextChangedListener(object : TextWatcher {
            private var editing = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (editing) return
                editing = true

                s?.let {
                    val cleanText = it.toString().replace(" ", "")
                    val formatted = cleanText.chunked(4).joinToString(" ")
                    field.setText(formatted)
                    field.setSelection(formatted.length)
                }
                editing = false
            }
        })

        field.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = field.text.toString().replace(" ", "")
                if (input.length < 16) {
                    field.error = "Necessário 16 números"
                } else {
                    field.error = null
                }
            }
        }
    }

    fun validateName(){
        val field = findViewById<EditText>(R.id.nome)
        field.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = field.text.toString().replace(" ", "")
                if (input.length < 3) {
                    field.error = "Mínimo de 3 caracteres"
                } else {
                    field.error = null
                }
            }
        }
    }

    fun formatDate(){
        val field = findViewById<EditText>(R.id.validade_field)
        field.addTextChangedListener(object : TextWatcher {
            private var editing = false
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (editing) return
                editing = true

                s?.let {
                    val cleanText = it.toString().replace("/", "")
                    if(it.length > 2) {

                        val formatted = cleanText.substring(0, 2) + "/" + cleanText.substring(2)

                        field.setText(formatted)
                        field.setSelection(formatted.length.coerceAtMost(field.text.length))
                    }
                }
                editing = false
            }
        })

        field.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = field.text.toString().replace("/", "")
                val regex = Regex("^(0[1-9]|1[0-2])\\d{2}$")
                if (input.length < 4 || !regex.matches(input)) {
                    field.error = "Data inválida"
                } else {
                    field.error = null
                }
            }
        }
    }

    fun validateCvc(){
        val field = findViewById<EditText>(R.id.cvc_field)
        field.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val input = field.text.toString().replace(" ", "")
                if (input.length < 3) {
                    field.error = "Necessário 3 números"
                } else {
                    field.error = null
                }
            }
        }
    }
}