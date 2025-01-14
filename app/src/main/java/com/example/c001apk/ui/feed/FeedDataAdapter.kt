package com.example.c001apk.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.c001apk.BR
import com.example.c001apk.adapter.ItemListener
import com.example.c001apk.databinding.ItemFeedArticleImageBinding
import com.example.c001apk.databinding.ItemFeedArticleShareUrlBinding
import com.example.c001apk.databinding.ItemFeedArticleTextBinding
import com.example.c001apk.databinding.ItemFeedContentBinding
import com.example.c001apk.logic.model.FeedArticleContentBean
import com.example.c001apk.logic.model.HomeFeedResponse
import com.example.c001apk.logic.model.Like

class FeedDataAdapter(
    private val listener: ItemListener,
    private val feedDataList: List<HomeFeedResponse.Data>?,
    private val articleList: List<FeedArticleContentBean.Data>?,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class FeedViewHolder(val binding: ItemFeedContentBinding, val listener: ItemListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: HomeFeedResponse.Data) {
            binding.setVariable(BR.data, data)
            binding.setVariable(BR.listener, listener)
            binding.likeData = Like().also {
                it.apply {
                    data.userAction?.like?.let { like ->
                        isLike.set(like)
                    }
                    likeNum.set(data.likenum)
                }
            }
            binding.executePendingBindings()
        }
    }

    class TextViewHolder(val binding: ItemFeedArticleTextBinding, val listener: ItemListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FeedArticleContentBean.Data) {
            binding.setVariable(BR.data, data)
            binding.setVariable(BR.listener, listener)
            binding.textView.paint.isFakeBoldText =
                (bindingAdapterPosition == 0 || bindingAdapterPosition == 1) && data.title == "true"
            binding.executePendingBindings()
        }
    }

    class ImageViewHolder(val binding: ItemFeedArticleImageBinding, val listener: ItemListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FeedArticleContentBean.Data) {
            binding.setVariable(BR.data, data)
            binding.executePendingBindings()
        }
    }

    class ShareUrlViewHolder(
        val binding: ItemFeedArticleShareUrlBinding,
        val listener: ItemListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FeedArticleContentBean.Data) {
            binding.setVariable(BR.data, data)
            binding.setVariable(BR.listener, listener)
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {

            0 -> FeedViewHolder(
                ItemFeedContentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )

            1 -> TextViewHolder(
                ItemFeedArticleTextBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )

            2 -> ImageViewHolder(
                ItemFeedArticleImageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )

            3 -> ShareUrlViewHolder(
                ItemFeedArticleShareUrlBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), listener
            )

            else -> throw IllegalArgumentException("invalid viewType: $viewType")
        }
    }

    override fun getItemCount(): Int {
        return if (feedDataList.isNullOrEmpty() && !articleList.isNullOrEmpty()) articleList.size
        else if (!feedDataList.isNullOrEmpty() && articleList.isNullOrEmpty()) feedDataList.size
        else 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FeedViewHolder -> holder.bind(feedDataList!![position])
            is TextViewHolder -> holder.bind(articleList!![position])
            is ImageViewHolder -> holder.bind(articleList!![position])
            is ShareUrlViewHolder -> holder.bind(articleList!![position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (articleList.isNullOrEmpty()) 0
        else when (articleList[position].type) {
            "text" -> 1
            "image" -> 2
            "shareUrl" -> 3
            else -> throw IllegalArgumentException("invalid article type: ${articleList[position].type}")
        }
    }

}