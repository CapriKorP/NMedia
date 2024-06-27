package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
interface PostRepository {
    fun getAll(): List<Post>
    fun like(id:Long)
    fun share(id: Long)
    fun removeByID(id: Long)
    fun save(post: Post)
    fun playMedia(id: Long)
    fun openPost(id: Long)
}