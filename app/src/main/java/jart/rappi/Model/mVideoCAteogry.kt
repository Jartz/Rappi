package jart.rappi.Model

import com.google.gson.annotations.SerializedName

/**
 * Created by julianx1 on 7/26/18.
 */


class mVideoCAteogry(
        @SerializedName("id") var id : Int = 0,
        @SerializedName("results") var results : List<mVideo>)