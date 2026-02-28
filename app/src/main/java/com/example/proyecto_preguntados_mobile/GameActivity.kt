package com.example.proyecto_preguntados_mobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.compareTo
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
    private var firstPressTime: Long = 0
    private var totalOfQuestions = 10
    private var hintsActivated = true
    private var totalAnswered = 0
    private var topicsChosen = listOf<String>()

    private fun updateInterface() {
        val index = quizModel.questionIndex
        val question = questionArray[index]
        val topic = questionArray[index].topic
        val hintsLeft = quizModel.hintsLeft
        questionNumberText.text = "Pregunta ${index + 1}"
        totalAnsweredText.text = "${totalAnswered} / ${questionArray.size} contestadas"

        questionText.text = question.text
        optionAButton.text = question.answers[0].text
        optionBButton.text = question.answers[1].text
        optionCButton.text = question.answers[2].text
        optionDButton.text = question.answers[3].text

        if (hintsActivated){
            hintButton.text = "Hint (${hintsLeft} left)"

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

    private fun hintUsed(){
        if (quizModel.hintsLeft <= 0) {
            Toast.makeText(baseContext, "You have no hints left", Toast.LENGTH_SHORT).show()
            return
        }
        quizModel.useHint()
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

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (firstPressTime + 1000 > System.currentTimeMillis()) {
                    finish()
                } else {
                    Toast.makeText(baseContext, "Press Back twice to exit the game", Toast.LENGTH_SHORT).show()
                }
                firstPressTime = System.currentTimeMillis()
            }
        })

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

        //PRUEBA
        topicsChosen = listOf("Cine", "Geografía", "Tecnología", "Deportes", "Astronomía")

        quizModel.startGame(totalOfQuestions, topicsChosen)

        questionArray = quizModel.questionList

        updateInterface()

        nextButton.setOnClickListener { _ ->
            quizModel.moveToTheNextQuestion()
            updateInterface()
        }
        prevButton.setOnClickListener { _ ->
            quizModel.moveToThePrevQuestion()
            updateInterface()
        }

        hintButton.setOnClickListener { _ ->
            hintUsed()
            updateInterface()
        }
    }
}