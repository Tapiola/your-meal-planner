package com.example.yourmealplanner.model.user

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User): Long

    @Query("SELECT * FROM users WHERE email==:email")
    fun getMatchingByEmail(email: String): MutableList<User>

    @Query("SELECT * FROM users WHERE email==:email LIMIT 1")
    fun getOneMatchingByEmail(email: String): User?

    @Query("SELECT COUNT(*) FROM users WHERE email==:email")
    fun getMatchingByEmailCount(email: String): Int

    @Query("SELECT COUNT(*) FROM users WHERE email==:email AND password==:password")
    fun getMatchingCount(email: String, password: String): Int

    @Query("SELECT * FROM users WHERE email==:email AND password==:password")
    fun getMatching(email: String, password: String): LiveData<MutableList<User>>

    @Query("SELECT * FROM users")
    fun getAll(): LiveData<MutableList<User>>

    @Delete
    fun delete(user: User)
}