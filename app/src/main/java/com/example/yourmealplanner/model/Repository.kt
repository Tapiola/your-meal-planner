package com.example.yourmealplanner.model

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.yourmealplanner.model.recipe.*
import com.example.yourmealplanner.model.user.User
import com.example.yourmealplanner.model.user.UserDao
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Repository (private val userDao: UserDao, private val recipeDao: RecipeDao) {

    val apiBaseUrl = "http://mealplannerserver.net:8080/"
    //http://mealplannerserver.net:8080/get-recipes?number=1&minCalories=300&type=apetizer

    val allUserLogins: LiveData<MutableList<User>> = userDao.getAll()
    val allRecipes: LiveData<MutableList<Recipe>> = recipeDao.getAllRecipes()

    fun insertUserLogin(user: User) {
        GlobalScope.launch {
            userDao.insert(user)
        }
    }

    fun deleteUserLogin(user: User) {
        GlobalScope.launch {
            userDao.delete(user)
        }
    }

    suspend fun getUser(user: User): User? {
        return withContext(Dispatchers.IO) {
            val users: MutableList<User> = userDao.getMatchingByEmail(user.email)
            users[0]
        }
    }

    suspend fun getOneUser(user: User): User? {
        return withContext(Dispatchers.IO) {
            userDao.getOneMatchingByEmail(user.email)
        }
    }

    suspend fun userExists(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            (userDao.getMatchingByEmailCount(user.email) > 0)
        }
    }

    suspend fun passwordIsCorrect(user: User): User? {
        return withContext(Dispatchers.IO) {
            val res = userDao.getMatching(user.email, user.password).value
            if (res != null && res.size > 0) res[0]
            else null
        }
    }

    suspend fun passwordIsCorrect2(user: User): Boolean {
        return withContext(Dispatchers.IO) {
            val res = userDao.getMatchingCount(user.email, user.password)
            (res > 0)
        }
    }

    fun insertRecipe(recipe: Recipe) {
        GlobalScope.launch {
            recipeDao.insert(recipe)
        }
    }

    fun deleteRecipe(recipe: Recipe) {
        GlobalScope.launch {
            recipeDao.delete(recipe)
        }
    }

    fun getTodaysRecipesForUser(userId: Long): LiveData<MutableList<Recipe>> {
        return recipeDao.getTodaysRecipesForUser(userId)

    }

    fun getFavouriteRecipesForUser(userId: Long): LiveData<MutableList<Recipe>> {
        return recipeDao.getFavouriteRecipesForUser(userId)

    }

    suspend fun getRecipesByParams(number: Long,
                           diet: String?,
                           intolerances: List<String>?,
                           type: String?,
                           minCarbs: Long?,
                           maxCarbs: Long?,
                           minProtein: Long?,
                           maxProtein: Long?,
                           minCalories: Long?,
                           maxCalories: Long?,
                           minFat: Long?,
                           maxFat: Long?): List<RecipeResponse>? {
        return withContext(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl(apiBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val api = retrofit.create(
                Api::class.java
            )
            val call = api.getDailyRecipes(
                number,
                diet,
                intolerances,
                type,
                minCarbs,
                maxCarbs,
                minProtein,
                maxProtein,
                minCalories,
                maxCalories,
                minFat,
                maxFat
            )
            call.execute().body()
        }
    }

}
