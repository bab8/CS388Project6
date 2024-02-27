package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bitfit.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "MainActivity/"

class MainActivity : AppCompatActivity() {
    private val moods = mutableListOf<Mood>()
    private lateinit var moodsRecyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Daily Reminder
        val toast = Toast.makeText(this, "Don't Forget To Enter Today's Mood!", Toast.LENGTH_SHORT) // in Activity
        toast.show()

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        moodsRecyclerView = findViewById(R.id.moods)
        val moodAdapter = MoodAdapter(this, moods)
        moodsRecyclerView.adapter = moodAdapter

        lifecycleScope.launch {
            (application as MoodApplication).db.moodDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    Mood(
                        entity.mood,
                        entity.date
                    )
                }.also { mappedList ->
                    moods.clear()
                    moods.addAll(mappedList)
                    moodAdapter.notifyDataSetChanged()
                }
            }
        }

        moodsRecyclerView.layoutManager = LinearLayoutManager(this).also {
            val dividerItemDecoration = DividerItemDecoration(this, it.orientation)
            moodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }


        //Make Button Start Detailed Activity
        val addMood = findViewById<Button>(R.id.submit)
        val deleteMoods = findViewById<Button>(R.id.delete)

        addMood.setOnClickListener{
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            startActivity(intent)
        }

        deleteMoods.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                (application as MoodApplication).db.moodDao().deleteAll()
            }
        }

    }
}