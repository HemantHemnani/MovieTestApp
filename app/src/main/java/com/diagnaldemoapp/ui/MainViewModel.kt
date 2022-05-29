package com.diagnaldemoapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.diagnaldemoapp.common.getJsonDataFromAsset
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class MainViewModel @Inject constructor(app: Application) : ViewModel() {

    val TAG = MainViewModel::class.java.simpleName
    private val context = app.applicationContext

    private val _response: MutableLiveData<Response> = MutableLiveData<Response>()
    val response: LiveData<Response?> = _response
    private val _listResponse: MutableLiveData<List<ContentItem?>> = MutableLiveData<List<ContentItem?>>()
    val listResponse: LiveData<List<ContentItem?>?> = _listResponse

    var pageNumber = 1

    fun getFirstPage(){
        pageNumber = 1
        getData()
    }

    fun getNextPage(){
        pageNumber += 1
        getData()
    }

    fun getData(){
        val listCountryType = object : TypeToken<Response>() {}.type
        val dataFromAssets = getJsonDataFromAsset(context, "page$pageNumber.json")
        if(dataFromAssets.isNullOrEmpty())
        {
            return
        }
        val responseValue: Response = Gson().fromJson(dataFromAssets, listCountryType)
        if(pageNumber == 1) {
            _response.value = responseValue
            _listResponse.value = responseValue.page?.contentItems?.content!!
        }else {
            val existing = _response.value
            val existing1 = _listResponse.value

            if (responseValue != null && existing != null) {
                _listResponse.value = existing1?.plus(responseValue.page?.contentItems?.content!!)
                _response.value = existing!!
            }
        }
    }
}