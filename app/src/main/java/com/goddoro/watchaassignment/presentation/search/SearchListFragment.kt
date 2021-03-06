package com.goddoro.watchaassignment.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ObservableBoolean
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goddoro.watchaassignment.MainViewModel
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.databinding.FragmentSearchListBinding
import com.goddoro.watchaassignment.util.*
import com.goddoro.watchaassignment.util.CommonConst.SCROLL_ITEM_LIMIT
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchListFragment : Fragment() {

    private val TAG = SearchListFragment::class.java.simpleName

    private lateinit var mBinding: FragmentSearchListBinding

    private val mViewModel: MainViewModel by sharedViewModel()

    /**
     * debounce 처리를 위한 BehaviorSubject 객체
     */
    private val starPressChanged : PublishSubject<Pair<Int,MusicItem>> = PublishSubject.create()

    private val toastUtil : ToastUtil by inject()

    private val compositeDisposable = CompositeDisposable()
    private val starDisposable = CompositeDisposable()
    private val reselectDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchListBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()
        setupRecyclerView()
        observeViewModel()
        setupRefreshLayout()
        listenStarChange()
        setupBroadcast()
    }

    private fun initView() {
        mBinding.txtTitle.setGreenText()
    }

    private fun setupRecyclerView() {

        mBinding.recyclerview.apply{
            adapter = SearchListAdapter().apply {

                clickStar.subscribe({
                    it.second.isFavorite.toggle()
                    starPressChanged.onNext(it)
                },{
                    mViewModel.errorInvoked.value = it
                }).disposedBy(compositeDisposable)

                needMoreEvent.subscribe{
                    mViewModel.needMoreData()
                }.disposedBy(compositeDisposable)
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
                toastUtil.createToast("${it.trackName}을(를) Favorite에 추가하였습니다").show()
            }

            onDeleteCompleted.observeOnce(viewLifecycleOwner){
                refreshFavorite()
                toastUtil.createToast("${it.trackName}을(를) Favorite에서 삭제하었습니다").show()
            }

            errorInvoked.observe(viewLifecycleOwner, {
                debugE(TAG, it.message)
                Toast.makeText(context, "서버가 불안정합니다",Toast.LENGTH_SHORT).show()

                if ( mBinding.mSwipeRefreshLayout.isRefreshing) {
                    mBinding.mSwipeRefreshLayout.isRefreshing = false
                }
            })
        }
    }

    private fun listenStarChange () {

        starPressChanged
            .debounce(1000L, TimeUnit.MILLISECONDS)
            .addSchedulers()
            .subscribe({

                if ( it.second.isFavorite.get() ) {
                    val favoriteItem = it.second.toFavoriteItem(it.first)
                    mViewModel.addFavorite(favoriteItem)
                }
                else {
                    val favoriteItem =
                        mViewModel.favoriteList.value?.find { favoriteItem -> favoriteItem.trackId == it.second.trackId }
                    if (favoriteItem != null) mViewModel.deleteFavorite(favoriteItem)
                }
            },{

            }).disposedBy(starDisposable)
    }

    private fun setupBroadcast() {

        Broadcast.apply {

            /**
             * 너무 아래에서부터 smoothScroll하면 오래걸리므로 SCROLL_ITEM_LIMIT개 이상으로는 scrollToPosition으로 이동
             */
            searchListReselectBroadcast.subscribe{
                if ( mViewModel.searchMusicList.value?.size ?: 0 > SCROLL_ITEM_LIMIT) mBinding.recyclerview.scrollToPosition(0)
                else mBinding.recyclerview.smoothScrollToPosition(0)
            }.disposedBy(reselectDisposable)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
        starDisposable.dispose()
        reselectDisposable.dispose()
    }


    companion object {

        fun newInstance() = SearchListFragment()
    }
}