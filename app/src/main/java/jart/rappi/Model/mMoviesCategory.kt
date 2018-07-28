package jart.rappi.Model

import com.google.gson.annotations.SerializedName


data class mMoviesCategory(
        @SerializedName("page") var page : Int = 0,
        @SerializedName("total_results") var total_results : Int = 0,
        @SerializedName("total_pages") var total_pages : Int = 0,
        @SerializedName("results") var results : ArrayList<mMovie> )




