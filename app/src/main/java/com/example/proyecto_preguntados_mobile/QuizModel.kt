package com.example.proyecto_preguntados_mobile

import androidx.lifecycle.ViewModel

class QuizModel : ViewModel() {
    //Manera temporal para manejar las preguntas, puede que cambiemos la parte de las respuestas por una variable de la respuesta correcta y la lista de incorrectas***
    private val questionArray = listOf<Question>(
        Question(
            "Ejemplo 1",
            "Geografía",
            listOf<Answer>(
                Answer("Respuesta 1", true),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", false)
            )
        ),
        Question(
            "Ejemplo 2",
            "Matemáticas",
            listOf<Answer>(
                Answer("Respuesta 1", true),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", false)
            )
        ),
        Question(
            "Ejemplo 3",
            "Cultura General",
            listOf<Answer>(
                Answer("Respuesta 1", true),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", false)
            )
        ),
        Question(
            "Ejemplo 4",
            "Otro tema",
            listOf<Answer>(
                Answer("Respuesta 1", true),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", false)
            )
        ),
        Question(
            "Ejemplo 5",
            "No sé",
            listOf<Answer>(
                Answer("Respuesta 1", true),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", false)
            )
        )

    )
}