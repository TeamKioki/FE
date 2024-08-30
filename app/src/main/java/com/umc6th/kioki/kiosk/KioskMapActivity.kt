package com.umc6th.kioki.kiosk

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.umc6th.kioki.databinding.ActivityKioskmapBinding

class KioskMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKioskmapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKioskmapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kioskmapBackBtn.setOnClickListener {
            finish()
        }

        binding.kioskmapMapIv.setOnClickListener {
            val intent = Intent(this, KioskMap2Activity::class.java)
            startActivity(intent)
        }
    }
//    private val LOCATION_PERMISSION_REQUEST_CODE = 5000
//, OnMapReadyCallback
//    private val PERMISSIONS = arrayOf(
//        Manifest.permission.ACCESS_FINE_LOCATION,
//        Manifest.permission.ACCESS_COARSE_LOCATION
//    )
//
//    private lateinit var binding: ActivityKioskmapBinding
//    private lateinit var naverMap: NaverMap
//    private lateinit var locationSource: FusedLocationSource
//
//    // onCreate에서 권한을 확인하며 위치 권한이 없을 경우 사용자에게 권한을 요청한다.
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityKioskmapBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        NaverMapSdk.getInstance(this).client =
//            NaverMapSdk.NaverCloudPlatformClient("fameqf3adq")
//
//        if (!hasPermission()) {
//            ActivityCompat.requestPermissions(this, PERMISSIONS, LOCATION_PERMISSION_REQUEST_CODE)
//        } else {
//            initMapView()
//        }
//
////        //마커 표시 예시 -> 하니까 안됨
////        val marker = Marker()
////        marker.position = LatLng(37.5670135, 126.9783740)
////        marker.map = naverMap
////        marker.icon = OverlayImage.fromResource(R.drawable.burgerking)
////        marker.icon = MarkerIcons.BLACK
////        marker.iconTintColor = Color.WHITE
////        marker.width = 40
////        marker.height = 43
////        marker.width = Marker.SIZE_AUTO
////        marker.height = Marker.SIZE_AUTO
//    }
//
//    private fun initMapView() {
//        val fm = supportFragmentManager
//        val mapFragment = fm.findFragmentById(com.umc6th.kioki.R.id.kioskmap_map_vw) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                fm.beginTransaction().add(com.umc6th.kioki.R.id.kioskmap_map_vw, it).commit()
//            }
//
//        // fragment의 getMapAsync() 메서드로 OnMapReadyCallback 콜백을 등록하면 비동기로 NaverMap 객체를 얻을 수 있다.
//        mapFragment.getMapAsync(this)
//        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
//    }
//
//    // hasPermission()에서는 위치 권한이 있을 경우 true를, 없을 경우 false를 반환한다.
//    private fun hasPermission(): Boolean {
//        for (permission in PERMISSIONS) {
//            if (ContextCompat.checkSelfPermission(this, permission)
//                != PackageManager.PERMISSION_GRANTED
//            ) {
//                return false
//            }
//        }
//        return true
//    }
//
//    override fun onMapReady(naverMap: NaverMap) {
//        this.naverMap = naverMap
//        // 현재 위치
//        naverMap.locationSource = locationSource
//        // 현재 위치 버튼 기능
//        naverMap.uiSettings.isLocationButtonEnabled = true
//        // 위치를 추적하면서 카메라도 따라 움직인다.
//        naverMap.locationTrackingMode = LocationTrackingMode.Follow
//    }
}