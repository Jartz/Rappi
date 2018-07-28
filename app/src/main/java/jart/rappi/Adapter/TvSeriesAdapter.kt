package jart.rappi.Adapter

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import jart.rappi.Fragment.SingleFragment
import jart.rappi.Model.mTvSeries
import jart.rappi.R

/**
 * Created by julianx1 on 7/26/18.
 */


class TvSeriesAdapter(val subCategoryList: ArrayList<mTvSeries>): RecyclerView.Adapter<TvSeriesAdapter.ViewHolder>() {

    override fun getItemCount(): Int {
        return subCategoryList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.cv_movie, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindItems(subCategoryList[position])
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(subcategory: mTvSeries) {

            val posterMovie = itemView.findViewById<ImageView>(R.id.posterMovie)
            val idMovie = subcategory.id

            // nameMovie.text = subcategory.backdrop_path

            val urlPhoto = "http://image.tmdb.org/t/p/w185${subcategory.poster_path}"


            Glide.with(itemView)
                    .load(urlPhoto)
                    .into(posterMovie)

            posterMovie.setOnClickListener{


                val mFragment = SingleFragment()
                val mArgs = Bundle()

                mArgs.putInt("Key", idMovie)
                mArgs.putString("TypeKey", "tv")
                mArgs.putString("title", subcategory.original_name)
                mArgs.putString("urlPoster", subcategory.poster_path)
                mArgs.putString("age", subcategory.first_air_date)
                mArgs.putString("description", subcategory.overview)



                mFragment.setArguments(mArgs)

                val transaction = (itemView.context as AppCompatActivity)
                        .supportFragmentManager
                        .beginTransaction()
                transaction.replace(R.id.fragmentHolder, mFragment)
                transaction.addToBackStack(null)
                transaction.commit()

            }



        }


    }
}