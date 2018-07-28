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
    fun getAllPosts(@Path("typeApi") typeApi: String,
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

    @GET("{type}/{keyVideo}/videos?api_key=13115ee8951044bf35230ce959846751&language=en-US")
    fun getVideo(@Path("keyVideo") keyVideo: Int,@Path("type") type: String):Observable<Response<mVideoCAteogry>>

    /*
     =================================
     API SERVICE CATEGO
     =================================
   */

    @GET("{type}/{category}?api_key=13115ee8951044bf35230ce959846751&language=en-US")
    fun getCategorys(@Path("type") type: String,@Path("category") category: String):Observable<Response<mMoviesCategory>>

    @GET("{type}/{category}?api_key=13115ee8951044bf35230ce959846751&language=en-US")
    fun getCategorysTV(@Path("type") type: String,@Path("category") category: String):Observable<Response<mTvSeriesCategory>>


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