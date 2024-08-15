package com.umc6th.kioki

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView

class MainExpandableListAdapter (
    private val context: Context,
    private val parents: MutableList<String>,
    private val childList: MutableList<MutableList<String>>
) : BaseExpandableListAdapter() {
    override fun getGroupCount() = parents.size

    override fun getChildrenCount(p0: Int) = childList[p0].size

    override fun getGroup(parent: Int) = parents[parent]

    override fun getChild(parent: Int, child: Int): String = childList[parent][child]

    override fun getGroupId(parent: Int) = parent.toLong()

    override fun getChildId(parent: Int, child: Int) = child.toLong()

    override fun hasStableIds() = false

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true

    // 부모 메뉴 설정
    override fun getGroupView(
        parent: Int,
        isExpanded: Boolean,
        convertView: View?,
        parentview: ViewGroup
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val parentView = convertView ?: inflater.inflate(R.layout.menu_parent_main, parentview, false)
        val parentText = parentView.findViewById<TextView>(R.id.main_nav_list_title_tv)
        parentText.text = parents[parent]

//        setArrow(parent, parentView, isExpanded)
        for(i in 0..3) {
            Log.d("자식확인", getGroupId(i).toString())
            if(getGroupId(i).toInt() == 2) {
                parentview.findViewById<ImageView>(R.id.main_nav_drop_down_iv)?.visibility = View.VISIBLE
            }
        }



        // 자식이 있는 부모 메뉴만 옆에 드롭다운 이미지가 보이도록 설정

        return parentView
    }

    /* 자식 계층 레이아웃 설정 */
    override fun getChildView(
        parent: Int,
        child: Int,
        isLastChild: Boolean,
        convertView: View?,
        parentview: ViewGroup
    ): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val childView = inflater.inflate(R.layout.menu_child_main, parentview, false)

        childView.findViewById<TextView>(R.id.main_child_title_tv).text = getChild(parent,child)
        // childView.mainChild.text = getChild(parent, child)

        return childView
    }


}