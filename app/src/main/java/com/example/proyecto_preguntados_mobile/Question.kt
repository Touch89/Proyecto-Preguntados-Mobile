package com.example.proyecto_preguntados_mobile

data class Question(val text: String, val topic: String, val answers: List<Answer>, var usedHint: Boolean = false)