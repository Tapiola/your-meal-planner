package com.example.yourmealplanner.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.yourmealplanner.R
import com.example.yourmealplanner.model.recipe.Recipe
import com.example.yourmealplanner.model.recipe.RecipeWrapper
import com.google.gson.Gson
import com.squareup.picasso.Picasso;
import java.lang.NullPointerException
import java.util.*

class RecipesAdapter(val navController: NavController): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val HEADER = 0
    private val CONTENT = 1
    lateinit var recipes: MutableList<RecipeWrapper>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            HEADER -> HeaderViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.header_item, parent, false)
            )
            CONTENT -> RecipesViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.recipe_cell_item, parent, false)
            )
            else -> throw NullPointerException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (recipes[position].recipe == null)
            return HEADER
        else return CONTENT
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val contentItem = recipes[position]

        if (contentItem.recipe == null) {
            val viewHolder: HeaderViewHolder = holder as HeaderViewHolder
            viewHolder.bindItems(contentItem)
        } else {
            val viewHolder: RecipesViewHolder = holder as RecipesViewHolder
            viewHolder.bindItems(contentItem)

            (viewHolder.itemView).setOnClickListener { view ->
                val args: Bundle = insertBundleData(contentItem.recipe)
                navController.navigate(R.id.action_mainFragment_to_recipeFragment, args);
            }
        }

    }
    
    private fun insertBundleData(contentItem: Recipe): Bundle {
        val args = Bundle()
        args.putString("recipe", Gson().toJson(contentItem))
        return args
    }
}

class RecipesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bindItems(contentItem: RecipeWrapper) {
        val imageView = itemView.findViewById<ImageView>(R.id.recipe_icon)
        Picasso.get().load(contentItem.recipe!!.image).into(imageView);
        itemView.findViewById<TextView>(R.id.recipe_name).text = contentItem.recipe.title
        itemView.findViewById<TextView>(R.id.recipe_preview).text = contentItem.recipe.recipeContent
    }
}

class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    fun bindItems(contentItem: RecipeWrapper) {
        val value = contentItem.header!!.toUpperCase(Locale.ROOT) + "S:"
        itemView.findViewById<TextView>(R.id.meal_type).text = value
    }
}