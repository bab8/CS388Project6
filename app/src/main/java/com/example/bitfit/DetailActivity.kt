package com.example.bitfit

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO: Get Button Ids
        val submitButton = findViewById<Button>(R.id.submit)
        val exitButton = findViewById<Button>(R.id.exit)

        // TODO: Use Button To Upload Content to Database
        submitButton.setOnClickListener{
            val InputMood = findViewById<EditText>(R.id.InputMood)
            val InputDate = findViewById<EditText>(R.id.InputDate)
            val newMood = MoodEntity(
                mood = InputMood.text.toString(),
                date = InputDate.text.toString(),
            )
            lifecycleScope.launch(Dispatchers.IO) {
                (application as MoodApplication).db.moodDao().insert(newMood)
            }

        }

        // TODO: Use Button To Exit Detail
        exitButton.setOnClickListener{
            finish()
        }


    }
}