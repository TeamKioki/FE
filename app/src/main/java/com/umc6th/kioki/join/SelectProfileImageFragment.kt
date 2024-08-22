package com.umc6th.kioki.join

import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.umc6th.kioki.R
import com.umc6th.kioki.databinding.FragmentSelectProfileImageBinding
import com.umc6th.kioki.utils.ImageUploader
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.Url
import java.io.File

class SelectProfileImageFragment : Fragment() {

    private lateinit var binding: FragmentSelectProfileImageBinding
    private val viewModel: JoinViewModel by activityViewModels<JoinViewModel>()
    private var fileUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectProfileImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectCharacter()

        val pickImageContract =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
                Glide.with(requireContext())
                    .load(uri)
                    .into(binding.selectProfileImageButton)

                fileUri = uri
                val fileName = getFileNameFromUri(uri!!)
                viewModel.getPresignedUrl(fileName!!)
                Log.d("SelectProfileImage", "onViewCreated: $fileName")
            }

        binding.selectProfileImageButton.setOnClickListener {
            pickImageContract.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_selectProfileImageFragment_to_inputMemberIntroduceFragment)

        }

        observePresignedUrl()
    }

    private fun observePresignedUrl() {
        viewModel.presignedUrl.observe(viewLifecycleOwner) { presignedUrl ->
            Log.d(TAG, "observePresignedUrl: $presignedUrl")
            if(fileUri == null || presignedUrl.isBlank()) return@observe
            // 파일을 MultipartBody.Part로 변환
            val contentResolver = requireContext().contentResolver
            val inputStream = contentResolver.openInputStream(fileUri!!)

            // 캐시 디렉토리에 임시 파일 생성
            val file = File(requireContext().cacheDir, "temp_image.jpg")
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            // 파일을 RequestBody로 변환
            val requestBody = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("file", file.name, requestBody)

            viewModel.uploadImageToS3(presignedUrl, body)
        }
    }

    private fun selectCharacter() {
        binding.apply {
            character1.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile1png)
            }

            character2.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile2)

            }

            character3.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile3)

            }

            character4.setOnClickListener {
                binding.selectProfileImageButton.setImageResource(R.drawable.profile4)

            }
        }
    }

    private fun getFileNameFromUri(uri: Uri): String? {
        var fileName: String? = null
        val contentResolver = context?.contentResolver

        val cursor = contentResolver?.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex >= 0) {
                    fileName = it.getString(columnIndex)
                }
            }
        }
        return fileName
    }

    companion object {
        private const val TAG = "SelectProfileImageFragment"
    }
}