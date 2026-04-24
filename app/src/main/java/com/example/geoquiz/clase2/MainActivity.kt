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
import com.example.geoquiz.pregunta.Pregunta

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val preguntas = listOf<Pregunta>(
        Pregunta(R.string.pregunta_australia, true),
        Pregunta(R.string.pregunta_oceanos, true),
        Pregunta(R.string.pregunta_medio_oriente, true),
        Pregunta(R.string.pregunta_africa, true),
        Pregunta(R.string.prgunta_america, true),
        Pregunta(R.string.pregunta_asia, true)
    )

    var indiceActual: Int = 0
    var puntaje: Int = 0 // Variable para contar las correctas
    val preguntasContestadas = BooleanArray(6) { false } // Arreglo para saber cuáles ya se respondieron

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() fue llamado")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperar datos si se gira la pantalla
        if (savedInstanceState != null) {
            indiceActual = savedInstanceState.getInt(KEY_INDEX_IA, 0)
            puntaje = savedInstanceState.getInt(KEY_PUNTAJE, 0)
            val guardadas = savedInstanceState.getBooleanArray(KEY_CONTESTADAS)
            if (guardadas != null) {
                guardadas.copyInto(preguntasContestadas)
            }
        }

        actulizarPregunta() // Se llama aquí para cargar la primera pregunta con sus bloqueos

        binding.trueButton.setOnClickListener {
            verificarRespuesta(true)
        }

        binding.falseButton.setOnClickListener {
            verificarRespuesta(false)
        }

        binding.btnNext.setOnClickListener {
            indiceActual = (indiceActual + 1) % preguntas.size
            actulizarPregunta()
        }
        binding.btnBack.setOnClickListener {
            indiceActual = if (indiceActual == 0) preguntas.size - 1 else indiceActual - 1
            actulizarPregunta()
        }
    }

    private fun actulizarPregunta() {
        val pregunta: Int = preguntas[indiceActual].IdTextoRpta
        binding.tVPregunta.text = getString(pregunta)

        // Habilitar o deshabilitar los botones si la pregunta actual ya fue contestada
        val yaContestada = preguntasContestadas[indiceActual]
        binding.trueButton.isEnabled = !yaContestada
        binding.falseButton.isEnabled = !yaContestada
    }

    private fun verificarRespuesta(rptaUsuario: Boolean) {
        var rptaCorrecta = preguntas[indiceActual].rpta
        val esCorrecto = (rptaUsuario == rptaCorrecta)

        // Marcar la pregunta como contestada y apagar los botones instantáneamente
        preguntasContestadas[indiceActual] = true
        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

        // Sumar al puntaje si acertó
        if (esCorrecto) {
            puntaje += 1
        }

        val msgRpta = if (esCorrecto) R.string.toast_correcto else R.string.toast_incorrecto
        val colorFondo = if (esCorrecto) "#4CAF50" else "#F44336"

        val vistaPersonalizada = android.widget.TextView(this)
        vistaPersonalizada.text = getString(msgRpta)

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
        if (preguntasContestadas.all { it }) {
            val porcentaje = (puntaje * 100) / preguntas.size
            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                Toast.makeText(this, "¡Completado! Tu porcentaje de aciertos es: $porcentaje%", Toast.LENGTH_LONG).show()
            }, 1500)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState() fue llamado")
        outState.putInt(KEY_INDEX_IA, indiceActual)
        outState.putInt(KEY_PUNTAJE, puntaje)
        outState.putBooleanArray(KEY_CONTESTADAS, preguntasContestadas)
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