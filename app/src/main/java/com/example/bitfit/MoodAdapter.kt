package com.example.bitfit

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MoodAdapter(private val context: Context, private val moods: List<Mood>) :
    RecyclerView.Adapter<MoodAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.mood_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val article = moods[position]
        holder.bind(article)
    }

    override fun getItemCount() = moods.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val moodTextView = itemView.findViewById<TextView>(R.id.mood)
        private val dateTextView = itemView.findViewById<TextView>(R.id.date)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(mood: Mood) {
            moodTextView.text = mood.mood
            dateTextView.text = mood.date

        }
        override fun onClick(v: View?) {
            // TODO: Get selected article
            val mood = moods[absoluteAdapterPosition]
        }
    }


}