package ru.netology.nmedia.dao


import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.dto.Post

class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHOR} TEXT NOT NULL,
            ${PostColumns.COLUMN_CONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHED} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0
            ${PostColumns.COLUMN_SHARED} INTEGER NOT NULL DEFAULT 0
            ${PostColumns.COLUMN_VIEWED} INTEGER NOT NULL DEFAULT 0
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_VIDEO_URL} TEXT NOT NULL
            ${PostColumns.COLUMN_VIDEO_VIEWED} INTEGER NOT NULL DEFAULT 0
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHOR = "author"
        const val COLUMN_CONTENT = "content"
        const val COLUMN_PUBLISHED = "published"
        const val COLUMN_LIKES = "likes"
        const val COLUMN_SHARED = "shared"
        const val COLUMN_VIEWED = "viewed"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_VIDEO_URL = "videoURL"
        const val COLUMN_VIDEO_VIEWED = "videoViewed"
        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHOR,
            COLUMN_CONTENT,
            COLUMN_PUBLISHED,
            COLUMN_LIKES,
            COLUMN_SHARED,
            COLUMN_VIEWED,
            COLUMN_LIKED_BY_ME,
            COLUMN_VIDEO_URL,
            COLUMN_VIDEO_VIEWED
        )
    }

    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun like(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }


    override fun share(id: Long) {
        db.execSQL(
            """
                UPDATE posts SET
                shared = shared + 1
                WHERE id = ?
            """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeByID(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
        arrayOf((id.toString()))
        )
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

private fun map(cursor: Cursor): Post {
    with(cursor) {
        return Post(
            id = getLong(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_ID)),
            author = getString(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_AUTHOR)),
            content = getString(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_CONTENT)),
            published = getString(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_PUBLISHED)),
            likes = getInt(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_LIKES)),
            shared = getInt(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_SHARED)),
            viewed = getInt(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_VIEWED)),
            likedByMe = getInt(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_LIKED_BY_ME)) != 0,
            videoURL = getString(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_VIDEO_URL)),
            videoViewed = getInt(getColumnIndexOrThrow(ru.netology.nmedia.dao.PostDaoImpl.PostColumns.COLUMN_VIDEO_VIEWED)),
        )
    }
}