package com.example.geoquiz.clase24042026

import android.app.Activity // 👇 NUEVO IMPORTANTE: Para usar Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityTrampaBinding

// 👇 1. MOVIMOS ESTA CONSTANTE ARRIBA COMO PIDE LA DIAPOSITIVA
const val EXTRA_RPTA_MOSTRADA = "com.example.geoquiz.rpta_mostrada"
private const val EXTRA_RPTA_CORRECTA = "com.example.geoquiz.rpta_correcta"

class TrampaActivity : AppCompatActivity() {

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

            // 👇 2. LLAMAMOS A LA FUNCIÓN AL MOMENTO DE MOSTRAR LA RESPUESTA
            setResultPreguntaMostrada(true)
        }
    }

    // 👇 3. LA NUEVA FUNCIÓN DE LA DIAPOSITIVA
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