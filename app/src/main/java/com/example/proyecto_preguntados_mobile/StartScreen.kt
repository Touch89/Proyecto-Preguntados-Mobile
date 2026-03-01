package com.example.proyecto_preguntados_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartScreen : AppCompatActivity() {

    private lateinit var startButton: ImageButton
    private lateinit var optionsButton: ImageButton
    private lateinit var leaderboardButton: ImageButton

    private val startModel: StartModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        startButton = findViewById(R.id.start_button)
        optionsButton = findViewById(R.id.options_button)
        leaderboardButton = findViewById(R.id.leaderboard_button)

        startButton.setOnClickListener { _ ->
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }

        optionsButton.setOnClickListener { _ ->
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}