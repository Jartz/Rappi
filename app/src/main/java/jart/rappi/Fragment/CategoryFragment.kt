package jart.rappi.Fragment

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
import android.widget.ProgressBar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import jart.rappi.Adapter.MovieAdapter
import jart.rappi.Adapter.TvSeriesAdapter
import jart.rappi.Interface.ApiService
import jart.rappi.Model.mMoviesCategory
import jart.rappi.Model.mTvSeriesCategory

import jart.rappi.R
import jart.rappi.Util.Api
import jart.rappi.Util.CacheSetting
import jart.rappi.Util.Cartelera
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class CategoryFragment : Fragment() {

    lateinit var cContext: Context

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var progress:ProgressBar

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

        progress = view.findViewById<ProgressBar>(R.id.progressbar)
        progress.setVisibility(View.VISIBLE)

        val rvCategoryTop = view.findViewById<RecyclerView>(R.id.rvCategoryTop)
        val rvCategoryPopular = view.findViewById<RecyclerView>(R.id.rvCategoryPopular)
        val rvCategoryUpcoming = view.findViewById<RecyclerView>(R.id.rvCategoryUpcoming)

        rvCategoryTop.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)
        rvCategoryPopular.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)
        rvCategoryUpcoming.layoutManager = GridLayoutManager(this.cContext, 1, GridLayoutManager.HORIZONTAL, false)


        for (i in 0 until 3) {

            if(typeApi.equals("movie")){
                when (i) {
                    0 -> Cartelera.setListByMediType("movie","top_rated",rvCategoryTop,cContext,progress)
                    1 -> Cartelera.setListByMediType("movie","popular",rvCategoryPopular,cContext,progress)
                    2->  Cartelera.setListByMediType("movie","upcoming",rvCategoryUpcoming,cContext,progress)
                    else -> { // Note the block
                        Log.i("Error","error")
                    }
                }

            }
            else{
                when (i) {
                    0 -> Cartelera.setListByMediType("tv","top_rated",rvCategoryTop,cContext,progress)
                    1 -> Cartelera.setListByMediType("tv","on_the_air",rvCategoryPopular,cContext,progress)
                    2->  Cartelera.setListByMediType("tv","popular",rvCategoryUpcoming,cContext,progress)
                    else -> { // Note the block
                        Log.i("Error","error")
                    }
                }

            }

        }

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

        fun newInstance(param1: String, param2: String): CategoryFragment {
            val fragment = CategoryFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
