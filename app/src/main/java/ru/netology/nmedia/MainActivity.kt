package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.Converter



val shareCountStep = 100_000 ///Переменная, задающее количество прибавленных шар

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Это новая Нетология, старую я закрыл",
            published = "32 мая в 24:61",
            likedByMe = false
        )

        with(binding) {
            tvChanel.text = post.author
            tvDateTime.text = post.published
            tvPostText.text = post.content
            tvLikes.text = Converter.converter.converter(post.likes)
            tvShare.text = Converter.converter.converter(post.share)
            tvWatching.text = Converter.converter.converter(post.view)

            if (post.likedByMe) {
                ibLikes?.setImageResource(R.drawable.baseline_favorite_24)
            }

            ibShare?.setOnClickListener {
                d("stuff","share")
                post.share = post.share + shareCountStep
                tvShare?.text = (Converter.converter.converter(post.share))
            }


            ibLikes?.setOnClickListener {
                d("stuff","likes")
                post.likedByMe = !post.likedByMe
                ibLikes.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_24 else R.drawable.baseline_favorite_border_24
                )
                if(post.likedByMe) post.likes++ else post.likes--
                tvLikes?.text = Converter.converter.converter(post.likes)
            }

            binding.root?.setOnClickListener{
                d("stuff", "root")
            }
        }
    }
}





