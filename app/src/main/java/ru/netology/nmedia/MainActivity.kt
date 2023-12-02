package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.*
import androidx.activity.viewModels
import ru.netology.nmedia.databinding.ActivityMainBinding

import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.dto.Converter.converter



val shareCountStep = 100_000 ///Переменная, задающее количество прибавленных шар

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                tvChanel.text = post.author
                tvDateTime.text = post.published
                tvPostText.text = post.content
                if (post.likes < 0) {

                    tvLikes.text = "Negative"
                } else {
                    tvLikes.text = converter.converter(post.likes)
                }
                tvShare.text = converter.converter(post.share)
                tvWatching.text = converter.converter(post.view)

                if (post.likedByMe) {
                    ibLikes?.setImageResource(R.drawable.baseline_favorite_24)
                }
            }

            binding.ibShare?.setOnClickListener {
                d("stuff", "share")
                viewModel.share()

                binding.tvShare.text = converter.converter(post.share)
            }

            binding.ibLikes.setOnClickListener {
                d("stuff", "likes")
                viewModel.like()
                binding.ibLikes.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )

                binding.tvLikes.text = converter.converter((post.likes))

            }

            binding.root?.setOnClickListener{
                d("stuff", "root")
            }

            binding.ivLogo?.setOnClickListener {
                d("stuff", "avatar")
            }
        }
    }
}