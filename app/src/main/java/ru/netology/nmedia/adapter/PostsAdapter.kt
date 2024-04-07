package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.ValueConverter

typealias OnLikeListener = (post: Post) -> Unit
typealias OnShareListener = (post: Post) -> Unit
class PostsAdapter(private val onLikeListener: OnLikeListener,private val onShareListener: OnShareListener) :
    RecyclerView.Adapter<PostViewHolder>() {
    var list = emptyList<Post>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onLikeListener, onShareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = list[position]
        holder.bind(post)
    }

    override fun getItemCount(): Int = list.size
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onLikeListener: OnLikeListener,
    private val onShareListener: OnShareListener
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
            if (post.likedByMe) ibLikes?.setImageResource(R.drawable.baseline_favorite_24) else ibLikes?.setImageResource(
                R.drawable.baseline_favorite_border_24
            )


            ibLikes.setOnClickListener {
                onLikeListener(post)
            }

            ibShare.setOnClickListener {
                onShareListener(post)
            }

        }
    }
}