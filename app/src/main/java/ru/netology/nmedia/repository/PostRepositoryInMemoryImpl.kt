package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post
import java.util.Calendar
import java.util.Date

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Kotlin: современный, статически типизированный язык программирования от компании JetBrains. Он обеспечивает более простой и безопасный код и совместим с библиотеками Java.",
            published = "1 мая в 15:06",
            likes = 10,
            shared = 2,
            viewed = 123,
            likedByMe = true,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "United Systems Administrators",
            content = "Рабочий день системного администратора",
            published = "2 мая в 15:06",
            likes = 8,
            shared = 15,
            viewed = 100,
            likedByMe = false,
            videoURL = "https://www.youtube.com/watch?v=2uc4EBrt9l8",
            videoViewed = 15
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "C++: мощный и универсальный язык программирования. Он славится своей производительностью и позволяет создавать быстрые и эффективные приложения.",
            published = "3 мая в 15:06",
            likes = -1,
            shared = 165234,
            viewed = 1500,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Ruby: интерпретируемый язык программирования, который славится своим элегантным синтаксисом и поддержкой концепции “меньше, да лучше”. Он часто используется для веб-разработки и создания скриптов.",
            published = "4 мая в 15:06",
            likes = 1042360,
            shared = 15345,
            viewed = 15657300,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "PHP: скриптовый язык, применяемый для разработки веб-приложений. Благодаря простоте и открытому исходному коду он широко используется для построения динамических веб-сайтов.",
            published = "5 мая в 15:06",
            likes = 107630,
            shared = 165745,
            viewed = 150457680,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "C#: язык программирования платформы .NET Framework, созданный компанией Microsoft. Он обладает строгой типобезопасностью, поддержкой LINQ и возможностью работы с различными платформами и библиотеками.",
            published = "6 мая в 15:06",
            likes = 100654,
            shared = 1525,
            viewed = 1506450,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "JavaScript: язык программирования, работающий в браузере и на стороне сервера. Он используется для создания интерактивных веб-приложений и динамической генерации контента на веб-страницах.",
            published = "7 мая в 15:06",
            likes = 10045645,
            shared = 154732,
            viewed = 15008567,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Java: объектно-ориентированный язык, разработанный компанией Oracle. Он отличается безопасностью, многопоточностью и возможностью разработки на нем кросс-платформенных приложений.",
            published = "8 мая в 15:06",
            likes = 100352,
            shared = 1554376,
            viewed = 15005477,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        ),
        Post(
            id = nextId++,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Python: высокоуровневый язык программирования с акцентом на читаемость и удобочитаемость кода. Он имеет динамическую типизацию, автоматическое сборщик мусора и большой стандартный библиотека.",
            published = "9 мая в 15:06",
            likes = 9847100,
            shared = 165653,
            viewed = 1456500,
            likedByMe = false,
            videoURL = "",
            videoViewed = 0
        )
    )

    private val data = MutableLiveData(posts)
    override fun getAll(): MutableLiveData<List<Post>> = data
    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
        data.value = posts
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shared = it.shared + 1)
        }
        data.value = posts
    }

    override fun removeByID(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    author = "Me",
                    published = Calendar.getInstance().time.toString()
                )
            ) + posts
        } else {
            posts.map { if (it.id == post.id) it.copy(content = post.content) else it }
        }
        data.value = posts
    }

    override fun playMedia(post: Post) {
        TODO("Not yet implemented")
    }
}