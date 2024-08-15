package com.umc6th.kioki.join

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.join.adapter.PermissionAdapter
import com.umc6th.myapplication.R
import com.umc6th.myapplication.databinding.FragmentRequirePermissionBinding

class RequirePermissionFragment : Fragment() {

    private lateinit var binding: FragmentRequirePermissionBinding
    private val permissionAdapter: PermissionAdapter by lazy { PermissionAdapter() }
    private val permissions = listOf(
        PermissionItem(
            R.drawable.location, "위치", "(필수)",
            "내 위치와 주변의 키오스크를 찾기 위해 필요해요"
        ),
        PermissionItem(R.drawable.camera, "카메라", "(필수)", "앱 내의 오류와 질문, 건의사항을 위한 사진을\n제출하기 위해 필요해요"),
        PermissionItem(R.drawable.phonebook, "주소록", "(필수)", "앱 내의 오류와 질문, 건의사항을 위한 사진을\n제출하기 위해 필요해요"),
        PermissionItem(R.drawable.imagelibrary, "사진 라이브러리", "(선택)", "앱 내의 오류와 질문, 건의사항을 위한 사진을\n제출하기 위해 필요해요"),
        PermissionItem(R.drawable.notification, "알림", "(선택)", "이벤트 및 다양한 혜택 등 필요한 알림을 보내줘요")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRequirePermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.permissionListView.apply {
            adapter = permissionAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        setPermissions()

        binding.nextButton.setOnClickListener {
            PermissionBottomSheet().show(parentFragmentManager, "PermissionBottomSheet")
        }
    }

    private fun showPermissionDialog() {

    }

    private fun setPermissions() {
        permissionAdapter.setList(permissions)
    }
}