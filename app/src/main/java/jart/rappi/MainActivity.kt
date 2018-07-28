package jart.rappi


import android.app.Activity
import android.content.Context
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import jart.rappi.Fragment.CategoryFragment
import jart.rappi.Fragment.HomeFragment
import jart.rappi.Fragment.SearchFragment
import jart.rappi.overwrite.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager
    var NavBar: ConstraintLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        NavBar = findViewById<ConstraintLayout>(R.id.navBar)
        HomeShowFragment()

        navigationMenu.setOnNavigationItemSelectedListener(onNavigation)
        BottomNavigationViewHelper.removeShiftMode(navigationMenu)

    }


    fun HomeShowFragment() {
        NavBar!!.visibility= VISIBLE
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit)
        transaction.replace(R.id.fragmentHolder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()



    }

    fun MovieCategoryShowFragment() {

        NavBar!!.visibility= GONE

        val transaction = manager.beginTransaction()
        val fragment = CategoryFragment()
        val mArgs = Bundle()

        mArgs.putString("typeApi","movie")
        fragment.setArguments(mArgs)
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit)
        transaction.replace(R.id.fragmentHolder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }


    fun TvCategoryShowFragment() {
        NavBar!!.visibility= GONE
        val transaction = manager.beginTransaction()
        val fragment = CategoryFragment()
        val mArgs = Bundle()

         mArgs.putString("typeApi","tv")
        fragment.setArguments(mArgs)
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit)
        transaction.replace(R.id.fragmentHolder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun SearchShowFragment() {
        NavBar!!.visibility= GONE
        val transaction = manager.beginTransaction()
        val fragment = SearchFragment()
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit)
        transaction.replace(R.id.fragmentHolder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }



    private val onNavigation = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.homeNavigation -> {
                HomeShowFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.movieNavigation -> {
                MovieCategoryShowFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.seriesNavigation -> {
                TvCategoryShowFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.searchNavigation -> {
                SearchShowFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }




    override fun onBackPressed() {
       // val parentLayout = findViewById<View>(android.R.id.content)
        //Snackbar.make(parentLayout, "Usa el menu de Navegación ", Snackbar.LENGTH_SHORT).show()

    }






}

