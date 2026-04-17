package com.example.geoquiz

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.trueButton.setOnClickListener { /*evento para el botón verdadero*/

            val vistaPersonalizada = android.widget.TextView(this)
            vistaPersonalizada.text = getString(R.string.toast_correcto)
            vistaPersonalizada.setBackgroundColor(Color.parseColor("#4CAF50")) /*color verde*/
            vistaPersonalizada.setTextColor(Color.WHITE)
            vistaPersonalizada.setPadding(50, 30, 50, 30) /*bordes curvos*/
            vistaPersonalizada.textSize = 16f

            val toast = Toast(this) /*creacion del toast vacio, se le pasa el cartel y se ubica arriba*/
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.TOP, 0, 150)
            toast.view = vistaPersonalizada /*evita el problema con las nuevas versiones de android*/

            toast.show()
        }

        binding.falseButton.setOnClickListener { /*evento para el botón falso*/
            val vistaPersonalizada = android.widget.TextView(this)
            vistaPersonalizada.text = getString(R.string.toast_incorrecto)
            vistaPersonalizada.setBackgroundColor(Color.parseColor("#F44336")) /*color rojo*/
            vistaPersonalizada.setTextColor(Color.WHITE)
            vistaPersonalizada.setPadding(50, 30, 50, 30)
            vistaPersonalizada.textSize = 16f

            val toast = Toast(this)
            toast.duration = Toast.LENGTH_SHORT
            toast.setGravity(Gravity.TOP, 0, 150)
            toast.view = vistaPersonalizada

            toast.show()
        }
    }
}