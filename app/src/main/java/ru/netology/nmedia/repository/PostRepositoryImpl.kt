package ru.netology.nmedia.repository

import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import ru.netology.nmedia.dto.Post
import java.util.concurrent.TimeUnit

class PostRepositoryImpl: PostRepository {
    private val client = OkHttpClient.Builder()
        .callTimeout(30, TimeUnit.SECONDS)
        .build()

    private val gson = Gson()
    private val type = object : TypeToken<List<Post>>() {}.type

    companion object {
        private const val BASE_URL = "http://10.0.2.2:9999"
    }

    override fun getAll(): List<Post> {
        val request = Request.Builder()
            .url("$BASE_URL/api/slow/posts")
            .build()

        val response = client.newCall(request)
            .execute()

        val responseText = response.body?.string() ?: error("response body is null")

        return gson.fromJson(responseText, type)
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