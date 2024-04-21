package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likes = 0,
    shared = 0,
    viewed = 0,
    likedByMe = false,
    videoURL = "",
    videoViewed = 0
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun like(id:Long) = repository.like(id)
    fun share(id:Long) = repository.share(id)
    fun removeByID(id: Long) = repository.removeByID(id)

    fun save() {
        edited.value?.let{
            repository.save(it)
        }
        edited.value = empty
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun changeContent (text:String) {
        edited.value?.let{
            if(it.content != text.trim()) {
                edited.value = it.copy(content = text)
            }
        }
    }

    fun cancelEdit() {
        edited.value = empty
    }
}