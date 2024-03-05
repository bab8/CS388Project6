package com.example.bitfit

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.bitfit.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.android.material.bottomnavigation.BottomNavigationView
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

        replaceFragment(MoodListFragment())
    }
    private fun replaceFragment(moodListFragment: MoodListFragment) {
        val fragmentManager = supportFragmentManager
        // define your fragments here
        val fragment1: Fragment = MoodListFragment()
        val fragment2: Fragment = SummaryFragment()

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // handle navigation selection
        bottomNavigationView.setOnItemSelectedListener { item ->
            lateinit var fragment: Fragment
            when (item.itemId) {
                R.id.Moods_list -> fragment = fragment1
                R.id.Mood_Summary -> fragment = fragment2
            }
            fragmentManager.beginTransaction().replace(R.id.mood_frame_layout, fragment).commit()
            true
        }

        // Set default selection
        bottomNavigationView.selectedItemId = R.id.Moods_list
    }
}