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
//    private var layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
//    private var layoutManager = LayoutManager


    //    private var layoutManager = LinearLayoutManager(this)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel

        val itemAdapter = AdapterItemHome(onItemClick = {
            val intent = Intent(this, DetailActivity::class.java)
            val bundle = Bundle()
            bundle.putSerializable(ITEM_ENTITY_KEY, it)
            intent.putExtra(BUNDLE_KEY, bundle)
            startActivity(intent)
        })

        binding.recyclerViewHome.adapter = itemAdapter
        binding.recyclerViewHome.layoutManager
        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!recyclerView.canScrollVertically(1)) {
                    WorkoutInstance.getInstance().setPage(page + 1)
                    processAlbums(itemAdapter)
                    binding.recyclerViewHome.adapter = itemAdapter
                    binding.recyclerViewHome.layoutManager
                    Log.d("BBBB", "ItemAdapter size : ${itemAdapter.currentList.size}")
                    // LOAD MORE
                }
            }
        })

//        loadNextPage(itemAdapter)
        processAlbums(itemAdapter)
//        init()
//        observe(itemAdapter)
    }

//    private fun observe(itemAdapterItemHome: AdapterItemHome) {
//        itemAdapterItemHome.submitList(listItem)
//    }

//    private fun init() {
//        processAlbums(itemAdapterItemHome)
//    }
//    fun isLastVisible(): Boolean {
//        val layoutManager = binding.recyclerViewHome.layoutManager as LinearLayoutManager
//        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
//        val numItems: Int = binding.recyclerViewHome.adapter!!.itemCount
//        return pos >= numItems - 1
//    }

//    private fun loadNextPage(itemAdapterItemHome: AdapterItemHome) {
//        binding.recyclerViewHome.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                val page = 1
//                if (dy > 0) {
//                    if (isLastVisible()) {
//                        WorkoutInstance.getInstance().setPage(page + 1)
//                        val a = WorkoutInstance.getInstance().getPage()
//                        processAlbums(itemAdapterItemHome)
//                        Log.d("BBBB","Page $a")
//
//                        Log.d("BBB","Lis Size ${listItem.size}")
//                        Log.d("BBB","Lis SIze ${itemAdapterItemHome.currentList.size}")
//                    }
//                }
//                super.onScrolled(recyclerView, dx, dy)
//            }
//        })
//    }

    private fun processAlbums(itemAdapterItemHome: AdapterItemHome) {
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
                    Log.d("aaa", "List Item = $list")
                    Log.d("aaa", "List Item 1 = ${listItem.size}")
                    listItem.addAll(list)
                    itemAdapterItemHome.submitList(listItem)
                    progressDialog?.dismiss()

                    Log.d("aaa", "List Item 1  = $list")
                    Log.d("aaa", "List Item 1 1  = ${listItem.size}")
                    Log.d(
                        "TAG_MAIN",
                        "Resource.Success resource.data: ${resource.data} "
                    )
                }
            }
        }
    }
}