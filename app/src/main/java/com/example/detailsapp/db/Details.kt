package com.example.detailsapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Details(
    @PrimaryKey(autoGenerate = true)
    val userId: Int,
    val name: String,
    val phoneNo1: String,
    val phoneNo2: String,
    val message: String,
    val email: String,
    val dob: String,
    val address: String
)
