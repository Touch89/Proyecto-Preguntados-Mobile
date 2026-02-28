package com.example.proyecto_preguntados_mobile

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.getValue

class GameActivity : AppCompatActivity() {

    private lateinit var questionText: TextView
    private lateinit var questionNumberText: TextView
    private lateinit var totalAnsweredText: TextView
    private lateinit var mainLayout: LinearLayout
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
    private var totalOfQuestions = 5
    private var hintsActivated = false
    private var consecutiveAnswers = 0
    private var totalAnswered = 0
    private var topicsChosen = listOf<String>()
    private var counter = 0
    //Agarrar los temas seleccionados

    private fun updateInterface() {
        questionNumberText.text = "Pregunta ${questionIndex + 1}"
        totalAnsweredText.text = "${totalAnswered} / ${questionArray.size} contestadas"
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
        mainLayout = findViewById(R.id.main_layout)
        optionAButton = findViewById(R.id.optionA_button)
        optionBButton = findViewById(R.id.optionB_button)
        optionCButton = findViewById(R.id.optionC_button)
        optionDButton = findViewById(R.id.optionD_button)
        prevButton = findViewById(R.id.prev_button)
        nextButton = findViewById(R.id.next_button)
        //hintButton = findViewById(R.id.hint_button) FALTA AGREGAR

        //prueba
        topicsChosen += "Matemáticas"
        topicsChosen += "Cultura General"
        //totalOfQuestions = variable pasada


        for (question in quizModel.questionList) {
            if (counter < totalOfQuestions) {
                if (topicsChosen.contains(question.topic)) {
                    questionArray += question
                }
            }
        }
        questionText.text = questionArray[questionIndex].text
        updateInterface()

        nextButton.setOnClickListener { _ ->
            questionIndex = (questionIndex + 1) % questionArray.size
            questionText.text = questionArray[questionIndex].text
            updateInterface()
        }

        prevButton.setOnClickListener { _ ->
            if (questionIndex == 0) {
                questionIndex = questionArray.size - 1
            } else {
                questionIndex = (questionIndex - 1) % questionArray.size
            }
            questionText.text = questionArray[questionIndex].text
            updateInterface()
        }


    }
}