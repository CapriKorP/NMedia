package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl(application: PostDao) : PostRepository {
    override fun getAll(): MutableLiveData<List<Post>> {
        TODO("Not yet implemented")
    }

    override fun like(id: Long) {
        TODO("Not yet implemented")
    }

    override fun share(id: Long) {
        TODO("Not yet implemented")
    }

    override fun removeByID(id: Long) {
        TODO("Not yet implemented")
    }

    override fun save(post: Post) {
        TODO("Not yet implemented")
    }

    override fun playMedia(id: Long) {
        TODO("Not yet implemented")
    }

    override fun openPost(id: Long) {
        TODO("Not yet implemented")
    }
}