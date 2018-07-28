package jart.rappi.Interface


import io.reactivex.Observable
import jart.rappi.Model.mMovie
import jart.rappi.Model.mMoviesCategory
import jart.rappi.Model.mTvSeriesCategory
import jart.rappi.Model.mVideoCAteogry
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.GET




interface ApiService {

    /*
    =================================
    API SERVICE MOVIE
    =================================
   */
    @GET("{typeApi}/{typeCategoryApi}")
    fun getAllMovie(@Path("typeApi") typeApi: String,
                    @Path("typeCategoryApi") typeCategoryApi: String,
                    @Query("api_key") api_key: String,
                    @Query("language") language: String,
                    @Query("page") page: String):Observable<Response<mMoviesCategory>>

    /*
    =================================
    API SERVICE TVSERIES
    =================================
     */

    @GET("{typeApi}/{typeCategoryApi}")
    fun getAllTvSeries(@Path("typeApi") typeApi: String,
                       @Path("typeCategoryApi") typeCategoryApi: String,
                       @Query("api_key") api_key: String,
                       @Query("language") language: String,
                       @Query("page") page: String):Observable<Response<mTvSeriesCategory>>

    /*
      =================================
      API SERVICE VIDEOS
      =================================
    */

    @GET("{type}/{keyVideo}/videos")
    fun getVideo(@Path("keyVideo") keyVideo: Int,
                 @Path("type") type: String,
                 @Query("api_key") api_key: String,
                 @Query("language")language: String):Observable<Response<mVideoCAteogry>>

    /*
     =================================
     API SERVICE CATEGORY
     =================================
   */

    @GET("{type}/{category}")
    fun getCategorysMovie(@Path("type") type: String,
                          @Path("category") category: String,
                          @Query("api_key") api_key: String,
                          @Query("language") language: String):Observable<Response<mMoviesCategory>>

    @GET("{type}/{category}")
    fun getCategorysTV(@Path("type") type: String,
                       @Path("category") category: String,
                       @Query("api_key") api_key: String,
                       @Query("language") language: String):Observable<Response<mTvSeriesCategory>>


    /*
     =================================
     API SERVICE SEARCH
     =================================
   */

    @GET("search/multi")
    fun searchMultiple(
            @Query("query") query: String,
            @Query("api_key") api_key: String,
            @Query("language") language: String,
            @Query("page") page: String,
            @Query("include_adult") include_adult: String
            ):Observable<Response<mMoviesCategory>>




}