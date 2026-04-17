package com.example.geoquiz.clase2

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityMainBinding
import com.example.geoquiz.pregunta.Pregunta

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val preguntas = listOf<Pregunta>(Pregunta(R.string.pregunta_australia, true),
        Pregunta(R.string.pregunta_oceanos, true),
        Pregunta(R.string.pregunta_medio_oriente, true),
        Pregunta(R.string.pregunta_africa, true),
        Pregunta(R.string.prgunta_america, true),
        Pregunta(R.string.pregunta_asia, true))

    var indiceActual:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var pregunta:Int=preguntas[indiceActual].IdTextoRpta
        binding.tVPregunta.text=getString(pregunta)


        binding.trueButton.setOnClickListener {
            verificarRespuesta(true)
        }

        binding.falseButton.setOnClickListener {
            verificarRespuesta(false)
        }

        binding.btnNext.setOnClickListener {
            indiceActual+=1
            actulizarPregunta()


        }
    }
    private fun actulizarPregunta(){
        val pregunta:Int=preguntas[indiceActual].IdTextoRpta
        binding.tVPregunta.text=getString(pregunta)
    }

    private fun verificarRespuesta(rptaUsuario: Boolean){
        var rptaCorrecta=preguntas[indiceActual].rpta
        var msgRpta:Int=0
        msgRpta=if(rptaUsuario==rptaCorrecta) R.string.toast_correcto else R.string.toast_incorrecto
        Toast.makeText(this, msgRpta, Toast.LENGTH_LONG).show()

    }
}