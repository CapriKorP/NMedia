package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.util.StringArg
import ru.netology.nmedia.utils.longArg
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

    companion object{
        var Bundle.textArg: String? by StringArg
        var Bundle.longArg: Long by longArg
    }

    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
            val binding = FragmentNewPostBinding.inflate(
                inflater,
                container,
                false)

        binding.content.requestFocus()
        binding.saveEditedPost.setOnClickListener {
            viewModel.save(binding.content.text.toString())
            findNavController().navigateUp()
        }

        binding.back.setOnClickListener{
            viewModel.cancelEdit()
            findNavController().navigateUp()
        }
        return binding.root
    }
}