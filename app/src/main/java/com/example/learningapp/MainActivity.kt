package com.example.learningapp

import com.example.learningapp.recyclerView.activity.RecyclerViewActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.activityLifeCycle.ActivityLifeCycle
import com.example.learningapp.retrofit.activity.RandomUserActivity
import com.example.learningapp.broadcast_reciever.BroadcastReceiverActivity
import com.example.learningapp.coroutines.CoroutinesActivity
import com.example.learningapp.database.SharedPref
import com.example.learningapp.databinding.ActivityMainBinding
import com.example.learningapp.fragmentLifeCycle.activity.FragmentLifeCycle
import com.example.learningapp.liveData.view.MainView
import com.example.learningapp.mvvm.view.ViewActivity
import com.example.learningapp.pdfCreation.PdfCreation
import com.example.learningapp.permissionHandling.PermissionActivity
import com.example.learningapp.roomDb.activity.RoomDbActivity
import com.example.learningapp.shimmerEffect.MainShimmerEffectActivity
import com.example.learningapp.sideNavigationBar.SideNavigationActivity
import com.example.learningapp.viewpager.activity.ViewPagerActivity
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBtnClick(binding.btRecyclerView , RecyclerViewActivity::class.java )
        onBtnClick(binding.btActivityLifecycle ,ActivityLifeCycle::class.java )
        onBtnClick(binding.btFragmentLifeCycle , FragmentLifeCycle::class.java )
        onBtnClick(binding.btSharedPref , SharedPref::class.java )
        onBtnClick(binding.btBroadcastReceiver , BroadcastReceiverActivity::class.java )
        onBtnClick(binding.btViewPager , ViewPagerActivity::class.java )
        onBtnClick(binding.btMvvm , ViewActivity::class.java )
        onBtnClick(binding.btLiveData , MainView::class.java )
        onBtnClick(binding.btRetrofit , RandomUserActivity::class.java )
        onBtnClick(binding.btRoomDb , RoomDbActivity::class.java )
        onBtnClick(binding.btPermissionHandling , PermissionActivity::class.java )
        onBtnClick(binding.btCoroutines , CoroutinesActivity::class.java )
        onBtnClick(binding.btPdf , PdfCreation::class.java )
        onBtnClick(binding.btShimmerEffect , MainShimmerEffectActivity::class.java )
        onBtnClick(binding.btSideNavigationBar , SideNavigationActivity::class.java )
    }

    private fun onBtnClick(btn : MaterialButton, targetActivity: Class<out AppCompatActivity>){
        btn.setOnClickListener{
            val intent = Intent(this , targetActivity  )
            startActivity(intent)
        }
    }
}