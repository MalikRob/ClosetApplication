package com.example.closet.database

import androidx.room.*
import com.example.closet.ui.User


@Dao
interface UserDAO {
    @Query("SELECT * FROM User where email= :mail and password= :password")
    fun getUser(mail: String?, password: String?): User?

    @Insert
    fun insert(user: User?)

    @Update
    fun update(user: User?)

    @Delete
    fun delete(user: User?)
}
