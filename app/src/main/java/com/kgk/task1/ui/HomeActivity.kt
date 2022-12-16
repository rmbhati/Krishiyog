package com.kgk.task1.ui

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.kgk.task1.R
import com.kgk.task1.base.BaseActivity
import com.kgk.task1.databinding.ActivityMainBinding
import com.kgk.task1.utils.CustomProgress
import com.kgk.task1.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeActivity : BaseActivity() {
    private lateinit var activityBinding: ActivityMainBinding
    private val viewModel by viewModel<HomeViewModel>()
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
        viewModel.getListData(true)
        refresh.setOnRefreshListener { viewModel.getListData(false) }
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            dialog?.show()
        } else {
            dialog?.cancel()
        }
    }

    private fun onSuccess(response: List<ListData>) {
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val dividerItemDecoration =
            DividerItemDecoration(recyclerView.context, layoutManager.getOrientation())
        recyclerView.addItemDecoration(dividerItemDecoration)
        val adapter = HomeAdapter(this, response)
        recyclerView.adapter = adapter
        refresh.isRefreshing = false
    }

    private fun onFailed(error: String) {
        toast(error)
    }
}