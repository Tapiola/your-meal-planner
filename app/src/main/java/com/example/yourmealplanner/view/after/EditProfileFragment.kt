package com.example.yourmealplanner.view.after

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.yourmealplanner.R
import com.example.yourmealplanner.databinding.EditProfileViewBinding
import com.example.yourmealplanner.model.user.User
import com.example.yourmealplanner.viewmodel.SharedViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class EditProfileFragment: Fragment() {

    var binding: EditProfileViewBinding? = null
    lateinit var viewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: ViewGroup =
            inflater.inflate(R.layout.edit_profile_view, container, false) as ViewGroup
        val args: Bundle? = arguments

        binding = EditProfileViewBinding.inflate(inflater,  container, false)
        binding!!.lifecycleOwner = requireActivity()

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args : Bundle? = arguments

        if (args != null && args.containsKey("user")) {
            val userJson = args!!.getString("user")
            val user: User = Gson().fromJson(userJson, object: TypeToken<User>() {
            }.type)
            viewModel =
                ViewModelProviders.of(requireActivity(), object : ViewModelProvider.Factory {
                    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                        @Suppress("UNCHECKED_CAST")
                        return SharedViewModel(requireActivity().application, user) as T
                    }
                }).get(SharedViewModel::class.java)
        }

        val adapterGender = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            (resources.getStringArray(R.array.genders)))
        binding!!.spinnerGender.adapter = adapterGender

        val spinnerActivity = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            (resources.getStringArray(R.array.activity)))
        binding!!.spinnerActivity.adapter = spinnerActivity

        val spinnerDiet = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            (resources.getStringArray(R.array.diet)))
        binding!!.spinnerDiet.adapter = spinnerDiet

        val spinnerIntolerances = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            (resources.getStringArray(R.array.intolerances)))
        binding!!.spinnerIntolerances.adapter = spinnerIntolerances
    }

}