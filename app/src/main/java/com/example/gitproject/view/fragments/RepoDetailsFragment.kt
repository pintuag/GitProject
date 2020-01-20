package com.example.gitproject.view.fragments

import com.example.gitproject.R
import com.example.gitproject.models.dataModel.TrendingListModel
import com.example.gitproject.util.ImageLoader
import com.example.gitproject.view.baseFragment.BaseFragment
import kotlinx.android.synthetic.main.repo_details_fragment.*

class RepoDetailsFragment : BaseFragment() {

    lateinit var imageLoader: ImageLoader

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

        imageLoader = ImageLoader(activity!!)
        username.text = trendingListModel.username
        repoName.text = trendingListModel.name

        imageLoader.DisplayImage(trendingListModel.avatar, R.drawable.placeholder, userProfile)
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