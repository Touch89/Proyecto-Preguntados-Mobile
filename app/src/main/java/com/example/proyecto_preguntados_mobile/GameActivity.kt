package com.example.proyecto_preguntados_mobile

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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
    }
}