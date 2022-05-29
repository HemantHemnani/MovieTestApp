package com.diagnaldemoapp.ui

import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import com.diagnaldemoapp.R
import com.diagnaldemoapp.databinding.ActivityMainBinding
import com.diagnaldemoapp.di.modules.injectViewModel
import com.diagnaldemoapp.ui.adapter.MovieListAdapter
import com.diagnaldemoapp.utils.GridItemMarginDecoration
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {
val TAG = MainActivity::class.java.simpleName
    lateinit var binding: ActivityMainBinding
    private val mHandler = Handler(Looper.myLooper()!!)
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: MainViewModel
    private var isSearchViewVisible : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        viewModel = injectViewModel(viewModelFactory)
        viewModel.getFirstPage()
        manageView()
        setUpRecycleAdapter()
        initObserver()
        initListeners()
        managePagination()
        manageSearchQuery()
    }

    private fun setUpRecycleAdapter(){
        binding.listRv.addItemDecoration(
            GridItemMarginDecoration(resources.getDimensionPixelSize(R.dimen.grid_distance))
        )

        val adapter = MovieListAdapter()
        binding.listRv.adapter = adapter
    }

    //manage and show the list
    private fun initObserver(){
        viewModel.response.observe(this) {
            setToolbar(it?.page?.title)
        }

        viewModel.listResponse.observe(this) {
            val adapter = binding.listRv.adapter as MovieListAdapter
            adapter.list = it
        }
    }

    private fun initListeners(){
        binding.toolbar.rightIb.setOnClickListener {
            isSearchViewVisible = !isSearchViewVisible
            manageView()
        }
    }

    //manage the view as per selection of view type
    private fun manageView(){
        if(!isSearchViewVisible){
            binding.toolbar.toolbarRl.visibility = View.VISIBLE
            binding.toolbar.searchview.visibility = View.GONE
        }else{
            binding.toolbar.toolbarRl.visibility = View.GONE
            binding.toolbar.searchview.visibility = View.VISIBLE
        }
    }

    //set the toolbar data
    private fun setToolbar(title: String?){
        binding.toolbar.titleTv.text = title
    }

    //manage pagination
    private fun managePagination(){
        binding.scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v: NestedScrollView, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
            if (v.getChildAt(v.childCount - 1) != null) {
                if (scrollY >= v.getChildAt(v.childCount - 1).measuredHeight -
                    v.measuredHeight && scrollY > oldScrollY) { //code to fetch more data for endless scrolling
                    viewModel.getNextPage()
                }
            }
        } as NestedScrollView.OnScrollChangeListener)

    }

    /*** manage search query ***/
    fun manageSearchQuery() {
        binding.toolbar.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query :String) :Boolean {
                return false
            }

            override fun onQueryTextChange(newText :String) :Boolean {
                mHandler.removeCallbacksAndMessages(null)
                mHandler.postDelayed({
                        val adapter = binding.listRv.adapter as MovieListAdapter
                        adapter.filterItems(newText.trim())
                }, 700)

                return true
            }
        })
    }

}