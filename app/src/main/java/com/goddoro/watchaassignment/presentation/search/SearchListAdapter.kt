package com.goddoro.watchaassignment.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.databinding.ItemSearchBinding
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent
import androidx.databinding.library.baseAdapters.BR

class SearchListAdapter: RecyclerView.Adapter<SearchListAdapter.SearchViewHolder>() {


    private val onClick: PublishSubject<MusicItem> = PublishSubject.create()
    val clickEvent: Observable<MusicItem> = onClick

    private val diff = object : DiffUtil.ItemCallback<MusicItem>() {
        override fun areItemsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
            return oldItem.collectionId == newItem.collectionId
        }

        override fun areContentsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<MusicItem>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchBinding.inflate(inflater, parent, false)

        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class SearchViewHolder(private val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {

            binding.root.setOnClickListener{

            }

        }

        fun bind(item: MusicItem) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()


        }
    }

}

@BindingAdapter("app:recyclerview_search_items")
fun RecyclerView.setSearchItemList(items: List<MusicItem>?) {
    (adapter as? SearchListAdapter)?.run {
        this.submitItems(items)
    }
}
