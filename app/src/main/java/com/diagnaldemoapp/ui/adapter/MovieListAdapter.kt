package com.diagnaldemoapp.ui.adapter

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.diagnaldemoapp.R
import com.diagnaldemoapp.common.loadStrImage
import com.diagnaldemoapp.common.movieNameSpannable
import com.diagnaldemoapp.databinding.RowMovieListBinding
import com.diagnaldemoapp.ui.ContentItem
import com.diagnaldemoapp.utils.DeviceUtil

class MovieListAdapter: RecyclerView.Adapter<MovieListAdapter.ItemViewHolder>() {

    var searchStr = ""
    var filterList: List<ContentItem?> ?= null
    var list: List<ContentItem?> ?= null
    set(value) {
        field = value
        filterItems("")
    }

    class ItemViewHolder(itemViewBinding: RowMovieListBinding): RecyclerView.ViewHolder(itemViewBinding.root){
        val binding = itemViewBinding
        
        fun bind(item: ContentItem?, searchStr:String){
//            binding.movieNameTv.text = item?.name
            binding.movieNameTv.setText(binding.movieNameTv.context.movieNameSpannable(item?.name , searchStr))
            binding.movieIv.loadStrImage(item?.posterImage!!, R.mipmap.placeholder_for_missing_posters)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(RowMovieListBinding.inflate(LayoutInflater.from(parent.context),
                parent , false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = filterList?.get(position)
        holder.bind(item, searchStr)
    }

    override fun getItemCount(): Int {
        return filterList?.size ?: 0
    }

    fun filterItems(search: String) {
        searchStr = search
        if (!TextUtils.isEmpty(search)) {
            applyFilter(search)
        } else {
            this.filterList = list
        }
        notifyDataSetChanged()
    }

    private fun applyFilter(search: String) {
        val filterList = mutableListOf<ContentItem>()
        list?.let {
            val text = search.lowercase()
            for (item in it) {
                if (item?.name?.lowercase()?.contains(text) == true) {
                    filterList.add(item)
                }
            }
        }
        this.filterList = filterList
    }
}