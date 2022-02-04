package com.shegor.samplefootballapp.base

import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerViewFragment<VM : ViewModel, B : ViewDataBinding, A : ListAdapter<*, *>> :
    BaseFragment<VM, B>() {

    var listAdapter: A? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        listAdapter = getRvAdapter()
        getRecyclerView().apply {
            layoutManager = getRvLayoutManager()
            adapter = listAdapter
        }
    }

    abstract fun getRvAdapter(): A
    abstract fun getRecyclerView(): RecyclerView
    abstract fun getRvLayoutManager(): RecyclerView.LayoutManager

    override fun onDestroyView() {
        super.onDestroyView()
        listAdapter = null
    }
}