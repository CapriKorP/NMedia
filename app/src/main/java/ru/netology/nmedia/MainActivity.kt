package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.netology.nmedia.databinding.ActivityMainBinding

class Post(id: Int, author: String, content: String, published: String, likedByMe: Boolean) {
    var id,
    var author,
    var content,
    var published,
    var likedByMe
}

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
            author.text = post.author
            content.text = post.content
            published.text = post.published
            if (likedByMe) {
                ibLikes?.setImageResource(R.drawable.baseline_favorite_border_24)
            }
        }

    }


}


