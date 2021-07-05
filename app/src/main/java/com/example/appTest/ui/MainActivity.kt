package com.example.appTest.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.appTest.R
import com.example.appTest.databinding.ActivityMainBinding
import com.example.appTest.entity.Item
import com.example.appTest.util.BUNDLE_KEY
import com.example.appTest.util.ITEM_ENTITY_KEY
import com.example.appTest.util.api.Resource
import com.example.appTest.util.progressDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@SuppressLint("LogNotTimber")
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    private var progressDialog: AlertDialog? = null
    private var listItem = ArrayList<Item>()
    private val page = 1
    private val itemAdapter = AdapterItemHome(onItemClick = {
        val intent = Intent(this, DetailActivity::class.java)
        val bundle = Bundle()
        bundle.putSerializable(ITEM_ENTITY_KEY, it)
        intent.putExtra(BUNDLE_KEY, bundle)
        startActivity(intent)
    })
//    private var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//    private var layoutManager = LayoutManager


    //    private var layoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel


        itemAdapter.submitList(listItem)
        binding.recyclerViewHome.adapter = itemAdapter
        binding.recyclerViewHome.layoutManager
        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    WorkoutInstance.getInstance().setPage(page + 1)
                    processAlbums()

//                    binding.recyclerViewHome.adapter = itemAdapter
//                    binding.recyclerViewHome.layoutManager
                    Log.d("BBBB", "ItemAdapter size : ${itemAdapter.currentList.size}")
                    // LOAD MORE
                }
            }
        })
        processAlbums()
    }

    private fun processAlbums() {
        progressDialog = progressDialog(this, getString(R.string.logging_msg))

        viewModel.getAlbums().observe(this) { resource ->

            when (resource) {
                is Resource.Loading -> {
                    progressDialog?.show()
                    Log.d("TAG_MAIN", "Resource.Loading: ")
                }
                is Resource.Error -> {
                    progressDialog?.dismiss()
                    Log.d("TAG_MAIN", "Resource.Error resource.code: ${resource.code} ")
                }
                is Resource.Success -> {
                    val list: List<Item> = resource.data
                    listItem.addAll(list)

                    itemAdapter.submitList(listItem)
                    itemAdapter.notifyDataSetChanged()

                    progressDialog?.dismiss()
                    Log.d(
                        "TAG_MAIN",
                        "Resource.Success resource.data: ${resource.data} "
                    )
                }
            }
        }
    }
}