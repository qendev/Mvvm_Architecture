package com.example.mvvmarchitecture.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmarchitecture.R
import com.example.mvvmarchitecture.data.api.ApiHelper
import com.example.mvvmarchitecture.data.api.ApiServiceImpl
import com.example.mvvmarchitecture.data.model.User
import com.example.mvvmarchitecture.main.adapter.MainAdapter
import com.example.mvvmarchitecture.ui.base.ViewModelFactory
import com.example.mvvmarchitecture.ui.main.viewmodel.MainViewModel
import com.example.mvvmarchitecture.utils.Status
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        myrecyclerview.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        myrecyclerview.addItemDecoration(
                DividerItemDecoration(
                        myrecyclerview.context,
                        (myrecyclerview.layoutManager as LinearLayoutManager).orientation
                )
        )
        myrecyclerview.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getUsers().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    myrecyclerview.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    myrecyclerview.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}