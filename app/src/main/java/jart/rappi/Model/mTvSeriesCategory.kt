package jart.rappi.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by julianx1 on 7/26/18.
 */
class mTvSeriesCategory(
        @SerializedName("page") var page : Int = 0,
        @SerializedName("total_results") var total_results : Int = 0,
        @SerializedName("total_pages") var total_pages : Int = 0,
        @SerializedName("results") var results : ArrayList<mTvSeries> )