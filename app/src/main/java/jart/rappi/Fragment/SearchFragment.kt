package jart.rappi.Fragment

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import jart.rappi.Adapter.MovieAdapter
import jart.rappi.Adapter.SearchAdapter
import jart.rappi.Interface.ApiService
import jart.rappi.Model.mMovie
import jart.rappi.Model.mMoviesCategory

import jart.rappi.R
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


class SearchFragment : Fragment() {

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

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        if (container != null) {
            cContext = container.getContext()
        }

        var view =inflater!!.inflate(R.layout.fragment_search, container, false)
        val etSarch = view.findViewById<EditText>(R.id.etSearch)
        val tvsearchinfo = view.findViewById<TextView>(R.id.tvSearchInfo)

        val listMovie = ArrayList<mMovie>()
        val rvMovies = view.findViewById<RecyclerView>(R.id.rvSearch)

        rvMovies.layoutManager = GridLayoutManager(this.cContext, 3, GridLayoutManager.VERTICAL, false)

        etSarch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                if (s.length>1){
                    tvsearchinfo.visibility=GONE
                    listMovie.clear()
                    SearchMultiple(s.toString(),rvMovies,listMovie,cContext)
                }
                else{
                    tvsearchinfo.visibility= VISIBLE
                }
            }
        })

        return view
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        if (mListener != null) {
            mListener!!.onFragmentInteraction(uri)
        }
    }

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


        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): SearchFragment {
            val fragment = SearchFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }


    fun SearchMultiple(query:String,reciclerView: RecyclerView, ListMovie: ArrayList<mMovie>, context: Context) {

        var httpClient = CacheSetting.getSetting(context)


        //https://api.github.com/
        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        // Build the gitHubApi with Retrofit and do the network request
        val apiService = retrofit.create(ApiService::class.java)
        apiService.searchMultiple(query,"13115ee8951044bf35230ce959846751","en-US","1","false")
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

                        Log.i(ContentValues.TAG,"------>>"+response.body()!!.results.toString());
                        val movies = response.body()!!.results
                        Log.i(ContentValues.TAG,""+movies.size)

                        for (i in 0 until movies.size) {

                            try{
                                Log.i(ContentValues.TAG,""+movies.get(i).backdrop_path)
                                val path =movies.get(i).backdrop_path
                                val p = movies.get(i)
                                if(!path.isNullOrBlank()){
                                    ListMovie.add(mMovie(p.vote_count,p.id,p.video,p.vote_average,p.title,p.popularity,p.poster_path,p.original_language,p.original_title,p.genre_ids,p.backdrop_path,p.adult,p.overview,p.release_date))
                                }
                             }
                            catch (e: JSONException) {
                                e.printStackTrace();
                            }

                        }

                        val adapter = SearchAdapter(ListMovie)
                        reciclerView.adapter = adapter

                    }

                    override fun onComplete() {}

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }



}
