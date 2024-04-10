package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.ValueConverter

interface OnInteractionListener {
    fun onLike(post: Post){}
    fun onShare(post: Post){}
    fun onRemove(post: Post){}
    fun onEdit(post: Post){}
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
            tvChanel.text = post.author
            tvDateTime.text = post.published
            tvPostText.text = post.content
            if (post.likes < 0) {
                tvLikes.text = "Negative"
            } else {
                tvLikes.text = ValueConverter.converter.converter(post.likes)
            }
            tvShare.text = ValueConverter.converter.converter(post.shared)
            tvWatching.text = ValueConverter.converter.converter(post.viewed)
            if (post.likedByMe) ibLikes.setImageResource(R.drawable.baseline_favorite_24) else ibLikes.setImageResource(
                R.drawable.baseline_favorite_border_24
            )
            ibLikes.setOnClickListener {
                onInteractionListener.onLike(post)
            }
            ibShare.setOnClickListener {
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
        }

    }
}


object PostDiffUtil : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem
}