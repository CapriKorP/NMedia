package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun like(id:Long)
    fun share(id: Long)
    fun removeByID(id: Long)
    fun save(post: Post)
    fun playMedia(id: Long)
    fun openPost(id: Long)
}