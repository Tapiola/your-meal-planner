package com.example.yourmealplanner.view.after

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.yourmealplanner.R
import com.example.yourmealplanner.databinding.RecipeViewBinding
import com.example.yourmealplanner.databinding.RecipesViewBinding
import com.example.yourmealplanner.model.recipe.Recipe
import com.example.yourmealplanner.viewmodel.SharedViewModel
import com.example.yourmealplanner.viewmodel.UserLoginViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso

class RecipeFragment: Fragment() {

    var binding: RecipeViewBinding? = null
    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: ViewGroup =
            inflater.inflate(R.layout.recipe_view, container, false) as ViewGroup
        val args: Bundle? = arguments

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : Bundle? = arguments
        val recipeJson = args!!.getString("recipe")
        val recipe: Recipe = Gson().fromJson(recipeJson, object: TypeToken<Recipe>() {
        }.type)

        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.recipe_view)
        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        binding?.viewmodel = viewModel

        Picasso.get().load(recipe.image).into(binding?.recipeIcon);
        binding?.recipeName?.text = recipe.title
        binding?.recipeIngredients?.text = recipe.ingredients
        val nutrition = "Calories: ${recipe.calories}\n" +
                "Proteins: ${recipe.proteins}\n" +
                "Fats: ${recipe.fats}\n" +
                "Carbohydrates: ${recipe.carbohydrates}\n"
        binding?.recipeNutrition?.text = nutrition
        binding?.recipeContent?.text = recipe.recipeContent

    }
}