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
import com.goddoro.watchaassignment.util.disposedBy
import com.goddoro.watchaassignment.util.observeOnce
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment : Fragment() {

    private lateinit var mBinding : FragmentFavoriteListBinding
    private val mViewModel : MainViewModel by sharedViewModel()

    private val compositeDisposable = CompositeDisposable()

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
    }

    private fun setupRecyclerView() {

        mBinding.recyclerview.apply {

            adapter = FavoriteListAdapter()
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            errorInvoked.observe(viewLifecycleOwner, {
                Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
            })
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
    }

    companion object {
        fun newInstance() = FavoriteListFragment()
    }
}