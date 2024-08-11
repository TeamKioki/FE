package com.umc6th.myapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
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
        val parentView = inflater.inflate(R.layout.menu_parent_main, parentview, false)
        parentView.findViewById<TextView>(R.id.main_nav_list_title_tv).text = parents[parent]

//        setArrow(parent, parentView, isExpanded)

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