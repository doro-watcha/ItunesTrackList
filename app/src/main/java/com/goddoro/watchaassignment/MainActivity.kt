package com.goddoro.watchaassignment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.goddoro.watchaassignment.databinding.ActivityMainBinding
import com.goddoro.watchaassignment.navigation.MainMenu
import com.goddoro.watchaassignment.presentation.favorite.FavoriteListFragment
import com.goddoro.watchaassignment.presentation.search.SearchListFragment
import com.goddoro.watchaassignment.util.Broadcast
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModel()

    private lateinit var fragment1: SearchListFragment
    private lateinit var fragment2: FavoriteListFragment
    private lateinit var curFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        setContentView(mBinding.root)

        initFragments(savedInstanceState == null)
        setupBottomNavigationView()

    }

    private fun initFragments(isFirstCreation: Boolean) {


        fragment1 = supportFragmentManager.findFragmentByTag("0") as? SearchListFragment
            ?: SearchListFragment.newInstance()
        fragment2 = supportFragmentManager.findFragmentByTag("1") as? FavoriteListFragment
            ?: FavoriteListFragment.newInstance()

        curFragment = when (mViewModel.menu.value) {

            MainMenu.SEARCH -> fragment1
            MainMenu.FAVORITE -> fragment2
            else -> throw Error()
        }

        if (isFirstCreation) {
            val fm = supportFragmentManager
            fm.beginTransaction().add(R.id.fragmentContainer, fragment1, "0").show(fragment1)
                .commit()
            fm.beginTransaction().add(R.id.fragmentContainer, fragment2, "1").hide(fragment2)
                .commit()

        }
    }

    private fun setupBottomNavigationView() {

        mBinding.bottomNavigationView.setOnNavigationItemSelectedListener {

            val selectedMenu = MainMenu.parseIdToMainMenu(it.itemId)

            changeFragment(selectedMenu)
            true

        }

        mBinding.bottomNavigationView.setOnNavigationItemReselectedListener {

            when (MainMenu.parseIdToMainMenu(it.itemId)) {

                MainMenu.SEARCH -> {
                    Broadcast.searchListReselectBroadcast.onNext(Unit)

                }
                MainMenu.FAVORITE -> {
                    Broadcast.favoriteListReselectBroadcast.onNext(Unit)
                }
            }
        }

    }


    private fun changeFragment(menu: MainMenu) {

        Log.d(TAG, "Change Fragment")

        val willShow = when (menu) {
            MainMenu.SEARCH -> fragment1
            MainMenu.FAVORITE -> fragment2
        }
        Log.d(TAG, willShow.tag.toString())
        supportFragmentManager.beginTransaction().hide(curFragment).show(willShow).commit()
        curFragment = willShow
        mViewModel.menu.value = menu
    }

    override fun onBackPressed() {

        if ( mViewModel.menu.value == MainMenu.FAVORITE) {
            mBinding.bottomNavigationView.selectedItemId = MainMenu.SEARCH.menuId
        } else {
            super.onBackPressed()
        }
    }

}