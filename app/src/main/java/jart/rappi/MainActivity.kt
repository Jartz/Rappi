package jart.rappi


import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import jart.rappi.Fragment.CategoryFragment
import jart.rappi.Fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    val manager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HomeShowFragment()
        navigationMenu.setOnNavigationItemSelectedListener(onNavigation)
    }

    fun HomeShowFragment() {
        val transaction = manager.beginTransaction()
        val fragment = HomeFragment()
        transaction.replace(R.id.fragmentHolder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()

    }

    fun CategoryShowFragment() {
        val transaction = manager.beginTransaction()
        val fragment = CategoryFragment()
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
                CategoryShowFragment()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }
}

