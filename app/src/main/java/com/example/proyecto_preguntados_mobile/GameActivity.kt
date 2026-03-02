package com.example.proyecto_preguntados_mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
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
import androidx.core.graphics.toColorInt

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
    private var initialHints = 3
    private var topicsChosen = listOf<String>()

    private fun resetOptionButtons() {
        optionAButton.setBackgroundTintList(ColorStateList.valueOf("#FF6750A4".toColorInt()))
        optionBButton.setBackgroundTintList(ColorStateList.valueOf("#FF6750A4".toColorInt()))
        optionCButton.setBackgroundTintList(ColorStateList.valueOf("#FF6750A4".toColorInt()))
        optionDButton.setBackgroundTintList(ColorStateList.valueOf("#FF6750A4".toColorInt()))
    }
    private fun updateInterface() {
        val index = quizModel.questionIndex
        val difficulty = quizModel.difficulty
        val question = questionArray[index]
        val answers = quizModel.answers
        val topic = questionArray[index].topic
        val hintsLeft = quizModel.hintsLeft
        val totalAnswered = quizModel.questionsAnswered
        val answeredCorrectly = quizModel.answeredCorrectly
        val hintIndicator = if (question.usedHint) "(Hint used)" else ""

        questionNumberText.text = "Pregunta ${index + 1} ${hintIndicator}"
        totalAnsweredText.text = "${totalAnswered} / ${questionArray.size} contestadas"

        questionText.text = question.text
        optionAButton.text = answers[index*(difficulty+2)].text
        optionBButton.text = answers[index*(difficulty+2)+1].text
        if(difficulty > 0) {
            optionCButton.text = answers[index*(difficulty+2)+2].text
            if (difficulty == 2) {
                optionDButton.text = answers[index*(difficulty+2)+3].text
            } else {
                optionDButton.visibility = View.GONE
            }
        } else {
            optionCButton.visibility = View.GONE
            optionDButton.visibility = View.GONE
        }

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

        resetOptionButtons()
        if (quizModel.answersGiven[quizModel.questionIndex] != null) {
            optionAButton.isEnabled = false
            optionBButton.isEnabled = false
            optionCButton.isEnabled = false
            optionDButton.isEnabled = false
            hintButton.isEnabled = false
            var answeredColor: Int
            if (quizModel.answeredCorrectly[quizModel.questionIndex] == true) {
                answeredColor = Color.GREEN
            } else {
                answeredColor = Color.RED
            }
            when (quizModel.answersGiven[quizModel.questionIndex]) {
                0 -> optionAButton.setBackgroundTintList(ColorStateList.valueOf(answeredColor))
                1 -> optionBButton.setBackgroundTintList(ColorStateList.valueOf(answeredColor))
                2 -> optionCButton.setBackgroundTintList(ColorStateList.valueOf(answeredColor))
                3 -> optionDButton.setBackgroundTintList(ColorStateList.valueOf(answeredColor))
                else -> println("Error setting option color")
            }
        } else {
            optionAButton.isEnabled = true
            optionBButton.isEnabled = true
            optionCButton.isEnabled = true
            optionDButton.isEnabled = true
            hintButton.isEnabled = true
        }
        for(answer in questionArray[quizModel.questionIndex].answers) {
            if (answer.eliminatedByHint){
                if (optionAButton.text == answer.text)
                {
                    optionAButton.isEnabled = false
                    optionAButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW))
                }
                if (optionBButton.text == answer.text)
                {
                    optionBButton.isEnabled = false
                    optionBButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW))
                }
                if (optionCButton.text == answer.text)
                {
                    optionCButton.isEnabled = false
                    optionCButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW))
                }
                if (optionDButton.text == answer.text)
                {
                    optionDButton.isEnabled = false
                    optionDButton.setBackgroundTintList(ColorStateList.valueOf(Color.YELLOW))
                }
            }
        }
    }

    private fun getCorrectAnswer(question: Question): String {
        for (answer in question.answers) {
            if (answer.correct) {
                return answer.text
            }
        }
        return "Error: No correct answer found"
    }

    private fun handleAnswer(button: Button, answerNumber: Int) {
        button.isEnabled = false
        if (button.text == getCorrectAnswer(questionArray[quizModel.questionIndex]))
        {
            quizModel.answeredCorrectly[quizModel.questionIndex] = true
            if (questionArray[quizModel.questionIndex].usedHint)
            {
                quizModel.consecutiveAnswers = 0
            }
            else{
                quizModel.consecutiveAnswers += 1
            }
        } else {
            quizModel.answeredCorrectly[quizModel.questionIndex] = false
            quizModel.consecutiveAnswers = 0
        }

        if (quizModel.consecutiveAnswers == 2){
            quizModel.consecutiveAnswers = 0
            quizModel.hintsLeft += 1
            Toast.makeText(baseContext, "You gained a hint!", Toast.LENGTH_SHORT).show()
        }

        quizModel.questionsAnswered++
        quizModel.answersGiven[quizModel.questionIndex] = answerNumber

        if (quizModel.questionsAnswered >= questionArray.size) {
            navigateToScoreScreen()
            return
        }

        updateInterface()
    }

    private fun navigateToScoreScreen() {
        val questionScore = quizModel.answeredCorrectly.count { it == true }
        val hintUse = questionArray.count { it.usedHint }
        val hintBono = (quizModel.hintsLeft - initialHints + hintUse).coerceAtLeast(0)

        val intent = Intent(this, ScoreScreen::class.java).apply {
            putExtra("questionScore", questionScore)
            putExtra("hintUse", hintUse)
            putExtra("hintBono", hintBono)
            putExtra("difficultyIndex", quizModel.difficulty)
        }
        startActivity(intent)
        finish()
    }
    private fun hintUsed(){
        if (quizModel.hintsLeft <= 0) {
            Toast.makeText(baseContext, "You have no hints left", Toast.LENGTH_SHORT).show()
            return
        }
        val autoSolveQuestion = quizModel.useHint()
        if (autoSolveQuestion){
            if (optionAButton.text == getCorrectAnswer(questionArray[quizModel.questionIndex]))
            {
                optionAButton.performClick()
            }
            if (optionBButton.text == getCorrectAnswer(questionArray[quizModel.questionIndex]))
            {
                optionBButton.performClick()
            }
            if (optionCButton.text == getCorrectAnswer(questionArray[quizModel.questionIndex]))
            {
                optionCButton.performClick()
            }
            if (optionDButton.text == getCorrectAnswer(questionArray[quizModel.questionIndex]))
            {
                optionDButton.performClick()
            }
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

        topicsChosen = intent.getStringArrayListExtra("topics") ?: listOf(
            "Cine",
            "Geografía",
            "Tecnología",
            "Deportes",
            "Astronomía"
        )
        totalOfQuestions = intent.getIntExtra("questionCount", 10)
        quizModel.difficulty = intent.getIntExtra("difficultyIndex", 0).coerceIn(0, 2)
        hintsActivated = intent.getBooleanExtra("hintsEnabled", true)
        quizModel.hintsLeft = if (hintsActivated) 3 else 0
        initialHints = quizModel.hintsLeft

        quizModel.startGame(totalOfQuestions, topicsChosen)

        questionArray = quizModel.questionList

        updateInterface()

        optionAButton.setOnClickListener { _ ->
            handleAnswer(optionAButton, 0)
            optionBButton.isEnabled = false
            optionCButton.isEnabled = false
            optionDButton.isEnabled = false
        }
        optionBButton.setOnClickListener { _ ->
            handleAnswer(optionBButton, 1)
            optionAButton.isEnabled = false
            optionCButton.isEnabled = false
            optionDButton.isEnabled = false
        }
        optionCButton.setOnClickListener { _ ->
            handleAnswer(optionCButton, 2)
            optionAButton.isEnabled = false
            optionBButton.isEnabled = false
            optionDButton.isEnabled = false
        }
        optionDButton.setOnClickListener { _ ->
            handleAnswer(optionDButton, 3)
            optionAButton.isEnabled = false
            optionBButton.isEnabled = false
            optionCButton.isEnabled = false
        }

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