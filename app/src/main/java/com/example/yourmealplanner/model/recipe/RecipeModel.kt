package com.example.yourmealplanner.model.recipe

import androidx.room.*
import com.example.yourmealplanner.model.user.User
import java.util.*
import kotlin.Comparator

@Entity(tableName = "recipes")
data class Recipe (
    val title: String,
    val recipeContent: String,
    val isFavourite: Boolean,
    val calories: Double,
    val proteins: Double,
    val fats: Double,
    val carbohydrates: Double,
    val type: String,
    val ingredients: String,
    val image: String,
    val dateCreated: Date,
    val userId: Long,
    @PrimaryKey(autoGenerate = true) var recipe_id: Long = 0
)

data class RecipeResponse (
    var title: String = "",
    var recipeContent: String,
    var calories: Double,
    var proteins: Double,
    var fats: Double,
    var carbohydrates: Double,
    var type: String?,
    var ingredients: String,
    var image: String
)

fun RecipeResponse.toRecipe(user_id: Long): Recipe {
    return Recipe (
        this.title,
        this.recipeContent,
        false,
        this.calories,
        this.proteins,
        this.fats,
        this.carbohydrates,
        this.type ?: "snack",
        this.ingredients,
        this.image,
        Date(),
        user_id
    )
}

data class RecipeWrapper (
    val header: String?,
    val recipe: Recipe?
)

val customGroupingComparator = Comparator<Recipe> {  a, b ->
    val list = listOf("breakfast", "side dish", "main course", "snack")

    when {
        list.indexOf(a.type) > list.indexOf(b.type) -> 1
        list.indexOf(a.type) == list.indexOf(b.type) -> 0
        else -> -1
    }
}