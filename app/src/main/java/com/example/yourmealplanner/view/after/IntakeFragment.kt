package com.example.yourmealplanner.view.after

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.yourmealplanner.R
import com.example.yourmealplanner.adapter.TabAdapter
import com.example.yourmealplanner.databinding.EditProfileViewBinding
import com.example.yourmealplanner.databinding.IntakeViewBinding
import com.example.yourmealplanner.viewmodel.SharedViewModel
import com.google.android.material.tabs.TabLayout

class IntakeFragment: Fragment() {

    var binding: IntakeViewBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: ViewGroup =
            inflater.inflate(R.layout.intake_view, container, false) as ViewGroup
        val args: Bundle? = arguments

        binding = IntakeViewBinding.inflate(inflater,  container, false)
        binding!!.setLifecycleOwner (requireActivity())

        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}