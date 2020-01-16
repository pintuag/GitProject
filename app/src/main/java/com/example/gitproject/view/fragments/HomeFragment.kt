package com.example.gitproject.view.fragments

import android.view.View
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.gitproject.R
import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.view.adapters.TrendingListAdapter
import com.example.gitproject.view.baseFragment.BaseFragment
import com.example.gitproject.viewModels.TrendingListViewModel
import kotlinx.android.synthetic.main.home_fragment.*
import com.example.gitproject.models.httpService.Result
import com.example.gitproject.util.toast
import kotlinx.android.synthetic.main.no_record_found.*
import kotlinx.android.synthetic.main.progress_bar.*

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var trendingListViewModel: TrendingListViewModel
    lateinit var itemClickListener: TrendingListAdapter.ItemClickListener

    override fun onRefresh() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    val trendingListAdapter = TrendingListAdapter()

    override fun getLayoutId() = R.layout.home_fragment

    override fun init() {

        setRecyclerView()
        setSwipeRefreshLayout()
        initViewModels()
        callGitHubListApi()
    }


    private fun setRecyclerView() {

        trendingListRecyclerView.adapter = trendingListAdapter

        itemClickListener = object : TrendingListAdapter.ItemClickListener {
            override fun itemClick(position: Int) {

            }
        }

    }

    private fun setSwipeRefreshLayout() {

        swipeRefresh.setOnRefreshListener(this)

    }


    private fun initViewModels() {

        trendingListViewModel = getViewModelInstance(TrendingListViewModel::class.java)
        trendingListViewModel.trendingListLiveData.observe(this,
            Observer<Result<List<TrendingListModel>>> {
                when (it) {
                    is Result.Loading -> {
                        showProgressBar()
                    }

                    is Result.Success -> {
                        hideProgressBar()
                        if (!it.data.isNullOrEmpty()) {
                            setAdapterValues(it.data)
                            viewVisibility(true)
                        } else {
                            viewVisibility(false)
                        }
                    }
                    is Result.Error -> {
                        hideProgressBar()
                        activity!!.toast(it.exception.message.toString())
                    }
                }
            })

    }

    private fun setAdapterValues(data: List<TrendingListModel>) {
        trendingListAdapter.setTrendingListData(activity!!, data, itemClickListener)
    }


    private fun callGitHubListApi() {
        trendingListViewModel.trendingListApiCall()
    }


    fun showProgressBar() {
        circleProgressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        circleProgressBar.visibility = View.GONE
    }

    fun viewVisibility(visible: Boolean) {
        if (visible) {
            trendingListRecyclerView.visibility = View.VISIBLE
            noRecordsFound.visibility = View.GONE
        } else {
            trendingListRecyclerView.visibility = View.GONE
            noRecordsFound.visibility = View.VISIBLE
        }
    }


}