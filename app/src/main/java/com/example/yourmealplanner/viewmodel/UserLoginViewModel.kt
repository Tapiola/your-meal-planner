package com.example.yourmealplanner.viewmodel

import android.app.Application
import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.yourmealplanner.model.user.User
import com.example.yourmealplanner.model.user.UserLoginDatabase
import com.example.yourmealplanner.model.Repository
import com.example.yourmealplanner.model.recipe.*
import com.example.yourmealplanner.utils.sha256
import kotlinx.coroutines.launch

class UserLoginViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: Repository
    val allUserLogins: LiveData<MutableList<User>>

    var email: ObservableField<String>? = null
    var password: ObservableField<String>? = null
    var rememberMeSelected: ObservableBoolean? = null
    val signInSuccessful: MutableLiveData<Boolean?> = MutableLiveData(null)
    val signUpSuccessful: MutableLiveData<Boolean?> = MutableLiveData(null)
    val _navigateUserSignIn = MutableLiveData<Event<User?>>()
    val _navigateUserSignUp = MutableLiveData<Event<User?>>()

    init {
        val userLoginDao = UserLoginDatabase.getDatabase(application, viewModelScope).getUserLoginDAO()
        val recipeDao = RecipeDatabase.getDatabase(application, viewModelScope).getRecipeDAO()
        repository = Repository(userLoginDao, recipeDao)
        allUserLogins = repository.allUserLogins

        email = ObservableField("")
        password = ObservableField("")
        rememberMeSelected = ObservableBoolean(false)
    }

    val navigateUserSignIn : MutableLiveData<Event<User?>>
        get() = _navigateUserSignIn

    val navigateUserSignUp : MutableLiveData<Event<User?>>
        get() = _navigateUserSignUp


    fun insert(user: User) {
        viewModelScope.launch {
            repository.insertUserLogin(user)
        }
    }

    fun deleteUserLogin(user: User) {
        viewModelScope.launch {
            repository.deleteUserLogin(user)
        }
    }

    fun signin() {
        viewModelScope.launch {
            val emailToSave = email?.get()!!
            val passwordToSave = password?.get()!!.sha256()

            val user = User(
                emailToSave,
                passwordToSave
            )

            if (repository.userExists(user)) {
                if (repository.passwordIsCorrect2(user)) {
                    signInSuccessful.value = true
                    val userDB = repository.getUser(user)
                    if (userDB != null)
                        _navigateUserSignIn.value = Event(userDB)
                } else signInSuccessful.value = false
            } else {
                signInSuccessful.value = false
            }
        }
    }

    fun signup() {
        viewModelScope.launch {
            val emailToSave = email?.get()!!
            val passwordToSave = password?.get()!!.sha256()

            val user = User(
                emailToSave,
                passwordToSave
            )
            if (!repository.userExists(user)) {
                repository.insertUserLogin(user)
                signUpSuccessful.value = true
//                val userDB = repository.getOneUser(user)
//                if (userDB != null)
//                    _navigateUserSignUp.value = Event(userDB)
            }
        }
    }

}