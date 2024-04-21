package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_TEXT
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.R.string.text_input_title_edit
import ru.netology.nmedia.databinding.ActivityNewPostBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()

        binding.content.requestFocus()

        val incomingText = intent?.getStringExtra(Intent.EXTRA_TEXT)
        if(incomingText.isNullOrBlank()) {
            binding.title.text = getResources().getString(R.string.text_input_title)
            binding.saveEditedPost.setImageResource(R.drawable.send)
        } else {
            binding.title.text = getResources().getString(R.string.text_input_title_edit)
            binding.saveEditedPost.setImageResource(R.drawable.bx_save)
            binding.content.setText(incomingText)
        }

        binding.saveEditedPost.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isBlank()) {
                setResult(RESULT_CANCELED)
            } else {
                setResult(RESULT_OK, Intent().apply { putExtra(EXTRA_TEXT, text) })
            }
            finish()
        }

        binding.back.setOnClickListener{
            viewModel.cancelEdit()
            finish()
        }
    }
}

object NewPostContract: ActivityResultContract<String?,String?>() {
    override fun createIntent(context: Context, input: String?) =
        Intent(context, NewPostActivity::class.java).putExtra(Intent.EXTRA_TEXT, input)

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT)
        } else {
            null
        }
}