package jart.rappi.Fragment

import android.content.ContentValues

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.youtube.player.*

import jart.rappi.R

import android.widget.Toast
import android.util.Log

import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

import jart.rappi.Interface.ApiService

import jart.rappi.Model.mVideoCAteogry
import jart.rappi.Util.Api
import jart.rappi.Util.CacheSetting

import org.json.JSONException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



class SingleFragment : Fragment()  {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    var idKey : Int = 0
    var typeKey : String = ""
    var title : String? = null
    var urlPoster : String? = null
    var age : String? = null
    var description : String? = null

    var VideoKeyYoutube : String = ""


    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)

            idKey = arguments.getInt("Key")
            typeKey = arguments.getString("TypeKey")

            title = arguments.getString("title")
            urlPoster = arguments.getString("urlPoster")
            age = arguments.getString("age")
            description = arguments.getString("description")

        }
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater!!.inflate(R.layout.fragment_single, container, false)

        getKeyVideoYoutube()

        val tvTitle = view.findViewById<TextView>(R.id.tvsingleTitle)
        val tvAge = view.findViewById<TextView>(R.id.tvsingleAge)
        val tvDescription = view.findViewById<TextView>(R.id.tvsingleDescription)
        val ivPoster = view.findViewById<ImageView>(R.id.ivSinglePoster)

        tvTitle.text = title
        tvAge.text = age
        tvDescription.text = description

        val urlPhoto = "http://image.tmdb.org/t/p/w185${urlPoster}"


        Glide.with(this)
                .load(urlPhoto)
                .into(ivPoster)


        return view
    }


    fun getKeyVideoYoutube() {

        var httpClient = CacheSetting.getSetting(context)

        val retrofit = Retrofit.Builder()
                .baseUrl(Api.BaseApi)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient)
                .build()

        val apiService = retrofit.create(ApiService::class.java)
        apiService.getVideo(idKey,typeKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Response<mVideoCAteogry>>() {
                    override fun onNext(response: Response<mVideoCAteogry>) {



                        if (response.raw().cacheResponse() != null) {
                            Log.d("Network", "response came from cache")
                        }

                        if (response.raw().networkResponse() != null) {
                            Log.i(ContentValues.TAG, "response came from server")
                        }

                        Log.i(ContentValues.TAG,"------>>"+response.body()!!.results.toString());
                        val videos = response.body()!!.results
                        Log.i(ContentValues.TAG,""+videos.size)

                        for (i in 0 until videos.size) {

                            try{
                                Log.i(ContentValues.TAG,""+videos.get(i).key)
                                VideoKeyYoutube = videos.get(i).key
                                Log.i("Video","---------->"+VideoKeyYoutube)


                            }
                            catch (e: JSONException) {
                                e.printStackTrace();
                            }

                        }

                        displayYoutube(VideoKeyYoutube)

                    }

                    override fun onComplete() {

                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })

        return
    }

    fun displayYoutube(KeyYoutube:String){

        val youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance()

        val transaction = childFragmentManager.beginTransaction()
        transaction.add(R.id.vvSingleVideo, youTubePlayerFragment).commit()


        youTubePlayerFragment.initialize(Api.api_key_youtube, object : YouTubePlayer.OnInitializedListener {

            override fun onInitializationSuccess(provider: YouTubePlayer.Provider, player: YouTubePlayer, wasRestored: Boolean) {
                if (!wasRestored) {
                    player.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT)
                    player.loadVideo(KeyYoutube.trim())
                    player.play()
                }
            }

            override fun onInitializationFailure(provider: YouTubePlayer.Provider, error: YouTubeInitializationResult) {
                val errorMessage = error.toString()
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                Log.d("errorMessage:", errorMessage)
            }
        })


    }

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
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"


        fun newInstance(param1: String, param2: String): SingleFragment {
            val fragment = SingleFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
