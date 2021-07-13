package com.goddoro.watchaassignment.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.goddoro.watchaassignment.data.MusicItem
import com.goddoro.watchaassignment.data.database.FavoriteItem
import com.goddoro.watchaassignment.databinding.ItemFavoriteBinding
import com.goddoro.watchaassignment.databinding.ItemSearchBinding
import com.goddoro.watchaassignment.util.setGreenText
import com.goddoro.watchaassignment.util.setOnDebounceClickListener
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.core.KoinComponent


class FavoriteListAdapter: RecyclerView.Adapter<FavoriteListAdapter.FavoriteViewHolder>() {

    private val onClickStar : PublishSubject<FavoriteItem> = PublishSubject.create()
    val clickStar : Observable<FavoriteItem> = onClickStar

    private val diff = object : DiffUtil.ItemCallback<FavoriteItem>() {
        override fun areItemsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem.trackId == newItem.trackId
        }

        override fun areContentsTheSame(oldItem: FavoriteItem, newItem: FavoriteItem): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diff)

    fun submitItems(items: List<FavoriteItem>?) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFavoriteBinding.inflate(inflater, parent, false)

        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) = holder.bind(differ.currentList[position])

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root),
        KoinComponent {
        init {
            /*
             * 연속 클릭에 의한 layoutPosition == -1 인 상황 제거
             */

            binding.imgStar.setOnDebounceClickListener {
                if ( layoutPosition >= 0 ) onClickStar.onNext(differ.currentList[layoutPosition])
            }

        }

        fun bind(item: FavoriteItem) {
            binding.setVariable(BR.item, item)
            binding.executePendingBindings()

            binding.txtArtistName.setGreenText()
            binding.txtCollectionName.setGreenText()
            binding.txtTrackName.setGreenText()

        }
    }

}

@BindingAdapter("app:recyclerview_favorite_items")
fun RecyclerView.setFavoriteItemList(items: List<FavoriteItem>?) {
    (adapter as? FavoriteListAdapter)?.run {
        this.submitItems(items)
    }
}
