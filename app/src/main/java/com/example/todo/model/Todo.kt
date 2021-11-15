package com.example.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo_List")
data class Todo (
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @ColumnInfo(name = "todoName")
    val name:String,
    @ColumnInfo(name = "currentDate")
    val date:String,
    @ColumnInfo(name = "deadline")
    val Deadline:String
        )