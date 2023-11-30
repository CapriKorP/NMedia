package ru.netology.nmedia.dto

   data class Post(
       val id: Int,
       val author: String,
       val content: String,
       val published: String,
       val likes: Int = 100,
       val share: Int = 30,
       val view: Int = 100,
       val likedByMe: Boolean = false
   )