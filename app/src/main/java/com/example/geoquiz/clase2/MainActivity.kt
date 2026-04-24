package com.example.geoquiz.clase2

import android.graphics.Color
import android.util.Log
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.geoquiz.R
import com.example.geoquiz.databinding.ActivityMainBinding
import androidx.activity.viewModels
import com.example.geoquiz.clase24042026.PreguntaViewModel
import android.content.Intent
import com.example.geoquiz.clase24042026.TrampaActivity
import androidx.activity.result.contract.ActivityResultContracts
import android.app.Activity
import com.example.geoquiz.clase24042026.EXTRA_RPTA_MOSTRADA

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val preguntaViewModel: PreguntaViewModel by viewModels() //clase 24-04-2026

    private val iniciadorTrampa = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { resultado ->
        if (resultado.resultCode == Activity.RESULT_OK) {
            preguntaViewModel.hizoTrampa = resultado.data?.getBooleanExtra(EXTRA_RPTA_MOSTRADA, false) ?: false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "Se obtuvo un PreguntaViewModel: $preguntaViewModel")
        Log.d(TAG, "onCreate() fue llamado")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        actulizarPregunta()

        binding.trueButton.setOnClickListener {
            verificarRespuesta(true)
        }

        binding.falseButton.setOnClickListener {
            verificarRespuesta(false)
        }

        binding.btnNext.setOnClickListener {
            // AHORA USAMOS LA FUNCIÓN DEL VIEWMODEL
            preguntaViewModel.moverAlSiguiente()
            actulizarPregunta()
        }

        binding.btnBack.setOnClickListener {
            // AHORA USAMOS LA FUNCIÓN EXTRA DEL VIEWMODEL
            preguntaViewModel.moverAlAnterior()
            actulizarPregunta()
        }

        binding.btnTrampa.setOnClickListener {
            val rptaVerdadera = preguntaViewModel.respuestaActual
            val intento = TrampaActivity.nuevoIntent(this, rptaVerdadera)
            iniciadorTrampa.launch(intento)
        }
    }

    private fun actulizarPregunta() {
        // AHORA USAMOS textoActual DEL VIEWMODEL
        val pregunta: Int = preguntaViewModel.textoActual
        binding.tVPregunta.text = getString(pregunta)

        val yaContestada = preguntaViewModel.preguntasContestadas[preguntaViewModel.indiceActual]
        binding.trueButton.isEnabled = !yaContestada
        binding.falseButton.isEnabled = !yaContestada
    }

    private fun verificarRespuesta(rptaUsuario: Boolean) {
        val rptaCorrecta = preguntaViewModel.respuestaActual
        val esCorrecto = (rptaUsuario == rptaCorrecta)

        // Marcar la pregunta como contestada y apagar los botones
        preguntaViewModel.preguntasContestadas[preguntaViewModel.indiceActual] = true
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

        val msgRpta = when {
            preguntaViewModel.hizoTrampa -> R.string.toast_juicio
            esCorrecto -> R.string.toast_correcto
            else -> R.string.toast_incorrecto
        }

        // Asignamos el color y sumamos el puntaje (solo si no hizo trampa y acertó)
        val colorFondo: String
        if (preguntaViewModel.hizoTrampa) {
            colorFondo = "#FF9800" // Naranja de advertencia por tramposo
        } else {
            if (esCorrecto) {
                preguntaViewModel.puntaje += 1
            }
            colorFondo = if (esCorrecto) "#4CAF50" else "#F44336" // Verde o Rojo
        }

        val vistaPersonalizada = android.widget.TextView(this)
        vistaPersonalizada.text = getString(msgRpta) // Aquí usamos el mensaje del 'when'

        val fondoCurvo = android.graphics.drawable.GradientDrawable()
        fondoCurvo.setColor(Color.parseColor(colorFondo))
        fondoCurvo.cornerRadius = 20f
        vistaPersonalizada.background = fondoCurvo

        vistaPersonalizada.setTextColor(Color.WHITE)
        vistaPersonalizada.setPadding(50, 30, 50, 30)
        vistaPersonalizada.textSize = 16f

        val toast = Toast(this)
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.TOP, 0, 150)
        toast.view = vistaPersonalizada
        toast.show()

        // Revisar si ya contestó todas
        if (preguntaViewModel.preguntasContestadas.all { it }) {
            val porcentaje = (preguntaViewModel.puntaje * 100) / preguntaViewModel.preguntas.size
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "¡Completado! Tu porcentaje de aciertos es: $porcentaje%", Toast.LENGTH_LONG).show()
            }, 1500)
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() fue llamado")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() fue llamado")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() fue llamado")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() fue llamado")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() fue llamado")
    }

    companion object {
        val TAG = "MainActivity"
        val KEY_INDEX_IA = "indiceActual"
        val KEY_PUNTAJE = "puntajeGuardado"
        val KEY_CONTESTADAS = "contestadasGuardado"
    }
}