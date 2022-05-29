package com.diagnaldemoapp.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GridItemMarginDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL,
    private val firstTopSpace: Int = 2
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = spaceSize
        outRect.right = spaceSize
        outRect.bottom = spaceSize

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) > 2) {
            outRect.top = spaceSize
        } else {
            if(firstTopSpace != -1) {
                outRect.top = 0
            }else{
                outRect.top = spaceSize
            }
        }
    }
   /* private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        outRect.left = spaceSize
        outRect.right = spaceSize
        outRect.bottom = spaceSize

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) > 1) {
            outRect.top = spaceSize
        } else {
            outRect.top = 0
        }
    }*/
}