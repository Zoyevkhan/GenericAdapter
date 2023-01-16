package com.example.genericadapter

object BaseApiClient {
    fun <T> processResponse(
        response:retrofit2.Response<T>,
        taskComplete: (Boolean,String,T?) -> Unit
    ){
        if(response.isSuccessful){
            response.body()?.let { responseModel ->
                taskComplete.invoke(true,"Loaded",responseModel)
            }?: kotlin.run {
                taskComplete.invoke(false,response.message(),response.body())
            }
        }else{
            taskComplete.invoke(false,response.message(),null)
        }
    }
}