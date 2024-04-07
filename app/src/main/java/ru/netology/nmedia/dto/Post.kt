package ru.netology.nmedia.dto

   data class Post(
       val id: Long,
       val author: String,
       val content: String,
       val published: String,
       val likes: Int = 100000,
       val shared: Int = 30,
       val viewed: Int = 100,
       val likedByMe: Boolean = false
   )