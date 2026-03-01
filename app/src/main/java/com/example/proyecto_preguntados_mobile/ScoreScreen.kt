package com.example.proyecto_preguntados_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ScoreScreen : AppCompatActivity() {

    private val scoreModel: ScoreModel by viewModels()

    private lateinit var imgScore: ImageView
    private lateinit var scoreText: TextView
    private lateinit var difficultyScore: TextView
    private lateinit var questionAnsweredScore: TextView
    private lateinit var hintUseScore: TextView
    private lateinit var hintBonoScore: TextView
    private lateinit var backStartButton: Button

    private var questionScore = 0
    private var hintUse = 0
    private var hintBono = 0

    enum class Difficulty {
        FACIL, MEDIO, DIFICIL
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_score_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        scoreText = findViewById(R.id.score_text)
        difficultyScore = findViewById(R.id.difficulty_score)
        questionAnsweredScore = findViewById(R.id.question_answered_score)
        hintUseScore = findViewById(R.id.hint_use_score)
        hintBonoScore = findViewById(R.id.hint_bono_score)
        backStartButton = findViewById(R.id.back_start_button)

        backStartButton.setOnClickListener { _ ->
            val intent = Intent(this, StartScreen::class.java)
            startActivity(intent)
        }

        calculateScore(9, 2, 1, Difficulty.MEDIO)
        changeImage(scoreModel.globalScore)

        scoreText.text = scoreModel.globalScore.toString()
        difficultyScore.text = Difficulty.MEDIO.toString()
        questionAnsweredScore.text = "9"
        hintUseScore.text = "1"
        hintBonoScore.text = "2"
    }

    fun calculateScore(questionScore: Int, hintUse: Int, hintBono: Int, difficulty: Difficulty): Int {
        val prevScore = questionScore + hintBono - hintUse
        scoreModel.globalScore = when (difficulty){
            Difficulty.FACIL -> prevScore * 1
            Difficulty.MEDIO -> prevScore * 2
            Difficulty.DIFICIL -> prevScore * 3
        }
        return scoreModel.globalScore
    }

    fun changeImage(globalScore: Int){
        imgScore = findViewById(R.id.img_score)
        val setImage = if (globalScore <= 10){
            R.drawable.sad_score
        } else if (globalScore in 11..15){
            R.drawable.akward_score
        } else {
            R.drawable.happy_score
        }

        imgScore.setImageResource(setImage)
    }
}