package com.eavesdropprivacy.aitylgames.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Details(
    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0,
    var name: String = "null",
    var phoneNo1: String = "null",
    var phoneNo2: String = "null",
    var message: String = "null",
    var email: String = "null",
    var dob: String = "null",
    var address: String = "null",
    var isMore: Boolean = false,
    var isNew: Boolean = false
)
