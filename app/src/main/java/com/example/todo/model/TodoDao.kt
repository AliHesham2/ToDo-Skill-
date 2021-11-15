package com.example.todo.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface TodoDao {
    @Insert
    suspend fun insert( vararg data: Todo)

    @Update
    suspend fun update(data: Todo)

    @Query("DELETE FROM Todo_List WHERE id=:itemID")
    suspend fun delete(itemID:Int)

    @Query("SELECT * FROM Todo_List")
    suspend  fun getAllData(): List<Todo>

    @Query("SELECT * from Todo_List WHERE id = :itemID")
     suspend  fun getDataWithId(itemID: Int): Todo
}