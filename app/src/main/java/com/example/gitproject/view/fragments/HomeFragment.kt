package com.example.gitproject.view.fragments

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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
import kotlin.collections.HashMap

class HomeFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var trendingListViewModel: TrendingListViewModel
    lateinit var itemClickListener: TrendingListAdapter.ItemClickListener


    val trendingListAdapter = TrendingListAdapter()
    var selectedPosition = 0

    override fun getLayoutId() = R.layout.home_fragment

    override fun init() {

        setRecyclerView()
        setSwipeRefreshLayout()
        initViewModels()
        setSpinnerLayouts()
        callGitHubListApi()
    }

    private fun setSpinnerLayouts() {

        val listOfLanguages = listOf<String>(*resources.getStringArray(R.array.languages))
        val insuranceAdapter =
            ArrayAdapter<String>(activity!!, R.layout.spinner_text_view_layout, listOfLanguages)
        languageSpinner.adapter = insuranceAdapter
        languageSpinner.setSelection(selectedPosition)
        languageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                if (position != selectedPosition) {
                    selectedPosition = position
                    callGitHubListApi()
                }

            }
        }
    }


    private fun setRecyclerView() {

        trendingListRecyclerView.adapter = trendingListAdapter

        itemClickListener = object : TrendingListAdapter.ItemClickListener {
            override fun itemClick(trendingListModel: TrendingListModel) {
                RepoDetailsFragment.getRepoDetailsInstance(trendingListModel)
                addFragment(RepoDetailsFragment(), HomeFragment::class.java.name)
            }
        }

    }

    private fun setSwipeRefreshLayout() {

        swipeRefresh.setOnRefreshListener(this)

    }

    override fun onRefresh() {
        callGitHubListApi()
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
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

        val params = HashMap<String, String>()
        params.apply {
            put("language", languageSpinner.selectedItem.toString())
            put("since", "weekly")
        }
        trendingListViewModel.trendingListApiCall(params)
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