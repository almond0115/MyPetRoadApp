package com.mju.capstone.mypetRoad.views.feature.walking

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.RequiresApi
import androidx.annotation.UiThread
import androidx.core.os.HandlerCompat
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.mju.capstone.mypetRoad.R
import com.mju.capstone.mypetRoad.data.retrofit.RetrofitManager
import com.mju.capstone.mypetRoad.databinding.FragmentWalkingHomeBinding
import com.mju.capstone.mypetRoad.domain.model.WalkingLog
import com.mju.capstone.mypetRoad.util.Config
import com.mju.capstone.mypetRoad.util.Route
import com.mju.capstone.mypetRoad.views.MainActivity
import com.mju.capstone.mypetRoad.views.base.BaseFragment
import com.mju.capstone.mypetRoad.widget.Adapter.WalkingLogAdapter
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class WalkingHomeFragment : BaseFragment<FragmentWalkingHomeBinding>(), OnMapReadyCallback {

    override fun getViewBinding() = FragmentWalkingHomeBinding.inflate(layoutInflater)

    private val LOCATION_PERMISSION_REQUEST_CODE : Int = 1000

    private lateinit var uiScope: CoroutineScope
    private lateinit var locationSource: FusedLocationSource
    lateinit var naverMap: NaverMap
    private lateinit var mapView: MapView
//    private var timer: Timer? = null
    lateinit var mainActivity: MainActivity
    private val walkingViewModel by viewModels<WalkingViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Context를 액티비티로 형변환해서 할당(토큰 받아올 때 쓰임)
        mainActivity = context as MainActivity
    }

    override fun initState() {
        super.initState()
        uiScope = CoroutineScope(Dispatchers.Main)
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        mapView = binding.walkingMapView
        mapView.getMapAsync(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initViews() {
        super.initViews()
        binding.walkingViewModel = walkingViewModel //ViewModel설정
        walkingViewModel.petInfoUpdateText()
        val viewPager = binding.walkingLogViewPager
        val buttonAnimation = AnimationUtils.loadAnimation(requireContext(), R.animator.button_animation)

        var walkingLog: List<WalkingLog> = listOf(
            WalkingLog(R.drawable.sample_dog, "2023/05/17", 23f, 110, "10'55\"",0),
            WalkingLog(R.drawable.sample_dduzzi, "2022/04/20", 12f, 73, "20'44\"",0),
            WalkingLog(R.drawable.sample_dduzzi, "2021/03/23", 18f, 134, "30'33\"",0),
            WalkingLog(R.drawable.sample_dog2, "2020/02/26", 12f, 89, "06:08",0),
            WalkingLog(R.drawable.sample_dduzzi, "2019/01/29", 33f, 134, "50'11\"",0)
        )

        val walkingLogAdapter = WalkingLogAdapter(walkingLog)
        viewPager.adapter = walkingLogAdapter
        viewPager.setPadding(30, 0, 30, 0)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        binding.btnWalkingStart.setOnClickListener {

            binding.homePetCard.startAnimation(buttonAnimation)
            //네비게이션 바꾸기
            val navController = findNavController()
            val graph = navController.navInflater.inflate(R.navigation.walking_nav_graph)
            navController.graph = graph
            Route.clearPing()

            try {
                Config.isWalking = true
                Config.startDate = Date()
                view?.let { walkingMode ->
                    Navigation.findNavController(walkingMode)
                        .navigate(R.id.action_walkingHomeFragment_to_walkingStartFragment)
                }
            } catch (e: IllegalArgumentException) {
                Log.e("산책에러", "IllegalArgumentException")
            }
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map.apply {
            uiSettings.isLocationButtonEnabled = false
            uiSettings.isScaleBarEnabled = false
            uiSettings.isCompassEnabled = false
            uiSettings.isZoomControlEnabled = false
            uiSettings.setLogoMargin(20, 20, 100, 2740)
            isIndoorEnabled = false // 실내 지도
            isLiteModeEnabled = false // 라이트모드
            lightness = -0.5f // 지도 밝기
            buildingHeight = 0.8f // 건물 높이
        }
    }
}