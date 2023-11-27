package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Converter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.shareCountStep

class PostRepositoryInMemoryImpl : PostRepository {
        private var post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Это новая Нетология, старую я закрыл",
            published = "32 мая в 24:61",
            likedByMe = false
        )


        private val data = MutableLiveData(post)

        override fun get(): LiveData<Post> = data
        override fun like() {
            post = post.copy(likedByMe = !post.likedByMe)
            data.value = post
            if (post.likedByMe) post.likes++ else post.likes--
            tvLikes?.text = Converter.converter.converter(post.likes)
        }

        override fun share() {
            post.share = post.share + shareCountStep
            tvShare?.text = (Converter.converter.converter(post.share))
        }
    }
}