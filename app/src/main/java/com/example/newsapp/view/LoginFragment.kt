package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.databinding.FragmentLoginBinding

private lateinit var binding: FragmentLoginBinding
class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.loginButton.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToFeedFragment()
            findNavController().navigate(action)
        }

        binding.createAccount.setOnClickListener {
            val action=LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        return binding.root
    }

}