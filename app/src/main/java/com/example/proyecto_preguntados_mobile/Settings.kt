package com.example.proyecto_preguntados_mobile

import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.json.JSONObject
import java.io.File

class Settings : AppCompatActivity() {
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

	private lateinit var seekQuestions: SeekBar
	private lateinit var tvQuestionCount: TextView
	private lateinit var spinnerDifficulty: Spinner
	private lateinit var switchHints: Switch
	private lateinit var hintsContainer: LinearLayout
	private lateinit var tvHintsInfo: TextView
	private lateinit var cbCine: CheckBox
	private lateinit var cbGeografia: CheckBox
	private lateinit var cbTecnologia: CheckBox
	private lateinit var cbDeportes: CheckBox
	private lateinit var cbAstronomia: CheckBox
	private lateinit var saveButton: Button
	private lateinit var geografiaLabel: ImageView
	private lateinit var cineLabel: ImageView
	private lateinit var tecnologiaLabel: ImageView
	private lateinit var deportesLabel: ImageView
	private lateinit var astronomiaLabel: ImageView
    private var numberOfTopicsSelected = 0

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContentView(R.layout.activity_settings)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}

		seekQuestions = findViewById(R.id.seek_questions)
		tvQuestionCount = findViewById(R.id.tv_question_count)
		spinnerDifficulty = findViewById(R.id.spinner_difficulty)
		switchHints = findViewById(R.id.switch_hints)
		hintsContainer = findViewById(R.id.hints_container)
		tvHintsInfo = findViewById(R.id.tv_hints_info)
		cbCine = findViewById(R.id.cb_historia)
		cbGeografia = findViewById(R.id.cb_geografia)
		cbTecnologia = findViewById(R.id.cb_ciencia)
		cbDeportes = findViewById(R.id.cb_deportes)
		cbAstronomia = findViewById(R.id.cb_arte)
		saveButton = findViewById(R.id.save_button)
		geografiaLabel = findViewById(R.id.geografia_label)
		cineLabel = findViewById(R.id.cine_label)
		tecnologiaLabel = findViewById(R.id.tecnologia_label)
		deportesLabel = findViewById(R.id.deportes_label)
		astronomiaLabel = findViewById(R.id.astronomia_label)
        val checkboxes = listOf(cbCine, cbGeografia, cbTecnologia, cbDeportes, cbAstronomia)

		loadSvgLabel("label_geografia.svg", geografiaLabel)
		loadSvgLabel("label_cine.svg", cineLabel)
		loadSvgLabel("label_tecnologia.svg", tecnologiaLabel)
		loadSvgLabel("label_deportes.svg", deportesLabel)
		loadSvgLabel("label_astronomia.svg", astronomiaLabel)

		val difficulties = resources.getStringArray(R.array.difficulty_options)
		val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficulties)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		spinnerDifficulty.adapter = adapter

		seekQuestions.max = 5

		val persistedState = loadSettingsState()
		applyStateToViews(persistedState)
        handleInvalidInputs()

		seekQuestions.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
			override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
				val questionCount = progress + 5
				tvQuestionCount.text = getString(R.string.questions_count_format, questionCount)
			}

			override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

			override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
		})

		switchHints.setOnCheckedChangeListener { _, isChecked ->
			updateHintsVisibility(isChecked)
		}

		saveButton.setOnClickListener {
			saveSettingsState(currentStateFromViews())
			val intent = Intent(this, StartScreen::class.java).apply {
				flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
			}
			startActivity(intent)
			finish()
		}
        for (checkbox in checkboxes) {
            checkbox.setOnCheckedChangeListener { _, _ ->
                handleInvalidInputs()
            }
        }
	}

	override fun onPause() {
		saveSettingsState(currentStateFromViews())
		super.onPause()
	}

	private fun applyStateToViews(state: SettingsState) {
		cbCine.isChecked = state.cine
		cbGeografia.isChecked = state.geografia
		cbTecnologia.isChecked = state.tecnologia
		cbDeportes.isChecked = state.deportes
		cbAstronomia.isChecked = state.astronomia

		val clampedQuestionCount = state.questionCount.coerceIn(5, 10)
		seekQuestions.progress = clampedQuestionCount - 5
		tvQuestionCount.text = getString(R.string.questions_count_format, clampedQuestionCount)

		val safeDifficultyIndex = state.difficultyIndex.coerceAtLeast(0)
		spinnerDifficulty.setSelection(safeDifficultyIndex.coerceAtMost(resources.getStringArray(R.array.difficulty_options).lastIndex), false)

		switchHints.isChecked = state.hintsEnabled
		updateHintsVisibility(state.hintsEnabled)
	}

	private fun updateHintsVisibility(isEnabled: Boolean) {
		hintsContainer.visibility = if (isEnabled) View.VISIBLE else View.GONE
		if (isEnabled) {
			tvHintsInfo.text = getString(R.string.hints_available_format, 3)
		}
	}

	private fun currentStateFromViews(): SettingsState {
		return SettingsState(
			cine = cbCine.isChecked,
			geografia = cbGeografia.isChecked,
			tecnologia = cbTecnologia.isChecked,
			deportes = cbDeportes.isChecked,
			astronomia = cbAstronomia.isChecked,
			questionCount = seekQuestions.progress + 5,
			difficultyIndex = spinnerDifficulty.selectedItemPosition,
			hintsEnabled = switchHints.isChecked
		)
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
					questionCount = json.optInt("questionCount", defaultState.questionCount),
					difficultyIndex = json.optInt("difficultyIndex", defaultState.difficultyIndex),
					hintsEnabled = json.optBoolean("hintsEnabled", defaultState.hintsEnabled)
				)
			}
		} catch (_: Exception) {
			defaultState
		}
	}

	private fun saveSettingsState(state: SettingsState) {
		try {
			val json = JSONObject().apply {
				put("cine", state.cine)
				put("geografia", state.geografia)
				put("tecnologia", state.tecnologia)
				put("deportes", state.deportes)
				put("astronomia", state.astronomia)
				put("questionCount", state.questionCount)
				put("difficultyIndex", state.difficultyIndex)
				put("hintsEnabled", state.hintsEnabled)
			}

			File(filesDir, settingsFileName).writeText(json.toString())
		} catch (_: Exception) {
		}
	}

	private fun loadSvgLabel(assetFileName: String, imageView: ImageView) {
		assets.open(assetFileName).use { inputStream ->
			val svg = SVG.getFromInputStream(inputStream)
			val pictureDrawable = PictureDrawable(svg.renderToPicture())
			imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null)
			imageView.setImageDrawable(pictureDrawable)
		}
	}

    private fun handleInvalidInputs() {
        val checkboxes = listOf(cbCine, cbGeografia, cbTecnologia, cbDeportes, cbAstronomia)
        numberOfTopicsSelected = 0
        for (checkbox in checkboxes) {
            if (checkbox.isChecked) {
                numberOfTopicsSelected++
                checkbox.isEnabled = true
            }
        }

        if (numberOfTopicsSelected == 0) {
            cbGeografia.isChecked = true
            numberOfTopicsSelected = 1
        }

        if (numberOfTopicsSelected == 1) {
            seekQuestions.progress = 0
            seekQuestions.isEnabled = false
            for (checkbox in checkboxes) {
                if (checkbox.isChecked) {
                    checkbox.isEnabled = false
                }
            }
        } else {
            seekQuestions.isEnabled = true
        }
    }
}