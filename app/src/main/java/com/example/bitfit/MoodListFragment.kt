package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Headers
import org.json.JSONException

class MoodListFragment : Fragment(){
    private val moods = mutableListOf<Mood>()
    private lateinit var moodsRecyclerView: RecyclerView
    private lateinit var moodAdapter: MoodAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Change this statement to store the view in a variable instead of a return statement
        val view = inflater.inflate(R.layout.fragment_mood_list, container, false)

        // Add these configurations for the recyclerView and to configure the adapter
        val layoutManager = LinearLayoutManager(context)
        moodsRecyclerView = view.findViewById(R.id.moods)
        moodsRecyclerView.layoutManager = layoutManager
        moodsRecyclerView.setHasFixedSize(true)
        moodAdapter = MoodAdapter(view.context, moods)
        moodsRecyclerView.adapter = moodAdapter

        // Update the return statement to return the inflated view from above
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Call the new method within onViewCreated
        moodsRecyclerView = view.findViewById(R.id.moods)
        val moodAdapter = MoodAdapter(view.context, moods)
        moodsRecyclerView.adapter = moodAdapter

        lifecycleScope.launch {
            (activity?.application as MoodApplication).db.moodDao().getAll().collect { databaseList ->
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

        moodsRecyclerView.layoutManager = LinearLayoutManager(context).also {
            val dividerItemDecoration = DividerItemDecoration(context, it.orientation)
            moodsRecyclerView.addItemDecoration(dividerItemDecoration)
        }


        //Make Button Start Detailed Activity
        val addMood = view.findViewById<Button>(R.id.submit)
        val deleteMoods = view.findViewById<Button>(R.id.delete)

        addMood.setOnClickListener{
            val intent = Intent(context, DetailActivity::class.java)
            startActivity(intent)
        }

        deleteMoods.setOnClickListener{
            lifecycleScope.launch(Dispatchers.IO) {
                (activity?.application as MoodApplication).db.moodDao().deleteAll()
            }
        }
    }
    companion object {
        fun newInstance(): MoodListFragment {
            return MoodListFragment()
        }
    }
}