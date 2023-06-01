package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentSettingsBinding
import com.example.newsapp.util.userLogOut
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var binding:FragmentSettingsBinding
class SettingsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val auth=Firebase.auth
        binding= FragmentSettingsBinding.inflate(inflater,container,false)


        binding.logOutText.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                auth.signOut()
                userLogOut(requireContext())
                val action=SettingsFragmentDirections.actionSettingsFragmentToLoginFragment2()
                findNavController().navigate(action)
            }
        }

        binding.changeMailText.setOnClickListener {
            AlertDialog.Builder(requireContext())
        }

        binding.changePasswordText.setOnClickListener {

        }

        binding.deleteText.setOnClickListener {

        }

        return binding.root
    }

}