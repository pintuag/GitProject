package com.example.gitproject.view.fragments

import com.bumptech.glide.Glide
import com.example.gitproject.R
import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.view.baseFragment.BaseFragment
import kotlinx.android.synthetic.main.repo_details_fragment.*

class RepoDetailsFragment : BaseFragment() {


    companion object {
        lateinit var trendingListModel: TrendingListModel
        fun getRepoDetailsInstance(trendingListModel: TrendingListModel) {
            this.trendingListModel = trendingListModel
        }
    }

    override fun getLayoutId() = R.layout.repo_details_fragment

    override fun init() {

        setRepoDetails()

    }

    private fun setRepoDetails() {

        username.text = trendingListModel.username
        repoName.text = trendingListModel.name
        Glide.with(activity!!)
            .load(trendingListModel.avatar)
            .thumbnail(0.8f)
            .into(userProfile)

        userProfileUrl.text = trendingListModel.url
        projectName.text = trendingListModel.repo.name
        projectDetails.text = trendingListModel.repo.description
        repoUrl.text = trendingListModel.repo.url

    }

    override fun onResume() {
        super.onResume()
        showToolbarBackButton()
    }

    override fun onPause() {
        super.onPause()
        hideToolbarBackButton()
    }

}