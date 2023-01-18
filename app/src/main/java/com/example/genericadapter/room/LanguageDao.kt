package com.example.genericadapter.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.genericadapter.model.Language
import com.example.genericadapter.model.State

@Dao
interface LanguageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Language)

    @Insert
    fun insertAll(note: List<Language>)
    fun insertAll1(note: List<State>)

    @Query("SELECT * FROM employeeTbl ")
    fun getAllLanguage(): LiveData<List<Language>>

    @Query("SELECT * FROM employeeTbl where employeeTbl.isSelected =:selected  ")
   suspend fun getAllLanguageSimple(selected:Boolean): List<Language>
}