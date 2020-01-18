package com.example.gitproject.view.baseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.gitproject.R
import com.example.gitproject.models.repository.ViewModelFactory
import com.example.gitproject.util.Constants
import com.example.gitproject.view.baseActivity.BaseActivity

abstract class BaseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    abstract fun getLayoutId(): Int
    abstract fun init()


    fun <T : ViewModel> getViewModelInstance(java: Class<T>): T {
        val factory = ViewModelFactory.getInstance(activity!!.application)
        return ViewModelProviders.of(this, factory).get(java)
    }

    public fun addFragment(fragment: Fragment, backstack: String) {
        (activity as BaseActivity).apply { addFragment(fragment, backstack) }
    }


    fun showToolbarBackButton() {
        Constants.TOOLBAR_BUTTON_CLICK = true
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp);
    }

    fun hideToolbarBackButton() {
        Constants.TOOLBAR_BUTTON_CLICK = false
        (activity as AppCompatActivity).supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_github);
    }

}