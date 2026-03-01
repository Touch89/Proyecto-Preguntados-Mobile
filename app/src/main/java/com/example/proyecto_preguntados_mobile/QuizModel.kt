package com.example.proyecto_preguntados_mobile

import androidx.lifecycle.ViewModel
import kotlin.rem

class QuizModel : ViewModel() {
    private val questionArray = listOf<Question>(
        Question(
            "¿Cómo se llama el ogro verde que vive en un pantano?",
            "Cine",
            listOf<Answer>(
                Answer("Shrek", true),
                Answer("Sulley", false),
                Answer("Hulk", false),
                Answer("Mike", false)
            )
        ),
        Question(
            "¿Cuál es el nombre del muñeco de nieve en la película Frozen?",
            "Cine",
            listOf<Answer>(
                Answer("Sven", false),
                Answer("Olaf", true),
                Answer("Kristoff", false),
                Answer("Hans", false)
            )
        ),
        Question(
            "¿Qué animal es Simba en 'El Rey León'?",
            "Cine",
            listOf<Answer>(
                Answer("Tigre", false),
                Answer("Leopardo", false),
                Answer("León", true),
                Answer("Hiena", false)
            )
        ),
        Question(
            "¿Quién es el fiel compañero de Sherlock Holmes?",
            "Cine",
            listOf<Answer>(
                Answer("Dr. Watson", true),
                Answer("Moriarty", false),
                Answer("Lestrade", false),
                Answer("Mycroft", false)
            )
        ),
        Question(
            "¿Cuál es el nombre del niño que viaja a la Tierra de los Muertos en 'Coco'?",
            "Cine",
            listOf<Answer>(
                Answer("Héctor", false),
                Answer("Ernesto", false),
                Answer("Dante", false),
                Answer("Miguel", true)
            )
        ),
        Question(
            "¿En qué país se encuentra la Gran Muralla?",
            "Geografía",
            listOf<Answer>(
                Answer("Japón", false),
                Answer("China", true),
                Answer("Corea", false),
                Answer("Tailandia", false)
            )
        ),
        Question(
            "¿Cuál es el río más largo del mundo?",
            "Geografía",
            listOf<Answer>(
                Answer("Nilo", false),
                Answer("Amazonas", true),
                Answer("Misuri", false),
                Answer("Yangtsé", false)
            )
        ),
        Question(
            "¿Cuál es el continente más grande del mundo?",
            "Geografía",
            listOf<Answer>(
                Answer("América", false),
                Answer("África", false),
                Answer("Europa", false),
                Answer("Asia", true)
            )
        ),
        Question(
            "¿En qué ciudad se encuentra la Torre Eiffel?",
            "Geografía",
            listOf<Answer>(
                Answer("París", true),
                Answer("Londres", false),
                Answer("Roma", false),
                Answer("Madrid", false)
            )
        ),
        Question(
            "¿Qué océano baña las costas de Brasil?",
            "Geografía",
            listOf<Answer>(
                Answer("Pacífico", false),
                Answer("Índico", false),
                Answer("Atlántico", true),
                Answer("Ártico", false)
            )
        ),
        Question(
            "¿Para qué sirve el comando 'Ctrl + C' en una computadora?",
            "Tecnología",
            listOf<Answer>(
                Answer("Pegar", false),
                Answer("Cortar", false),
                Answer("Copiar", true),
                Answer("Deshacer", false)
            )
        ),
        Question(
            "¿Qué empresa fabrica el iPhone?",
            "Tecnología",
            listOf<Answer>(
                Answer("Samsung", false),
                Answer("Apple", true),
                Answer("Google", false),
                Answer("Microsoft", false)
            )
        ),
        Question(
            "¿Cómo se llama el sistema operativo de Google para móviles?",
            "Tecnología",
            listOf<Answer>(
                Answer("iOS", false),
                Answer("Android", true),
                Answer("Windows", false),
                Answer("Linux", false)
            )
        ),
        Question(
            "¿Qué significa la 'W' en las siglas WWW?",
            "Tecnología",
            listOf<Answer>(
                Answer("World", true),
                Answer("Web", false),
                Answer("Wide", false),
                Answer("Wireless", false)
            )
        ),
        Question(
            "¿Cuál es el nombre del asistente virtual de Amazon?",
            "Tecnología",
            listOf<Answer>(
                Answer("Siri", false),
                Answer("Cortana", false),
                Answer("Alexa", true),
                Answer("Bixby", false)
            )
        ),
        Question(
            "¿Cuántos jugadores hay en un equipo de fútbol en el campo?",
            "Deportes",
            listOf<Answer>(
                Answer("10", false),
                Answer("12", false),
                Answer("11", true),
                Answer("9", false)
            )
        ),
        Question(
            "¿En qué deporte se utiliza una raqueta y una pelota amarilla?",
            "Deportes",
            listOf<Answer>(
                Answer("Tenis", true),
                Answer("Fútbol", false),
                Answer("Golf", false),
                Answer("Béisbol", false)
            )
        ),
        Question(
            "¿Cada cuántos años se celebran los Juegos Olímpicos?",
            "Deportes",
            listOf<Answer>(
                Answer("2 años", false),
                Answer("4 años", true),
                Answer("6 años", false),
                Answer("5 años", false)
            )
        ),
        Question(
            "¿Cómo se llama cuando un jugador de baloncesto encesta desde muy lejos?",
            "Deportes",
            listOf<Answer>(
                Answer("Dunk", false),
                Answer("Tiro libre", false),
                Answer("Triple", true),
                Answer("Saque", false)
            )
        ),
        Question(
            "¿Cuál es el color del cinturón más alto en Karate?",
            "Deportes",
            listOf<Answer>(
                Answer("Blanco", false),
                Answer("Rojo", false),
                Answer("Verde", false),
                Answer("Negro", true)
            )
        ),
        Question(
            "¿Cuál es el planeta más grande de nuestro sistema solar?",
            "Astronomía",
            listOf<Answer>(
                Answer("Marte", false),
                Answer("Júpiter", true),
                Answer("Saturno", false),
                Answer("Tierra", false)
            )
        ),
        Question(
            "¿Cómo se llama nuestra galaxia?",
            "Astronomía",
            listOf<Answer>(
                Answer("Andrómeda", false),
                Answer("Vía Láctea", true),
                Answer("Nebulosa", false),
                Answer("Centauri", false)
            )
        ),
        Question(
            "¿Cuál es el planeta conocido como el 'Planeta Rojo'?",
            "Astronomía",
            listOf<Answer>(
                Answer("Venus", false),
                Answer("Mercurio", false),
                Answer("Marte", true),
                Answer("Urano", false)
            )
        ),
        Question(
            "¿Qué astro es el centro de nuestro sistema?",
            "Astronomía",
            listOf<Answer>(
                Answer("La Luna", false),
                Answer("El Sol", true),
                Answer("La Tierra", false),
                Answer("Júpiter", false)
            )
        ),
        Question(
            "¿Cuál es el único satélite natural de la Tierra?",
            "Astronomía",
            listOf<Answer>(
                Answer("Titán", false),
                Answer("Europa", false),
                Answer("Ío", false),
                Answer("La Luna", true)
            )
        )
    )

    //Ya se queda en la misma pregunta, ya solo faltaría que veas eso de las opciones
    //Para que ya pueda meter lo de las pistas y ya debería estar👍

    private var gameQuestions = listOf<Question>()
    var answers = listOf<Answer>()
    var difficulty = 2
    private var questionsChosen = false
    var questionIndex = 0
    private var hintAmount = 3
    private var consecutiveAnswers = 0
    private var counter = 0

    fun startGame(questionAmount: Int, topicsChosen: List<String>){
        counter = 0
        if (questionsChosen) {return}

        for (question in questionArray.shuffled()) {
            if (counter < questionAmount) {
                if (topicsChosen.contains(question.topic)) {
                    gameQuestions += question
                    var incorrectAnswerNumber = 0
                    val randomQuestionAnswers = question.answers.shuffled()
                    for (answer in randomQuestionAnswers) {
                        if (answer.correct) {
                            answers += answer
                        }
                        else if (incorrectAnswerNumber <= difficulty) {
                            answers += answer
                            incorrectAnswerNumber++
                        }
                    }
                    counter++
                }
            }
        }
        questionsChosen = true
    }

    fun moveToTheNextQuestion() {
        questionIndex = (questionIndex + 1) % gameQuestions.size
    }

    fun moveToThePrevQuestion() {
        if (questionIndex == 0) {
            questionIndex = gameQuestions.size - 1
        } else {
            questionIndex = (questionIndex - 1) % gameQuestions.size
        }
    }

    fun useHint(){ //Acá van las cosas para
        gameQuestions[questionIndex].usedHint = true
        hintAmount -= 1
        consecutiveAnswers = 0
    }

    val questionAnswer: List<Answer>
        get() = gameQuestions[questionIndex].answers

    val questionText: String
        get() = gameQuestions[questionIndex].text

    val questionList: List<Question>
        get() = gameQuestions

    val hintsLeft: Int
        get() = hintAmount
}