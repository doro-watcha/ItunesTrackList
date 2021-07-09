package com.goddoro.watchaassignment.presentation.search

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goddoro.watchaassignment.databinding.FragmentSearchListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchListFragment : Fragment() {

    private val TAG = SearchListFragment::class.java.simpleName

    private lateinit var mBinding: FragmentSearchListBinding

    private val mViewModel: SearchListViewModel by viewModel()

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
    }

    private fun setupRecyclerView() {

        mBinding.recyclerview.apply{
            adapter = SearchListAdapter()
        }

    }

    private fun setupRefreshLayout() {


        mBinding.mSwipeRefreshLayout.apply {

            setOnRefreshListener {
                mViewModel.refresh()
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

            searchMusicList.observe(viewLifecycleOwner, {
                Log.d(TAG, it.size.toString())
            })
            errorInvoked.observe(viewLifecycleOwner, {
                Log.d(TAG, it.toString())
                Toast.makeText(context, it.message,Toast.LENGTH_SHORT).show()
            })
        }
    }




    companion object {

        fun newInstance() = SearchListFragment()
    }
}