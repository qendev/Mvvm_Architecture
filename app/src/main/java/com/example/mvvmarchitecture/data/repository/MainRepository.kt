package com.example.mvvmarchitecture.data.repository

import com.example.mvvmarchitecture.data.api.ApiHelper
import com.example.mvvmarchitecture.data.model.User
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {

    fun getUsers(): Single<List<User>> {
        return apiHelper.getUsers()
    }
}