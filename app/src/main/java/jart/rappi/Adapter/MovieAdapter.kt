package jart.rappi.Adapter

import android.app.PendingIntent.getActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import jart.rappi.Fragment.CategoryFragment
import jart.rappi.Fragment.SingleFragment
import jart.rappi.Model.mMovie
import jart.rappi.R
import android.support.v7.app.AppCompatActivity



/**
 * Created by julianx1 on 7/25/18.
 */


class MovieAdapter(val subCategoryList: ArrayList<mMovie>): RecyclerView.Adapter<MovieAdapter.ViewHolder>() {


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

        fun bindItems(subcategory: mMovie) {

            //!!!!val containerCv = itemView.findViewById<ConstraintLayout>(R.id.containerCv)
            //val nameMovie = itemView.findViewById<TextView>(R.id.titleMovie)
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
                mArgs.putString("TypeKey", "Movie")
                mArgs.putString("Title", "Movie")
                mArgs.putString("description", "Movie")
                mArgs.putString("photo", "Movie")
                mArgs.putString("date", "Movie")

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