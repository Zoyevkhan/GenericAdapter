package com.example.genericadapter.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface BaseDao<T> {

    /**
     * Insert a list in the database. If the item already exists, replace it.
     *
     * @param list the items to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)

    @JvmSuppressWildcards
    abstract fun insertAll(list: List<T>)

}