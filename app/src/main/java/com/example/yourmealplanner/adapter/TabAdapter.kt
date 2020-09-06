package com.example.yourmealplanner.adapter

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.yourmealplanner.view.after.EditProfileFragment
import com.example.yourmealplanner.view.after.IntakeFragment
import com.example.yourmealplanner.view.after.RecipesFragment

class TabAdapter(fragment: Fragment, private var totalTabs: Int) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                RecipesFragment()
            }
            1 -> {
                EditProfileFragment()
            }
            2 -> {
                IntakeFragment()
            }
            else -> EditProfileFragment()
        }
    }

}