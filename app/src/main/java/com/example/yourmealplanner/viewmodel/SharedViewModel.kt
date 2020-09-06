package com.example.yourmealplanner.viewmodel

import android.app.Application
import android.text.Editable
import android.widget.Spinner
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmealplanner.logic.getIdealCalories
import com.example.yourmealplanner.logic.getMacroNutrients
import com.example.yourmealplanner.logic.getPercentages
import com.example.yourmealplanner.model.Repository
import com.example.yourmealplanner.model.recipe.Recipe
import com.example.yourmealplanner.model.recipe.RecipeDatabase
import com.example.yourmealplanner.model.recipe.toRecipe
import com.example.yourmealplanner.model.user.User
//import com.example.yourmealplanner.model.recipe.Type
import com.example.yourmealplanner.model.user.UserLoginDatabase
import kotlinx.coroutines.launch
import java.util.*

class SharedViewModel(application: Application, val user: User) : AndroidViewModel(application) {
    private val repository: Repository
    val selected = MutableLiveData<String>()
    val recipes: LiveData<MutableList<Recipe>>

    var recipesByUser: LiveData<MutableList<Recipe>>? = null
    var recipesByUserFavourites: LiveData<MutableList<Recipe>>? = null
    var recipesRecyclerView: ObservableField<RecyclerView>? = null
//    var weight: ObservableField<Editable>? = null
//    var height: ObservableField<Editable>? = null
//    var age: ObservableField<Editable>? = null
//    var gender: ObservableField<String>? = null
//    var diet: ObservableField<Int>? = null
//    var activity: ObservableField<String>? = null
//    var intolerance: ObservableField<String>? = null

    fun select(item: String) {
        selected.value = item
    }

    init {
        val userLoginDao = UserLoginDatabase.getDatabase(application, viewModelScope).getUserLoginDAO()
        val recipeDao = RecipeDatabase.getDatabase(application, viewModelScope).getRecipeDAO()
        repository = Repository(userLoginDao, recipeDao)
        recipes = repository.allRecipes

        recipesByUser = repository.getTodaysRecipesForUser(user.id)
        recipesByUserFavourites = repository.getFavouriteRecipesForUser(user.id)

        saveInput()

//        weight = ObservableField(Editable.Factory.getInstance().newEditable(""))
//        height =  ObservableField(Editable.Factory.getInstance().newEditable(""))
//        age =  ObservableField(Editable.Factory.getInstance().newEditable("0"))

//        viewModelScope.launch {
//            val list = repository.getRecipesByParams(
//                10,
//                "omnivore",
//                null,
//                "MainCourse",
//                0,
//                null,
//                0,
//                null,
//                0,
//                null,
//                0,
//                null
//            )
//
//            if (list != null) {
//                for (item in list) {
//                    repository.insertRecipe(item.toRecipe(user.id))
//                }
//            }
//        }
    }

    fun saveInfo() {
        println("meow")
    }

    fun addRecipe() {
        println("MEOW")
    }

    private fun saveInput() {
        val age = 23
        val weight = 50
        val height = 160
        val gender = "female"
        val diet = "omnivore"
        val activity = "sedentary"
        val intolerance = "gluten"

        val calories = getIdealCalories(
            weight,
            height,
            age,
            diet,
            activity
        )

//        getRecipesByType("breakfast", calories, diet, listOf())
//        getRecipesByType("side dish", calories, diet, listOf())
//        getRecipesByType("main course", calories, diet, listOf())
//        getRecipesByType("snack", calories, diet, listOf())

    }

    private fun getRecipesByType(type: String,
                                 calories: Double,
                                 diet: String,
                                 intolerances: List<String>) {
        val carbohydratesPerMeal = getPercentages(
            type, getMacroNutrients("carbohydrates", calories))
        val proteinsPerMeal = getPercentages(
            type, getMacroNutrients("proteins", calories))
        val caloriesPerMeal = getPercentages(
            type, calories)
        val fatsPerMeal = getPercentages(
            type, getMacroNutrients("fats", calories))

        println("$type\n" +
                "$carbohydratesPerMeal\n" +
                "$proteinsPerMeal\n" +
                "$caloriesPerMeal\n" +
                "$fatsPerMeal")

        viewModelScope.launch {
            val list = repository.getRecipesByParams(
                10,
                diet,
                intolerances,
                type,
                carbohydratesPerMeal.toLong() - 100,
                carbohydratesPerMeal.toLong() + 100,
                proteinsPerMeal.toLong() - 100,
                proteinsPerMeal.toLong() + 100,
                caloriesPerMeal.toLong() - 100,
                caloriesPerMeal.toLong() + 100,
                fatsPerMeal.toLong() - 100,
                fatsPerMeal.toLong() + 100
            )

            if (list != null) {
                 for (item in list) {
                    repository.insertRecipe(item.toRecipe(user.id))
                }
            }
        }
    }

}
