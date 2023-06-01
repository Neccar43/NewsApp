package com.example.newsapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsapp.databinding.FragmentSignUpBinding
import com.example.newsapp.model.User
import com.example.newsapp.util.storeUserToRoom
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private lateinit var auth:FirebaseAuth
private lateinit var binding: FragmentSignUpBinding
class SignUpFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth= Firebase.auth
        binding= FragmentSignUpBinding.inflate(inflater,container,false)



        binding.loginLayout.setOnClickListener{
            val action=SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
            findNavController().navigate(action)
        }

        binding.signInButton.setOnClickListener { view ->
            val email = binding.signInEmailText.text.toString()
            val password = binding.firstPasswordText.text.toString()
            val name = binding.signInNameText.text.toString()
            val surName = binding.signInSurnameText.text.toString()
            val password2 = binding.secondPasswordText.text.toString()

            if (email.isEmpty() || password.isEmpty() ||
                name.isEmpty() || surName.isEmpty() ||
                password2.isEmpty()) {

                Snackbar.make(view, "Alanlar dolmak zorunda.", Snackbar.LENGTH_LONG).show()

            } else if (password != password2) {
                binding.firstPasswordInput.isErrorEnabled = true
                binding.firstPasswordInput.error = "Şifreler uyuşmuyor."
                binding.secondPasswordInput.isErrorEnabled = true
                binding.secondPasswordInput.error = "Şifreler uyuşmuyor."
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                    val id = auth.currentUser!!.uid
                    val profileUpdates = userProfileChangeRequest {
                        displayName = "$name $surName"
                    }
                    auth.currentUser!!.updateProfile(profileUpdates).addOnSuccessListener {
                        CoroutineScope(Dispatchers.Main).launch {
                            storeUserToRoom(requireContext(),User(id,name,surName,email,password))
                        }
                        val action =SignUpFragmentDirections.actionSignUpFragmentToFeedFragment()
                        findNavController().navigate(action)
                    }
                }.addOnFailureListener {
                    var exception = it.message

                    when (it.message!!) {

                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred." -> {
                            exception = "Bir ağ hatası oluştu. Lütfen bağlantınızı kontrol edin."
                        }
                        "The email address is badly formatted." -> {
                            exception = "Geçerli bir E-Mail girin."
                        }
                        "The email address is already in use by another account." -> {
                            exception = "Bu E-Mail zaten kullanımda."
                            binding.signInEmailInput.isErrorEnabled = true
                            binding.signInEmailInput.error = "Bu e-mail kullanımda."
                        }
                        "The given password is invalid. [ Password should be at least 6 characters ]" -> {
                            binding.firstPasswordInput.isErrorEnabled = true
                            binding.firstPasswordInput.error =
                                "Şifre minimum 6 karakter olmalı."
                            binding.secondPasswordInput.isErrorEnabled = true
                            binding.secondPasswordInput.error =
                                "Şifre minimum 6 karakter olmalı."
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