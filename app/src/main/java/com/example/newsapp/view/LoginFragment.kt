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
import com.example.newsapp.model.User
import com.example.newsapp.util.isUserExistInRoom
import com.example.newsapp.util.storeUserToRoom
import com.example.newsapp.util.userLogin
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var binding: FragmentLoginBinding

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val auth = Firebase.auth
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val action =
                LoginFragmentDirections.actionLoginFragmentToFeedFragment()
            findNavController().navigate(action)
        }


        binding.createAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToSignUpFragment()
            findNavController().navigate(action)
        }

        binding.loginButton.setOnClickListener { view ->

            val email = binding.loginEmailText.text.toString()
            val password = binding.passwordText.text.toString()
            if (email.isEmpty()) {
                binding.loginEmailInput.isErrorEnabled = true
                binding.loginEmailInput.error = "Mail adresinizi girin."

            } else if (password.isEmpty()) {
                binding.loginPasswordInput.isErrorEnabled = true
                binding.loginPasswordInput.error = "Şifrenizi girin."
            } else {

                auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
                    val id = auth.currentUser!!.uid
                    val fullName = auth.currentUser!!.displayName!!
                    val parts = fullName.split(" ")
                    val name = parts.first()
                    val lastName = parts.last()

                    CoroutineScope(Dispatchers.Main).launch {
                        if (!isUserExistInRoom(requireContext(), id)) {
                            storeUserToRoom(
                                requireContext(),
                                User(id, name, lastName, email, password)
                            )
                        }
                        userLogin(requireContext(),email,password)
                        val action =
                        LoginFragmentDirections.actionLoginFragmentToFeedFragment()
                        findNavController().navigate(action)
                    }


                }
                    .addOnFailureListener {
                        var exception = it.message

                        when (it.message!!) {
                            "There is no user record corresponding to this identifier. The user may have been deleted." -> {
                                exception = "Böyle bir hesap bulunamadı."
                            }

                            "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> {
                                exception =
                                    "Bir ağ hatası oluştu. Lütfen bağlantınızı kontrol edin."
                            }

                            "The email address is badly formatted." -> {
                                exception = "Geçerli bir E-Mail girin."
                            }

                            "The password is invalid or the user does not have a password." -> {
                                binding.loginPasswordInput.isErrorEnabled = true
                                binding.loginPasswordInput.error = "Geçersiz şifre."
                                exception = null
                            }
                        }
                        if (exception != null) {
                            Snackbar.make(view, exception, Snackbar.LENGTH_LONG).show()
                        }

                    }
            }

        }

        return binding.root
    }

}