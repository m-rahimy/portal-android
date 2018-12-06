package ir.mrahimy.ingress.portal.view

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import ir.mrahimy.ingress.portal.R
import ir.mrahimy.ingress.portal.fragments.AddPortalFragment
import ir.mrahimy.ingress.portal.fragments.HomeFragment
import ir.mrahimy.ingress.portal.fragments.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
        HomeFragment.OnFragmentInteractionListener,
        SettingsFragment.OnFragmentInteractionListener,
        AddPortalFragment.OnFragmentInteractionListener {
    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //todo: portals list
                goto(HomeFragment.newInstance("A", "B"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_add_portal -> {
                goto(AddPortalFragment.newInstance("A", "B"))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                goto(SettingsFragment.newInstance("A", "B"))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        goto(HomeFragment.newInstance("A", "B"))
    }

    fun goto(fragment: Fragment) {
        val b = supportFragmentManager.beginTransaction()
        b.replace(R.id.main_fragment_layout, fragment)
        b.commit()
    }
}
