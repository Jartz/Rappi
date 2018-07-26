package jart.rappi.Interface


import io.reactivex.Observable
import jart.rappi.Model.mMovie
import jart.rappi.Model.mMoviesCategory
import jart.rappi.Model.mTvSeriesCategory
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface ApiService {

    /*
    =================================
    API SERVICE MOVIE
    =================================
   */
    @GET("popular?api_key=13115ee8951044bf35230ce959846751&language=en-US&page=1")
    fun getAllPosts():Observable<Response<mMoviesCategory>>

    //@GET("/users/{username}")
    //fun getGithubAccountObservable(@Path("username") username: String): Observable<Response<GithubAccount>>


    @GET("posts/{id}")
    fun getPostById(@Path("id") id: Int): Call<mMoviesCategory>

    @POST("posts/{id}")
    fun editPostById(@Path("id") id: Int, @Body post: mMovie?): Call<mMoviesCategory>

    /*
    =================================
    API SERVICE TVSERIES
    =================================
     */
    @GET("popular?api_key=13115ee8951044bf35230ce959846751&language=en-US&page=1")
    fun getAllTvSeries():Observable<Response<mTvSeriesCategory>>

    }
