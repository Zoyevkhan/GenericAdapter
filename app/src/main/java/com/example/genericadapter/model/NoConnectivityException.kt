package com.example.genericadapter.model

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String?
        get() = "Please Connect With Internet"

}