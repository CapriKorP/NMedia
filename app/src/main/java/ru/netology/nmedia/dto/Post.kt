package ru.netology.nmedia.dto

   data class Post(
       val id: Int,
       val author: String,
       val content: String,
       val published: String,
       var share: Int,
       val view: Int,
       var likedByMe: Boolean,
       var likes: Int
   )

