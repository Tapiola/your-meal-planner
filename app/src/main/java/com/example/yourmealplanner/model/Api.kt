package com.example.yourmealplanner.model

import com.example.yourmealplanner.model.recipe.RecipeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/get-recipes")
    fun getDailyRecipes(
        @Query("number") number: Long,
        @Query("diet") diet: String?,
        @Query("intolerances") intolerances: List<String>?,
        @Query("type") type: String?,
        @Query("minCarbs") minCarbs: Long?,
        @Query("maxCarbs") maxCarbs: Long?,
        @Query("minProtein") minProtein: Long?,
        @Query("maxProtein") maxProtein: Long?,
        @Query("minCalories") minCalories: Long?,
        @Query("maxCalories") maxCalories: Long?,
        @Query("minFat") minFat: Long?,
        @Query("maxFat") maxFat: Long?
    ): Call<List<RecipeResponse>>
}