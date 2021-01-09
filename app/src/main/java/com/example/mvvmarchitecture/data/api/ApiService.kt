package com.example.mvvmarchitecture.data.api

import com.example.mvvmarchitecture.data.model.User
import io.reactivex.Single

interface ApiService {

    fun getUsers(): Single<List<User>>
}