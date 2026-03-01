package com.example.proyecto_preguntados_mobile

data class Answer(val text: String, val correct: Boolean, var eliminatedByHint: Boolean = false)