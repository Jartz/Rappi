package jart.rappi.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by julianx1 on 7/26/18.
 */
class mTvSeries (
        @SerializedName("original_name") var original_name : String,
        @SerializedName("genre_ids") var genre_ids : List<Int>,
        @SerializedName("name") var name : String,
        @SerializedName("popularity") var popularity : Double,
        @SerializedName("origin_country") var origin_country : List<String>,
        @SerializedName("vote_count") var vote_count : Int,
        @SerializedName("first_air_date") var first_air_date : String,
        @SerializedName("backdrop_path") var backdrop_path : String,
        @SerializedName("original_language") var original_language : String,
        @SerializedName("id") var id : Int,
        @SerializedName("vote_average") var vote_average : Double,
        @SerializedName("overview") var overview : String,
        @SerializedName("poster_path") var poster_path : String)