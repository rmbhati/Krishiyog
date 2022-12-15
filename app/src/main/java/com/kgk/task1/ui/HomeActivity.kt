package com.kgk.task1.ui

import androidx.databinding.DataBindingUtil
import com.kgk.task1.R
import com.kgk.task1.base.BaseActivity
import com.kgk.task1.databinding.ActivityMainBinding
import com.kgk.task1.utils.CustomProgress
import com.kgk.task1.utils.toast
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {
    private lateinit var activityBinding: ActivityMainBinding
    private val viewModel by viewModel<HomeViewModel>()
    private var dialog: CustomProgress? = null

    override fun initView() {
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activityBinding.viewmodel = viewModel
        viewModel.fetchingData.observe(this) { showLoading(it) }
        viewModel.errorData.observe(this) { onFailed(it) }
        viewModel.listData.observe(this) { onSuccess(it) }
    }

    override fun initData() {
        viewModel.getListData()
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            dialog?.show()
        } else {
            dialog?.cancel()
        }
    }

    private fun onSuccess(response: List<ListData>) {
        toast(response.toString())
    }

    private fun onFailed(error: String) {
        toast(error)
    }
}