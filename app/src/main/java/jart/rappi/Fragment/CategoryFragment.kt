package jart.rappi.Fragment

import android.animation.PropertyValuesHolder
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import jart.rappi.Util.Api
import jart.rappi.Util.CacheSetting
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
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [CategoryFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {

    lateinit var cContext: Context

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    private var typeApi: String  = "movie"

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
            typeApi = arguments.getString("typeApi")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var view =inflater!!.inflate(R.layout.fragment_category, container, false)
        if (container != null) {
            cContext = container.getContext()
        }



        val listMovie1 = ArrayList<mMovie>()
        val listMovie2 = ArrayList<mMovie>()
        val listMovie3 = ArrayList<mMovie>()

        val listTvSeries1 = ArrayList<mTvSeries>()
        val listTvSeries2 = ArrayList<mTvSeries>()
        val listTvSeries3 = ArrayList<mTvSeries>()


        val rvCategoryTop = view.findViewById<RecyclerView>(R.id.rvCategoryTop)
        val rvCategoryPopular = view.findViewById<RecyclerView>(R.id.rvCategoryPopular)
        val rvCategoryUpcoming = view.findViewById<RecyclerView>(R.id.rvCategoryUpcoming)

        rvCategoryTop.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)
        rvCategoryPopular.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)
        rvCategoryUpcoming.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)


        for (i in 0 until 3) {

            if(typeApi.equals("movie")){
                when (i) {
                    0 -> loadMovie("top_rated",rvCategoryTop,listMovie1,cContext)
                    1 -> loadMovie("popular",rvCategoryPopular,listMovie2,cContext)
                    2->  loadMovie("upcoming",rvCategoryUpcoming,listMovie3,cContext)
                    else -> { // Note the block
                        Log.i("Error","error")
                    }
                }

            }
            else{
                when (i) {
                    0 -> loadTvSeries("top_rated",rvCategoryTop,listTvSeries1,cContext)
                    1 -> loadTvSeries("on_the_air",rvCategoryPopular,listTvSeries2,cContext)
                    2->  loadTvSeries("popular",rvCategoryUpcoming,listTvSeries3,cContext)
                    else -> { // Note the block
                        Log.i("Error","error")
                    }
                }

            }

        }

        return view
    }



    fun loadMovie(categoryApi:String,reciclerView: RecyclerView, ListMovie: ArrayList<mMovie>,context:Context) {

        var httpClient = CacheSetting.getSetting(context)

        val retrofit = Retrofit.Builder()
                .baseUrl(Api.BaseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        // Build the gitHubApi with Retrofit and do the network request
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getCategorys("movie",categoryApi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<mMoviesCategory>>() {
                    override fun onNext(response: Response<mMoviesCategory>) {

                        if (response.raw().cacheResponse() != null) {
                            Log.d("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.i(ContentValues.TAG, "response came from server")
                        }

                        //Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()
                        Log.i(ContentValues.TAG,"------>>"+response.body()!!.results.toString());
                        val movies = response.body()!!.results
                        Log.i(ContentValues.TAG,""+movies.size)

                        for (i in 0 until movies.size) {

                            try{
                                Log.i(ContentValues.TAG,""+movies.get(i).backdrop_path)
                                val p = movies.get(i)
                                ListMovie.add(mMovie(p.vote_count,p.id,p.video,p.vote_average,p.title,p.popularity,p.poster_path,p.original_language,p.original_title,p.genre_ids,p.backdrop_path,p.adult,p.overview,p.release_date))
                            }
                            catch (e: JSONException) {
                                e.printStackTrace();
                            }

                        }
                        Log.i(ContentValues.TAG,"Save in adapter")
                        val adapter = MovieAdapter(ListMovie)
                        reciclerView.adapter = adapter
                        Log.i(ContentValues.TAG,"enviando a reciclerView")

                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }


    fun loadTvSeries(categoryApi:String,reciclerView: RecyclerView,ListMovie: ArrayList<mTvSeries>,context:Context) {


        var httpClient = CacheSetting.getSetting(context)


        //https://api.github.com/
        val retrofit = Retrofit.Builder()
                .baseUrl(Api.BaseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        // Build the gitHubApi with Retrofit and do the network request
        val apiService = retrofit.create(ApiService::class.java)
        apiService.getCategorysTV("tv",categoryApi)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<mTvSeriesCategory>>() {
                    override fun onNext(response: Response<mTvSeriesCategory>) {

                        if (response.raw().cacheResponse() != null) {
                            Log.d("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.i(ContentValues.TAG, "response came from server")
                        }

                        //Toast.makeText(context, response.body().toString(), Toast.LENGTH_SHORT).show()
                        Log.i(ContentValues.TAG,"------>>"+response.body()!!.results.toString());
                        val tvSeries = response.body()!!.results
                        Log.i(ContentValues.TAG,""+tvSeries.size)

                        for (i in 0 until tvSeries.size) {

                            try{
                                Log.i(ContentValues.TAG,""+tvSeries.get(i).backdrop_path)
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
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
        }
    }
    */

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
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
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
