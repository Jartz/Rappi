package jart.rappi.Fragment

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import jart.rappi.Adapter.MovieAdapter
import jart.rappi.Adapter.TvSeriesAdapter
import jart.rappi.Interface.ApiService
import jart.rappi.Model.mMovie
import jart.rappi.Model.mMoviesCategory
import jart.rappi.Model.mTvSeries
import jart.rappi.Model.mTvSeriesCategory

import jart.rappi.R
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {

    lateinit var cContext: Context

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    //=============================================================
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view = inflater!!.inflate(R.layout.fragment_home, container, false)
            if (container != null) {
                cContext = container.getContext()
            }

        val listMovie = ArrayList<mMovie>()
        val listTvSeries = ArrayList<mTvSeries>()
        val rvMovies = view.findViewById<RecyclerView>(R.id.rvMovie)
        val rvTvSeries = view.findViewById<RecyclerView>(R.id.rvTvSeries)

        rvMovies.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)
        loadMovie(rvMovies,listMovie,this.cContext)

        rvTvSeries.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)
        loadMovie(rvTvSeries,listMovie,this.cContext)
        loadTvSeries(rvTvSeries,listTvSeries,this.cContext)


        return view
    }

    //=============================================================

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }


    /*
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context
        } else {
           // throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }
    */


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): HomeFragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    fun loadMovie(reciclerView: RecyclerView,ListMovie: ArrayList<mMovie>,context: Context) {

        //https://api.themoviedb.org/3/movie/
        // Create a cache object
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        // create a network cache interceptor, setting the max age to 10 minute
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                    .maxAge(10, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }

        // Create the logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        // Create the httpClient, configure it
        // with cache, network cache interceptor and logging interceptor
        val httpClient = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()


        //https://api.github.com/
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        // Build the gitHubApi with Retrofit and do the network request
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getAllPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<mMoviesCategory>>() {
                    override fun onNext(response: Response<mMoviesCategory>) {

                        if (response.raw().cacheResponse() != null) {
                            Log.d("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.i(TAG, "response came from server")
                        }

                        //Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()
                        Log.i(TAG,"------>>"+response.body()!!.results.toString());
                        val movies = response.body()!!.results
                        Log.i(TAG,""+movies.size)

                        for (i in 0 until movies.size) {

                            try{
                                Log.i(TAG,""+movies.get(i).backdrop_path)
                                val p = movies.get(i)
                                ListMovie.add(mMovie(p.vote_count,p.id,p.video,p.vote_average,p.title,p.popularity,p.poster_path,p.original_language,p.original_title,p.genre_ids,p.backdrop_path,p.adult,p.overview, Date()))
                            }
                            catch (e: JSONException) {
                                e.printStackTrace();
                            }

                        }

                        val adapter = MovieAdapter(ListMovie)
                        reciclerView.adapter = adapter

                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }


    fun loadTvSeries(reciclerView: RecyclerView,ListMovie: ArrayList<mTvSeries>,context: Context) {

        //https://api.themoviedb.org/3/movie/
        // Create a cache object
        val cacheSize = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cache = Cache(httpCacheDirectory, cacheSize.toLong())

        // create a network cache interceptor, setting the max age to 10 minute
        val networkCacheInterceptor = Interceptor { chain ->
            val response = chain.proceed(chain.request())

            var cacheControl = CacheControl.Builder()
                    .maxAge(10, TimeUnit.MINUTES)
                    .build()

            response.newBuilder()
                    .header("Cache-Control", cacheControl.toString())
                    .build()
        }

        // Create the logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        // Create the httpClient, configure it
        // with cache, network cache interceptor and logging interceptor
        val httpClient = OkHttpClient.Builder()
                .cache(cache)
                .addNetworkInterceptor(networkCacheInterceptor)
                .addInterceptor(loggingInterceptor)
                .build()


        //https://api.github.com/
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/tv/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        // Build the gitHubApi with Retrofit and do the network request
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getAllTvSeries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<mTvSeriesCategory>>() {
                    override fun onNext(response: Response<mTvSeriesCategory>) {

                        if (response.raw().cacheResponse() != null) {
                            Log.d("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.i(TAG, "response came from server")
                        }

                        //Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()
                        Log.i(TAG,"------>>"+response.body()!!.results.toString());
                        val tvSeries = response.body()!!.results
                        Log.i(TAG,""+tvSeries.size)

                        for (i in 0 until tvSeries.size) {

                            try{
                                Log.i(TAG,""+tvSeries.get(i).backdrop_path)
                                val p = tvSeries.get(i)
                                ListMovie.add(mTvSeries(p.original_name,p.genre_ids,p.name,p.popularity,p.origin_country,p.vote_count,p.first_air_date,p.backdrop_path,p.original_language,p.id,p.vote_average,p.overview,p.poster_path))
                            }
                            catch (e: JSONException) {
                                e.printStackTrace();
                            }

                        }

                        val adapter = TvSeriesAdapter(ListMovie)
                        reciclerView.adapter = adapter

                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }





}// Required empty public constructor
