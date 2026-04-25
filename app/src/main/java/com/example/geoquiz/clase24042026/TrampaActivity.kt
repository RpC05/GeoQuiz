package com.example.geoquiz.clase24042026

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityTrampaBinding

const val EXTRA_RPTA_MOSTRADA = "com.example.geoquiz.rpta_mostrada"
private const val EXTRA_RPTA_CORRECTA = "com.example.geoquiz.rpta_correcta"

class TrampaActivity : AppCompatActivity() {
    private var hizoTrampa = false
    private lateinit var binding: ActivityTrampaBinding
    private var rptaCorrecta = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTrampaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rptaCorrecta = intent.getBooleanExtra(EXTRA_RPTA_CORRECTA, false)

        binding.showAnswerButton.setOnClickListener {
            val textoRpta = when {
                rptaCorrecta -> R.string.boton_verdadero
                else -> R.string.botn_falso
            }
            binding.answerTextView.setText(textoRpta)

            setResultPreguntaMostrada(true)
        }

        hizoTrampa = savedInstanceState?.getBoolean("HIZO_TRAMPA", false) ?: false

        // Si ya había hecho trampa, mostramos la respuesta de una vez
        if (hizoTrampa) {
            mostrarRespuesta()
        }

        binding.showAnswerButton.setOnClickListener {
            hizoTrampa = true
            mostrarRespuesta()
            setResultPreguntaMostrada(true)
        }
    }

    private fun mostrarRespuesta() {
        val textoRpta = if (rptaCorrecta) R.string.boton_verdadero else R.string.botn_falso
        binding.answerTextView.setText(textoRpta)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("HIZO_TRAMPA", hizoTrampa)
    }

    private fun setResultPreguntaMostrada(fueMostrada: Boolean) {
        val data = Intent() // Creamos un Intent vacío, solo para llevar datos
        data.putExtra(EXTRA_RPTA_MOSTRADA, fueMostrada) // Metemos el "true"
        setResult(Activity.RESULT_OK, data) // Marcamos el resultado como OK y adjuntamos la data
    }

    companion object {
        fun nuevoIntent(packageContext: Context, rptaCorrecta: Boolean): Intent {
            val intento = Intent(packageContext, TrampaActivity::class.java)
            intento.putExtra(EXTRA_RPTA_CORRECTA, rptaCorrecta)
            return intento
        }
    }
}