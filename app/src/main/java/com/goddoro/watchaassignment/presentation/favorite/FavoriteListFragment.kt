package com.goddoro.watchaassignment.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.watchaassignment.databinding.FragmentFavoriteListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteListFragment : Fragment() {

    private lateinit var mBinding : FragmentFavoriteListBinding
    private val mViewModel : FavoriteListViewModel by viewModel()

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


    }

    private fun observeViewModel() {


    }

    companion object {
        fun newInstance() = FavoriteListFragment()
    }
}