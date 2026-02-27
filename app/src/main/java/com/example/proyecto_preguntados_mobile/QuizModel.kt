package com.example.proyecto_preguntados_mobile

import androidx.lifecycle.ViewModel
import kotlin.rem

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
                Answer("Respuesta 1", false),
                Answer("Respuesta 2", true),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", false)
            )
        ),
        Question(
            "Ejemplo 4",
            "Otro tema",
            listOf<Answer>(
                Answer("Respuesta 1", false),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", false),
                Answer("Respuesta 4", true)
            )
        ),
        Question(
            "Ejemplo 5",
            "No sé",
            listOf<Answer>(
                Answer("Respuesta 1", false),
                Answer("Respuesta 2", false),
                Answer("Respuesta 3", true),
                Answer("Respuesta 4", false)
            )
        )
    )
    private var questionIndex = 0
    fun moveToTheNextQuestion() {
        questionIndex = (questionIndex + 1) % questionArray.size
    }

    fun moveToThePrevQuestion() {
        if (questionIndex == 0) {
            questionIndex = questionArray.size - 1
        } else {
            questionIndex = (questionIndex - 1) % questionArray.size
        }
    }

    val questionAnswer: List<Answer>
        get() = questionArray[questionIndex].answers

    val questionText: String
        get() = questionArray[questionIndex].text

    val questionList: List<Question>
        get() = questionArray
}