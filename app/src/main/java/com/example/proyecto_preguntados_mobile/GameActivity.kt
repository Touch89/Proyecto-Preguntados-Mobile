package com.example.proyecto_preguntados_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.getValue

class GameActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var totalAnsweredText: TextView
    private lateinit var mainLayout: ConstraintLayout
    private lateinit var optionAButton: Button
    private lateinit var optionBButton: Button
    private lateinit var optionCButton: Button
    private lateinit var optionDButton: Button
    private lateinit var prevButton: Button
    private lateinit var nextButton: Button
    private lateinit var hintButton: Button

    private val quizModel: QuizModel by viewModels()

    private var questionArray = listOf<Question>()

    private var questionIndex = 0
    private var totalOfQuestions = 10
    private var hintsActivated = false
    private var hintAmount = 3
    private var consecutiveAnswers = 0
    private var totalAnswered = 0
    private var topicsChosen = listOf<String>()
    private var counter = 0
    //Agarrar los temas seleccionados

    private fun updateInterface(topic: String) {
        questionNumberText.text = "Pregunta ${questionIndex + 1}"
        totalAnsweredText.text = "${totalAnswered} / ${questionArray.size} contestadas"

        questionText.text = questionArray[questionIndex].text
        optionAButton.text = questionArray[questionIndex].answers[0].text
        optionBButton.text = questionArray[questionIndex].answers[1].text
        optionCButton.text = questionArray[questionIndex].answers[2].text
        optionDButton.text = questionArray[questionIndex].answers[3].text

        if (hintsActivated){
            hintButton.text = "Hint (${hintAmount}  left)"

        }
        else{
            hintButton.visibility = View.GONE
        }
        when (topic) {
            "Cine" -> mainLayout.setBackgroundResource(R.drawable.cinemaimage)
            "Geografía" -> mainLayout.setBackgroundResource(R.drawable.geographyimage)
            "Tecnología" -> mainLayout.setBackgroundResource(R.drawable.technologyimage)
            "Deportes" -> mainLayout.setBackgroundResource(R.drawable.sportsimage)
            "Astronomía" -> mainLayout.setBackgroundResource(R.drawable.astronomyimage)
            else -> return
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_game)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        questionText = findViewById(R.id.question_text)
        questionNumberText = findViewById(R.id.questionNumber_text)
        totalAnsweredText = findViewById(R.id.totalAnswered_text)
        mainLayout = findViewById(R.id.main)
        optionAButton = findViewById(R.id.optionA_button)
        optionBButton = findViewById(R.id.optionB_button)
        optionCButton = findViewById(R.id.optionC_button)
        optionDButton = findViewById(R.id.optionD_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        hintButton = findViewById(R.id.hint_button)

        //prueba
        topicsChosen += "Cine"
        topicsChosen += "Tecnología"
        topicsChosen += "Geografía"
        topicsChosen += "Deportes"
        topicsChosen += "Astronomía"

        //totalOfQuestions = variable pasada
        /*
        Esto está acá por ahora, pero se puede pasar al quizModel y se crea el
        onSaveInstanceState para poder guardar si ya se revolvió y así no se
        cambian las preguntas a cada rato.
        En realidad, debí hacer eso desde el principio, pero ni modos.
        Puedes mover todo lo de abajo, solo lo estaba usando para ver si
        las respuestas servían y qué tanto se desacomodaban 👍.
        */

        for (question in quizModel.questionList.shuffled()) {
            if (counter < totalOfQuestions) {
                if (topicsChosen.contains(question.topic)) {
                    questionArray += question
                    counter++
                }
            }
        }
        updateInterface(questionArray[questionIndex].topic)

        nextButton.setOnClickListener { _ ->
            questionIndex = (questionIndex + 1) % questionArray.size
            updateInterface(questionArray[questionIndex].topic)
        }
        prevButton.setOnClickListener { _ ->
            questionIndex = if (questionIndex == 0) {
                questionArray.size - 1
            } else {
                (questionIndex - 1) % questionArray.size
            }
            updateInterface(questionArray[questionIndex].topic)
        }
    }
}