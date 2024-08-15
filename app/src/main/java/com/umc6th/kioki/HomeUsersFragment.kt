package com.umc6th.kioki

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.umc6th.kioki.databinding.FragmentHomeUsersBinding

class HomeUsersFragment : Fragment() {
    private lateinit var binding : FragmentHomeUsersBinding
    private lateinit var groups: List<MemberEntity>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHomeUsersBinding.inflate(layoutInflater) // fragment_home_users.xml과 연결
//        val recyclerView = binding.homeRecyclerview
//        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.adapter = HomeUsersAdapter(users)

        val choice =arguments?.getInt(MENUCATEGORY_ITEM)
        val recyclerview = binding.homeRecyclerview
        recyclerview.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        choice?.let {
            when(choice){
                0 -> recyclerview.adapter = HomeUsersAdapter(MemberLists.groups)
                1 -> recyclerview.adapter = HomeUsersAdapter(MemberLists.groups)
                else -> recyclerview.adapter = HomeUsersAdapter(MemberLists.groups)

            }
        }
        return binding.root
    }



    companion object {
        val MENUCATEGORY_ITEM = "menucateogry_item"
        @JvmStatic // 자바의 정적메소드처럼 사용함
        fun newInstance(position : Int) =
            HomeUsersFragment().apply {
                arguments = Bundle().apply {
                    putInt(MENUCATEGORY_ITEM, position)
                }
            }

//        private const val ARG_USER_LIST = "user_list"
//
//        @JvmStatic
//        fun newInstance(users: List<User>) = HomeUsersFragment().apply {
//            arguments = Bundle().apply {
//                putParcelableArrayList(ARG_USER_LIST, ArrayList(users))
//            }
//        }
    }
}