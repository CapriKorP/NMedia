package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeByID(post.id)
            }

            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        viewModel.edited.observe(this) {
            if (it.id != 0L) {
                binding.content.setText(it.content)
                binding.postTextEdit.setText(it.content)
                binding.content.focusAndShowKeyboard()
                binding.group.visibility = View.VISIBLE
            }

            binding.save.setOnClickListener {
                val context = binding.content.text.toString()
                if (context.isBlank()) {
                    Toast.makeText(this, R.string.empty_сontent, Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(context)
                viewModel.save()

                binding.content.setText("")
                binding.content.clearFocus()
                binding.group.visibility = View.GONE
                AndroidUtils.hideKeyboard(binding.content)
            }

            binding.cancelEdit.setOnClickListener {
                binding.content.setText("")
                binding.content.clearFocus()
                binding.group.visibility = View.GONE
                AndroidUtils.hideKeyboard(binding.content)
            }
        }
    }
}