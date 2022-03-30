package com.godaMeal.meals.data.exception

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.IOException

class APIException(
    @Expose var code: Int?,
    @SerializedName("message") var errorMessage: String
) : IOException()