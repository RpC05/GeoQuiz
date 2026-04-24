package com.example.geoquiz.clase24042026

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.geoquiz.R
import com.example.geoquiz.pregunta.Pregunta

private const val TAG="PreguntaViewModel"

const val CLAVE_INDICE_ACTUAL = "clave_indice_actual"
const val CLAVE_PUNTAJE = "clave_puntaje"
const val CLAVE_CONTESTADAS = "clave_contestadas"
const val CLAVE_TRAMPA = "clave_trampa"

class PreguntaViewModel(private val savedStateHandle: SavedStateHandle): ViewModel() {

    val preguntas = listOf<Pregunta>(
        Pregunta(R.string.pregunta_australia, true),
        Pregunta(R.string.pregunta_oceanos, true),
        Pregunta(R.string.pregunta_medio_oriente, true),
        Pregunta(R.string.pregunta_africa, true),
        Pregunta(R.string.prgunta_america, true),
        Pregunta(R.string.pregunta_asia, true)
    )

    var indiceActual: Int
        get() = savedStateHandle.get(CLAVE_INDICE_ACTUAL) ?: 0
        set(value) = savedStateHandle.set(CLAVE_INDICE_ACTUAL, value)

    var puntaje: Int
        get() = savedStateHandle.get(CLAVE_PUNTAJE) ?: 0
        set(value) = savedStateHandle.set(CLAVE_PUNTAJE, value)

    var preguntasContestadas: BooleanArray
        get() = savedStateHandle.get(CLAVE_CONTESTADAS) ?: BooleanArray(6) { false }
        set(value) = savedStateHandle.set(CLAVE_CONTESTADAS, value)

    var hizoTrampa: Boolean
        get() = savedStateHandle.get(CLAVE_TRAMPA) ?: false
        set(value) = savedStateHandle.set(CLAVE_TRAMPA, value)

    val respuestaActual: Boolean
        get() = preguntas[indiceActual].rpta

    val textoActual: Int
        get() = preguntas[indiceActual].IdTextoRpta

    fun moverAlSiguiente() {
        indiceActual = (indiceActual + 1) % preguntas.size
    }

    fun moverAlAnterior() {
        indiceActual = if (indiceActual == 0) preguntas.size - 1 else indiceActual - 1
    }

    init {
        Log.d(TAG, "Instancia del ViewModel creada")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "Instancia del ViewModel por destruirse")
    }
}