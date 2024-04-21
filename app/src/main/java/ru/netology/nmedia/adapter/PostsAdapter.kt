package ru.netology.nmedia.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post

interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onShare(post: Post) {}
    fun onRemove(post: Post) {}
    fun onEdit(post: Post) {}
    fun playMedia(post: Post) {}
    fun openPost(post: Post)
}

class PostsAdapter(
    private val onInteractionListener: OnInteractionListener,
) :
    ListAdapter<Post, PostViewHolder>(PostDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener

) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            tvAuthor.text = post.author
            tvPublished.text = post.published
            tvContent.text = post.content
            bLikes.text = if (post.likes < 0) "Negative" else converter(post.likes)
            bShare.text = converter(post.shared)
            bWatching.text = converter(post.viewed)

            if (post.videoURL != "") {
                group.visibility = View.VISIBLE
                tvContent.visibility = View.GONE
                tvTitle.text = post.content
                tvWatch.text = post.videoViewed.toString() + " просмотров"
            }

            bLikes.isChecked = post.likedByMe

            bLikes.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            bShare.setOnClickListener {
                onInteractionListener.onShare(post)
            }

            popupMenu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }

                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }

                            else -> false
                        }
                    }
                }.show()
            }

            binding.bPlay.setOnClickListener() {
                onInteractionListener.playMedia(post)
            }

            binding.ivPlay.setOnClickListener() {
                onInteractionListener.playMedia(post)
            }

            binding.tvContent.setOnClickListener{
                onInteractionListener.openPost(post)
            }
        }
    }
}

object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}

fun converter(value: Int): String = when (value) {
    in 0..999 -> "$value"
    in 1000..1099 -> "${(value / 1000)}K"
    in 1100..9999 -> "${String.format("%.1f", value.toDouble() / 1000)}K"
    in 10000..999999 -> "${(value / 1000)}K"
    in -10..-1 -> converter(value)
    else ->
        if (value > 0) "${String.format("%.1f", value.toDouble() / 1_000_000)}М" else "Negative"
}
