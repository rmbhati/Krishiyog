package com.kgk.task1.ui

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kgk.task1.R
import com.kgk.task1.base.BaseActivity
import com.kgk.task1.databinding.ActivityMainBinding
import com.kgk.task1.utils.CustomProgress
import com.kgk.task1.utils.PrefKeys
import com.kgk.task1.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import java.lang.reflect.Type;
import kotlin.collections.ArrayList

class HomeActivity : BaseActivity() {
    private lateinit var activityBinding: ActivityMainBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val sdf = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
    private var dialog: CustomProgress? = null

    override fun initView() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityBinding.viewmodel = viewModel
        dialog = CustomProgress.createDialog(this, "Loading")
        viewModel.fetchingData.observe(this) { showLoading(it) }
        viewModel.errorData.observe(this) { onFailed(it) }
        viewModel.listData.observe(this) { onSuccess(it) }
    }

    override fun initData() {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val did = DividerItemDecoration(recyclerView.context, layoutManager.orientation)
        recyclerView.addItemDecoration(did)

        if (sharedPreferences.getString(PrefKeys.datetime, "") == "") {
            viewModel.getListData(true)//if date and time is not saved in cashing
        } else {
            //date and time saved in cashing
            val currentDate = sdf.parse(sdf.format(Date()))
            val savedDate = sdf.parse(sharedPreferences.getString(PrefKeys.datetime, ""))
            val diff: Long = currentDate.time - savedDate.time
            val seconds = diff / 1000
            val minutes = seconds / 60
            val hours = minutes / 60
            if (minutes > 120) {
                viewModel.getListData(true)
            } else {
                val listType: Type = object : TypeToken<ArrayList<ListData?>?>() {}.type
                val response: List<ListData> =
                    Gson().fromJson(sharedPreferences.getString(PrefKeys.listData, ""), listType)
                setAdapter(response)
            }
        }
        refresh.setOnRefreshListener { viewModel.getListData(false) }
        retry.setOnClickListener { viewModel.getListData(true) }
    }

    private fun setAdapter(response: List<ListData>) {
        val adapter = HomeAdapter(this, response)
        recyclerView.adapter = adapter
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            dialog?.show()
        } else {
            dialog?.cancel()
        }
    }

    private fun onSuccess(response: List<ListData>) {
        retry.visibility = View.GONE
        setAdapter(response)
        refresh.isRefreshing = false
        val currentDate = sdf.format(Date())
        sharedPreferences.edit().putString(PrefKeys.listData, Gson().toJson(response)).apply()
        sharedPreferences.edit().putString(PrefKeys.datetime, currentDate).apply()
    }

    private fun onFailed(error: String) {
        retry.visibility = View.VISIBLE
        recyclerView.adapter = null
        refresh.isRefreshing = false
    }
}