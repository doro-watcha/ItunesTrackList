package com.goddoro.watchaassignment.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goddoro.watchaassignment.MainViewModel
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.databinding.FragmentSearchListBinding
import com.goddoro.watchaassignment.util.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchListFragment : Fragment() {

    private val TAG = SearchListFragment::class.java.simpleName

    private lateinit var mBinding: FragmentSearchListBinding

    private val mViewModel: MainViewModel by sharedViewModel()

    private val starPressChanged : BehaviorSubject<MusicItem> = BehaviorSubject.create()

    private val compositeDisposable = CompositeDisposable()
    private val starDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchListBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner


        setupRecyclerView()
        observeViewModel()
        setupRefreshLayout()
        listenStarChange()
    }

    private fun setupRecyclerView() {

        mBinding.recyclerview.apply{
            adapter = SearchListAdapter().apply {

                clickStar.subscribe({

                    Log.d(TAG, it.isFavorite.get().toString())
                    it.isFavorite.toggle()
                    starPressChanged.onNext(it)
                },{
                    mViewModel.errorInvoked.value = it
                }).disposedBy(compositeDisposable)
            }
        }

    }

    private fun setupRefreshLayout() {


        mBinding.mSwipeRefreshLayout.apply {

            setOnRefreshListener {
                mViewModel.refreshSearch()
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply{

            onLoadCompleted.observe(viewLifecycleOwner){
                if ( it == true ) {
                    mBinding.mSwipeRefreshLayout.isRefreshing = false
                }
            }

            onInsertCompleted.observeOnce(viewLifecycleOwner){
                refreshFavorite()
                Toast.makeText(context, "즐겨찾기에 추가하였습니다.",Toast.LENGTH_SHORT).show()
            }

            onDeleteCompleted.observeOnce(viewLifecycleOwner){
                refreshFavorite()
                Toast.makeText(context, "즐겨찾기에서 해제하였습니다..",Toast.LENGTH_SHORT).show()
            }

            searchMusicList.observe(viewLifecycleOwner, {
                Log.d(TAG, it.size.toString())
            })
            errorInvoked.observe(viewLifecycleOwner, {
                Log.d(TAG, it.toString())
                Toast.makeText(context, it.message,Toast.LENGTH_SHORT).show()
            })
        }
    }

    private fun listenStarChange () {

        starPressChanged
            .debounce(500L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe({
                if ( it.isFavorite.get() ) mViewModel.addFavorite(it)
                else mViewModel.deleteFavorite(it)
            },{

            }).disposedBy(starDisposable)
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
        starDisposable.clear()
    }


    companion object {

        fun newInstance() = SearchListFragment()
    }
}