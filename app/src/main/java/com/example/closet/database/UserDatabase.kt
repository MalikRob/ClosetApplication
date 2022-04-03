package com.example.closet.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.closet.ui.User


@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {
    abstract val UserDAO: UserDAO?
}

