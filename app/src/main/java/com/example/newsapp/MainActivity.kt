package com.example.newsapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.notification.NotificationWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.concurrent.TimeUnit

private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setupWithNavController(navController)

        navController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.signUpFragment,R.id.loginFragment-> binding.bottomNavigationView.visibility = View.GONE
                else->binding.bottomNavigationView.visibility = View.VISIBLE
            }
        }







        //wormanager için çalışma koşulumuzu belirledik: Internete bağlı olmak
        val constraints=Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        //work manager kullanarak periodik bildirim oluşturma
        val request= PeriodicWorkRequestBuilder<NotificationWorker>(10,TimeUnit.MINUTES)
            .setInitialDelay(10,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()

        //isteğimizi kuyruğa ekledik
        WorkManager.getInstance(this).enqueue(request)

        //isteğimizi dinledik
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
            .observe(this){
                val state=it.state.name
                println(state)
            }






    }
}