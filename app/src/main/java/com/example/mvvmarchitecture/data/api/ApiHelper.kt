package com.example.mvvmarchitecture.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getUsers() = apiService.getUsers()
}