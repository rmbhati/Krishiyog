package com.kgk.task1.ui

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.kgk.task1.base.BaseViewModel
import com.kgk.task1.network.APIEndPoints
import com.kgk.task1.network.APIMethods
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val api: APIMethods) : BaseViewModel() {
    var fetchingData: MutableLiveData<Boolean> = MutableLiveData()
    var listData: MutableLiveData<List<ListData>> = MutableLiveData()
    var errorData: MutableLiveData<String> = MutableLiveData()

    @SuppressLint("CheckResult")
    fun getListData(b: Boolean) {
        if(b){
            emitListData(true)
        }
        val url = APIEndPoints.LIVE_URL
        api.getData(url)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                emitListData(false, it.body())
            }, { emitListData(false, error = it.message) })
    }

    private fun emitListData(
        showProgress: Boolean? = null, response: List<ListData>? = null, error: String? = null,
    ) {
        fetchingData.postValue(showProgress)
        if (response != null) listData.postValue(response)
        if (error != null) errorData.postValue(error)
    }
}
