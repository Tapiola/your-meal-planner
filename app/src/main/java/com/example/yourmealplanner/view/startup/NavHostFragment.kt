package com.example.yourmealplanner.view.startup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.yourmealplanner.R
import com.example.yourmealplanner.databinding.LoginViewBinding
import com.example.yourmealplanner.viewmodel.UserLoginViewModel
import com.google.gson.Gson

class NavHostFragment: Fragment() {

    var loginBinding: LoginViewBinding? = null
    private var loginViewModel: UserLoginViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val rootView: ViewGroup =
            inflater.inflate(R.layout.login_view, container, false) as ViewGroup
        val args: Bundle? = arguments

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.login_view)
        loginViewModel = ViewModelProviders.of(this).get(UserLoginViewModel::class.java)
        loginBinding?.viewmodel = loginViewModel
        initObservables()
    }

    private fun initObservables() {
        loginViewModel?.navigateUserSignIn?.observe(viewLifecycleOwner, Observer {
            it.getContentIfNotHandled()?.let {
                val args = Bundle()
                args.putString("user", Gson().toJson(it))
                findNavController().navigate(R.id.action_navHostFragment_to_mainFragment, args)
            }
        })

//        loginViewModel?.navigateUserSignUp?.observe(viewLifecycleOwner, Observer {
//            it.getContentIfNotHandled()?.let {
//                val args = Bundle()
//                args.putString("user", Gson().toJson(it))
//                findNavController().navigate(R.id.action_navHostFragment_to_editProfileFragment, args)
//            }
//        })

        loginViewModel?.signInSuccessful?.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    //findNavController().navigate(R.id.action_navHostFragment_to_mainFragment)
                    Toast.makeText(requireActivity(), "Sign in successful!", Toast.LENGTH_LONG).show()
                }
                false -> {
                    Toast.makeText(requireActivity(), "Email or password incorrect!", Toast.LENGTH_LONG).show()
                }
                else -> {}
            }
        })

    }
}
