package jart.rappi.Fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView

import jart.rappi.R
import jart.rappi.Util.Cartelera


class SearchFragment : Fragment() {

    lateinit var cContext: Context

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    private lateinit var progress:ProgressBar

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

        progress = view.findViewById<ProgressBar>(R.id.progressbar)
        progress.setVisibility(View.GONE)


        val etSarch = view.findViewById<EditText>(R.id.etSearch)
        val tvsearchinfo = view.findViewById<TextView>(R.id.tvSearchInfo)

        val rvMovies = view.findViewById<RecyclerView>(R.id.rvSearch)

        rvMovies.layoutManager = GridLayoutManager(this.cContext, 3, GridLayoutManager.VERTICAL, false)

        etSarch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length>1){
                    progress.setVisibility(View.VISIBLE)
                    tvsearchinfo.visibility= VISIBLE
                    Cartelera.setListFilter(s.toString(),rvMovies,cContext,progress)

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



}
