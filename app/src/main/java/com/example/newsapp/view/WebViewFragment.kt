package com.example.newsapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.newsapp.databinding.FragmentWebViewBinding

class WebViewFragment : Fragment() {
    private lateinit var binding:FragmentWebViewBinding
    private val args:WebViewFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val url =args.url
        binding= FragmentWebViewBinding.inflate(inflater,container,false)

        binding.webView.apply {
            loadUrl(url)
            settings.javaScriptEnabled=true
        }



        return binding.root
    }

}