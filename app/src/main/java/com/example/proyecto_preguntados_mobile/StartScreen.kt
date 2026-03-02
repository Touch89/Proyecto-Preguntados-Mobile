package com.example.proyecto_preguntados_mobile

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import java.io.File

class StartScreen : AppCompatActivity() {

    private data class SettingsState(
        val cine: Boolean,
        val geografia: Boolean,
        val tecnologia: Boolean,
        val deportes: Boolean,
        val astronomia: Boolean,
        val questionCount: Int,
        val difficultyIndex: Int,
        val hintsEnabled: Boolean
    )

    private val settingsFileName = "game_settings.json"

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
            val settings = loadSettingsState()
            val topics = mutableListOf<String>().apply {
                if (settings.cine) add("Cine")
                if (settings.geografia) add("Geografía")
                if (settings.tecnologia) add("Tecnología")
                if (settings.deportes) add("Deportes")
                if (settings.astronomia) add("Astronomía")
            }

            if (topics.isEmpty()) {
                topics.addAll(listOf("Cine", "Geografía", "Tecnología", "Deportes", "Astronomía"))
            }

            val intent = Intent(this, GameActivity::class.java).apply {
                putStringArrayListExtra("topics", ArrayList(topics))
                putExtra("questionCount", settings.questionCount)
                putExtra("difficultyIndex", settings.difficultyIndex)
                putExtra("hintsEnabled", settings.hintsEnabled)
            }
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

    private fun loadSettingsState(): SettingsState {
        val defaultState = SettingsState(
            cine = false,
            geografia = false,
            tecnologia = false,
            deportes = false,
            astronomia = false,
            questionCount = 5,
            difficultyIndex = 0,
            hintsEnabled = true
        )

        return try {
            val file = File(filesDir, settingsFileName)
            if (!file.exists()) {
                defaultState
            } else {
                val json = JSONObject(file.readText())
                SettingsState(
                    cine = json.optBoolean("cine", defaultState.cine),
                    geografia = json.optBoolean("geografia", defaultState.geografia),
                    tecnologia = json.optBoolean("tecnologia", defaultState.tecnologia),
                    deportes = json.optBoolean("deportes", defaultState.deportes),
                    astronomia = json.optBoolean("astronomia", defaultState.astronomia),
                    questionCount = json.optInt("questionCount", defaultState.questionCount).coerceIn(5, 10),
                    difficultyIndex = json.optInt("difficultyIndex", defaultState.difficultyIndex).coerceIn(0, 2),
                    hintsEnabled = json.optBoolean("hintsEnabled", defaultState.hintsEnabled)
                )
            }
        } catch (_: Exception) {
            defaultState
        }
    }
}