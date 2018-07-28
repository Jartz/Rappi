package jart.rappi.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by julianx1 on 7/26/18.
 */



class mVideo(
        @SerializedName("id") var id : String,
        @SerializedName("iso_639_1") var iso_639_1 : String,
        @SerializedName("iso_3166_1") var iso_3166_1 : String,
        @SerializedName("key") var key : String,
        @SerializedName("name") var name : String,
        @SerializedName("site") var site : String,
        @SerializedName("size") var size : Int= 0,
        @SerializedName("type") var type : String)
