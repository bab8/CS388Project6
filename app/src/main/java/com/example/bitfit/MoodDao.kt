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

    @Query("DELETE FROM mood_table")
    fun deleteAll()
}