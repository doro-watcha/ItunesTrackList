package com.goddoro.watchaassignment.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goddoro.watchaassignment.MainViewModel
import com.goddoro.watchaassignment.databinding.FragmentFavoriteListBinding
import com.goddoro.watchaassignment.util.Broadcast
import com.goddoro.watchaassignment.util.CommonConst.SCROLL_ITEM_LIMIT
import com.goddoro.watchaassignment.util.debugE
import com.goddoro.watchaassignment.util.disposedBy
import com.goddoro.watchaassignment.util.observeOnce
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment : Fragment() {

    private val TAG = FavoriteListFragment::class.java.simpleName

    private lateinit var mBinding : FragmentFavoriteListBinding
    private val mViewModel : MainViewModel by sharedViewModel()

    private val compositeDisposable = CompositeDisposable()
    private val reselectDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentFavoriteListBinding.inflate(inflater,container,false).also { mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
        setupRecyclerView()
        setupBroadcast()
    }

    private fun setupRecyclerView() {

        mBinding.recyclerview.apply {

            adapter = FavoriteListAdapter().apply {

                clickStar.subscribe{
                    mViewModel.deleteFavorite(it)

                    /**
                     * Favorite에서 삭제할 때, 현재 Music Item에 있는 star도 찾아서 같이 toggle해준다.
                     */
                    val musicItem = mViewModel.searchMusicList.value?.find { musicItem -> musicItem.trackId == it.trackId && musicItem.isFavorite.get() }
                    musicItem?.isFavorite?.set(false)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            errorInvoked.observe(viewLifecycleOwner, {
                debugE(TAG,it.message)
            })
        }

    }

    private fun setupBroadcast() {

        Broadcast.apply {

            favoriteListReselectBroadcast.subscribe{
                /**
                 * 너무 아래에서부터 smoothScroll하면 오래걸리므로 SCROLL_ITEM_LIMIT개 이상으로는 scrollToPosition으로 이동
                 */
                if ( mViewModel.favoriteList.value?.size ?: 0 > SCROLL_ITEM_LIMIT) mBinding.recyclerview.scrollToPosition(0)
                else mBinding.recyclerview.smoothScrollToPosition(0)
            }.disposedBy(reselectDisposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
        reselectDisposable.dispose()
    }

    companion object {
        fun newInstance() = FavoriteListFragment()
    }
}