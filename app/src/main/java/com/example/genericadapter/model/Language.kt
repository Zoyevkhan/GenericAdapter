package com.example.genericadapter.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "employeeTbl")
data class Language(
    var title:String?=null,
    var isSelected:Boolean=false
){
    @PrimaryKey(autoGenerate = true)
    var foodId: Int = 0
}



