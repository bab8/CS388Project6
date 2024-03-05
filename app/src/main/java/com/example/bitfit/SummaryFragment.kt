package com.example.bitfit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONObject

class SummaryFragment : Fragment() {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_summary, container, false)
        val context = view.context
        val popularMood = view.findViewById<TextView>(R.id.popularMood)
        val popularDate = view.findViewById<TextView>(R.id.populardate)
        val firstMood = view.findViewById<TextView>(R.id.firstMood)
        val firstDate = view.findViewById<TextView>(R.id.firstdate)
        val latestMood = view.findViewById<TextView>(R.id.latestMood)
        val latestDate = view.findViewById<TextView>(R.id.latestdate)

        lifecycleScope.launch {
            val mood = (activity?.application as MoodApplication).db.moodDao().mostPopular().collect { databaseList ->
                databaseList.map { entity ->
                    Mood(
                        entity.mood,
                        entity.date
                    )
                }.also { mappedList ->
                    popularMood.text = mappedList[0].mood;
                    popularDate.text = mappedList[0].date;
                }
            }
        }
        lifecycleScope.launch {
            val mood = (activity?.application as MoodApplication).db.moodDao().firstMood().collect { databaseList ->
                databaseList.map { entity ->
                    Mood(
                        entity.mood,
                        entity.date
                    )
                }.also { mappedList ->
                    firstMood.text = mappedList[0].mood;
                    firstDate.text = mappedList[0].date;
                }
            }
        }
        lifecycleScope.launch {
            val mood = (activity?.application as MoodApplication).db.moodDao().mostRecent().collect { databaseList ->
                databaseList.map { entity ->
                    Mood(
                        entity.mood,
                        entity.date
                    )
                }.also { mappedList ->
                    latestMood.text = mappedList[0].mood;
                    latestDate.text = mappedList[0].date;
                }
            }
        }
        return view
    }

}