package com.example.genericadapter.model

data class LoginResponse(
    var status: Boolean? = null,
    var message: String? = null,
    var data: ArrayList<String> = arrayListOf(),
    var isIosPrice: Int? = null,
    var error: ArrayList<String> = arrayListOf()

)

