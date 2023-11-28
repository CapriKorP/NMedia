package ru.netology.nmedia.dto

   data class Post(
       val id: Int,
       val author: String,
       val content: String,
       val published: String,
       var likes: Int = 1099,
       var share: Int = 900_000,
       var view: Int = 10_000,
       var likedByMe: Boolean
   )