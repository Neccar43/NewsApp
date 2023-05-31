package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentLoginBinding
import com.example.newsapp.databinding.FragmentSignUpBinding

private lateinit var binding: FragmentSignUpBinding
class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSignUpBinding.inflate(inflater,container,false)

        binding.signInButton.setOnClickListener {
            val action=SignUpFragmentDirections.actionSignUpFragmentToFeedFragment()
            findNavController().navigate(action)
        }

        binding.loginLayout.setOnClickListener{
            val action=SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }





        return binding.root
    }

}