package jart.rappi.Util

import android.content.ContentValues
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import jart.rappi.Adapter.MovieAdapter
import jart.rappi.Adapter.SearchAdapter
import jart.rappi.Adapter.TvSeriesAdapter
import jart.rappi.Interface.ApiService
import jart.rappi.Model.mMoviesCategory
import jart.rappi.Model.mTvSeriesCategory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by julianx1 on 7/28/18.
 */
class Cartelera {

    companion object Factory {

        fun setListFilter(query: String, reciclerView: RecyclerView, context: Context, progress: ProgressBar) {

            var httpClient = CacheSetting.getSetting(context)


            val retrofit = Retrofit.Builder()
                    .baseUrl(Api.base_api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()

            val apiService = retrofit.create(ApiService::class.java)
            apiService.searchMultiple(query, Api.api_key, Api.language, Api.page, "false")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<Response<mMoviesCategory>>() {
                        override fun onNext(response: Response<mMoviesCategory>) {

                            Log.i(ContentValues.TAG, "------>>" + response.body()!!.results.toString());
                            val movies = response.body()!!.results
                            val adapter = SearchAdapter(movies)
                            reciclerView.adapter = adapter
                            progress.setVisibility(View.GONE)


                        }

                        override fun onComplete() {}

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })
        }

        fun setListByMediType(typeApi:String,categoryApi: String,reciclerView: RecyclerView,context: Context,progress:ProgressBar) {


            var httpClient = CacheSetting.getSetting(context)


            val retrofit = Retrofit.Builder()
                    .baseUrl(Api.base_api)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient)
                    .build()

            if(typeApi.equals("movie")){
                loadMovie(categoryApi,retrofit,reciclerView,progress)
            }
            else { loadTv(categoryApi,retrofit,reciclerView,progress) }
        }


        fun loadMovie(categoryApi: String,retrofit:Retrofit,reciclerView: RecyclerView,progress:ProgressBar){

            val apiService = retrofit.create(ApiService::class.java)
            apiService.getAllMovie("movie", categoryApi, Api.api_key, Api.language, Api.page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<Response<mMoviesCategory>>() {
                        override fun onNext(response: Response<mMoviesCategory>) {

                            val movies = response.body()!!.results
                            Log.i(ContentValues.TAG, "" + movies.size)

                            val adapter = MovieAdapter(movies)
                            reciclerView.adapter = adapter
                            progress.setVisibility(View.GONE)

                        }

                        override fun onComplete() {}

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })

        }

        fun loadTv(categoryApi: String,retrofit:Retrofit,reciclerView: RecyclerView,progress:ProgressBar){
            val apiService = retrofit.create(ApiService::class.java)
            apiService.getAllTvSeries("tv", categoryApi, Api.api_key, Api.language, Api.page)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(object : DisposableObserver<Response<mTvSeriesCategory>>() {
                        override fun onNext(response: Response<mTvSeriesCategory>) {

                            val tvSeries = response.body()!!.results
                            Log.i(ContentValues.TAG, "" + tvSeries.size)

                            val adapter = TvSeriesAdapter(tvSeries)
                            reciclerView.adapter = adapter
                            progress.setVisibility(View.GONE)

                        }

                        override fun onComplete() {}

                        override fun onError(e: Throwable) {
                            e.printStackTrace()
                        }
                    })
        }

    }
}


