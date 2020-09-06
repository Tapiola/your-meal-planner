package com.example.yourmealplanner.view.after

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmealplanner.R
import com.example.yourmealplanner.adapter.RecipesAdapter
import com.example.yourmealplanner.databinding.IntakeViewBinding
import com.example.yourmealplanner.databinding.RecipesViewBinding
import com.example.yourmealplanner.model.recipe.Recipe
import com.example.yourmealplanner.model.recipe.RecipeWrapper
import com.example.yourmealplanner.model.recipe.customGroupingComparator
import com.example.yourmealplanner.model.user.User
import com.example.yourmealplanner.viewmodel.SharedViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipesFragment: Fragment() {

    var binding: RecipesViewBinding? = null
    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: ViewGroup =
            inflater.inflate(R.layout.recipes_view, container, false) as ViewGroup
        val args: Bundle? = arguments

        binding = RecipesViewBinding.inflate(inflater,  container, false)
        binding!!.lifecycleOwner = requireActivity()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        binding?.viewmodel = viewModel

        val linearLayoutManager = LinearLayoutManager(view.context)
        binding?.recyclerViewRecipe?.layoutManager = linearLayoutManager
        val adapter = RecipesAdapter(findNavController())
        adapter.recipes = mutableListOf()
        binding?.recyclerViewRecipe?.adapter = adapter

        initObservables(view)
    }
    private fun initObservables(view: View) {
        viewModel.recipes.observe(requireActivity(), Observer {
            if (it != null) {
                println(it.size)
            }
        })
        viewModel.recipesByUser?.observe(requireActivity(), Observer {
            if (it != null) {
                showRecipes(view, it)
            }
        })
        viewModel.recipesByUserFavourites?.observe(requireActivity(), Observer {
            if (it != null) {
                println(it.size)
            }
        })
    }

    private fun showRecipes(view: View, recipes: MutableList<Recipe>) {
        recipes.sortWith(customGroupingComparator)
        val groupedRecipes = mutableListOf<RecipeWrapper>()

        for (i in 0 until recipes.size - 1) {
            if (i == 0)  groupedRecipes.add(RecipeWrapper(recipes[i].type, null))
            groupedRecipes.add(RecipeWrapper(null, recipes[i]))
            if (recipes[i].type != recipes[i+1].type) {
                groupedRecipes.add(RecipeWrapper(recipes[i+1].type, null))
            }
        }
        groupedRecipes.add(RecipeWrapper(null, recipes[recipes.size - 1]))

        (binding?.recyclerViewRecipe?.adapter as RecipesAdapter).recipes = groupedRecipes
        requireActivity().runOnUiThread {
            binding?.recyclerViewRecipe?.adapter!!.notifyDataSetChanged()
        }
    }
}