package com.example.yourmealplanner.view.after

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.yourmealplanner.R
import com.example.yourmealplanner.adapter.TabAdapter
import com.example.yourmealplanner.databinding.MainViewBinding
import com.example.yourmealplanner.model.user.User
import com.example.yourmealplanner.viewmodel.SharedViewModel
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainFragment: Fragment() {

    var binding: MainViewBinding? = null
    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: ViewGroup =
            inflater.inflate(R.layout.main_view, container, false) as ViewGroup
        val args: Bundle? = arguments

        return rootView

    }

    private fun setTabs(view: View) {

        val adapter = TabAdapter(this, 3)
        binding!!.viewPager.adapter = adapter


        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "MEALS"}
                    1 -> { tab.text = "PROFILE"}
                    2 -> { tab.text = "INTAKE"}
                }
            }).attach()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args : Bundle? = arguments
        val userJson = args!!.getString("user")
        val user: User = Gson().fromJson(userJson, object: TypeToken<User>() {
        }.type)

        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_view)
        viewModel = ViewModelProviders.of(requireActivity(), object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return SharedViewModel(requireActivity().application, user) as T
            }
        }).get(SharedViewModel::class.java)

//        binding = DataBindingUtil.setContentView(requireActivity(), R.layout.main_view)
//        viewModel = ViewModelProviders.of(requireActivity()).get(SharedViewModel::class.java)
        binding?.viewmodel = viewModel

        setTabs(view)
    }


}
