package com.example.gitproject.view.baseFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.gitproject.models.repository.ViewModelFactory
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

    public fun addFragment(fragment: Fragment) {
        (activity as BaseActivity).apply { addFragment(fragment) }
    }

    public fun addFragment(fragment: Fragment, backstack: String) {
        (activity as BaseActivity).apply { addFragment(fragment, backstack) }
    }

    public fun replaceFragment(fragment: Fragment, backstack: String) {
        (activity as BaseActivity).apply { replaceFragment(fragment, backstack) }
    }

    public fun replaceFragment(fragment: Fragment) {
        (activity as BaseActivity).apply { replaceFragment(fragment) }
    }


}