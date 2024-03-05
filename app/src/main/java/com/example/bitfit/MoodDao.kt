package com.example.bitfit

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

@Dao
interface MoodDao {
    @Query("SELECT * FROM mood_table")
    fun getAll(): Flow<List<MoodEntity>>

    @Insert
    fun insert(moodEntity: MoodEntity)

    @Query("SELECT * FROM mood_table GROUP BY mood ORDER BY COUNT(*) DESC LIMIT 1")
    fun mostPopular(): Flow<List<MoodEntity>>

    @Query("SELECT * FROM mood_table ORDER BY date DESC LIMIT 1")
    fun mostRecent(): Flow<List<MoodEntity>>

    @Query("SELECT * FROM mood_table LIMIT 1")
    fun firstMood(): Flow<List<MoodEntity>>

    @Query("DELETE FROM mood_table")
    fun deleteAll()
}