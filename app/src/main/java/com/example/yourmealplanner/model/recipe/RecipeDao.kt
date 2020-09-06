package com.example.yourmealplanner.model.recipe

import androidx.lifecycle.LiveData
import androidx.room.*
import java.time.LocalDateTime
import java.util.*

@Dao
interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): LiveData<MutableList<Recipe>>

    @Delete
    fun delete(recipe: Recipe)

    @Query("SELECT * FROM recipes WHERE userId = :userId ")
//            "AND dateCreated = DATETIME( ' :today ' )")
    fun getTodaysRecipesForUser(userId: Long): LiveData<MutableList<Recipe>>

    @Query("SELECT * FROM recipes WHERE userId = :userId AND isFavourite")
    fun getFavouriteRecipesForUser(userId: Long): LiveData<MutableList<Recipe>>

}