package com.example.appTest.ui

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.appTest.R
import com.example.appTest.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint

class DetailActivity : BaseActivity<ActivityDetailBinding, DetailViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.viewModel = this.viewModel

        setSupportActionBar(materialToolbarDetail)
        init()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun init() {
        viewModel.processIntentData(intent)
    }
}