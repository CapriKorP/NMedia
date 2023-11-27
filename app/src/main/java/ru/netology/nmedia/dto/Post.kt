package ru.netology.nmedia.dto

   data class Post(
       val id: Int,
       val author: String,
       val content: String,
       val published: String,
       val likes: Int,
       val share: Int,
       val view: Int,
       val likedByMe: Boolean
   )

