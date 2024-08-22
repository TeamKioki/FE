package com.umc6th.kioki.join

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.join.adapter.PermissionAdapter
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentRequirePermissionBinding

class RequirePermissionFragment : Fragment() {

    private lateinit var binding: FragmentRequirePermissionBinding
    private val permissionAdapter: PermissionAdapter by lazy { PermissionAdapter() }
    private val permissions = listOf(
        PermissionItem(
            R.drawable.location, "위치", "(필수)",
            "내 위치와 주변의 키오스크를 찾기 위해 필요해요"
        ),
        PermissionItem(R.drawable.camera, "카메라", "(필수)", "앱 내의 오류와 질문, 건의사항을 위한 사진을\n제출하기 위해 필요해요"),
        PermissionItem(
            R.drawable.phonebook,
            "주소록",
            "(필수)",
            "앱 내의 오류와 질문, 건의사항을 위한 사진을\n제출하기 위해 필요해요"
        ),
        PermissionItem(
            R.drawable.imagelibrary,
            "사진 라이브러리",
            "(선택)",
            "앱 내의 오류와 질문, 건의사항을 위한 사진을\n제출하기 위해 필요해요"
        ),
        PermissionItem(R.drawable.notification, "알림", "(선택)", "이벤트 및 다양한 혜택 등 필요한 알림을 보내줘요")
    )

    // 권한 요청을 처리하는 ActivityResultLauncher 등록
    private lateinit var requestPermissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequirePermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // ActivityResultLauncher 초기화
        requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            // 권한 요청 결과를 처리
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                showPermissionDialog()
            } else {
                Toast.makeText(requireContext(), "모든 권한을 허용해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.permissionListView.apply {
            adapter = permissionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        permissionAdapter.setList(permissions)

        // 권한 요청 버튼 클릭 리스너 설정
        binding.nextButton.setOnClickListener {
            askAllPermissions()
        }
    }

    private fun askAllPermissions() {
        // 요청할 권한 리스트
        val permissionsToRequest = mutableListOf<String>()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        } else {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }

        permissionsToRequest.add(Manifest.permission.READ_CONTACTS)

        // 필수 권한이 모두 승인되었는지 확인
        val permissionsToRequestFinal = permissionsToRequest.filter {
            ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_GRANTED
        }

        if (permissionsToRequestFinal.isEmpty()) {
            showPermissionDialog()
        } else {
            Log.d("REQUIREPERMISSIONFRAGMENT", "askAllPermissions: $permissionsToRequestFinal")
            requestPermissionLauncher.launch(permissionsToRequestFinal.toTypedArray())
        }
    }

    private fun showPermissionDialog() {
        Toast.makeText(requireContext(), "모든 권한이 승인되었습니다.", Toast.LENGTH_SHORT).show()
        PermissionBottomSheet().show(parentFragmentManager, "PermissionBottomSheet")
        // 필요 시 추가 동작 수행
    }
}
