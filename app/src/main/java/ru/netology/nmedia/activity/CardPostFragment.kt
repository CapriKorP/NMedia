package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.EditPostFragment.Companion.longArg
import ru.netology.nmedia.activity.EditPostFragment.Companion.textArg
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.databinding.FragmentCardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel

class CardPostFragment : Fragment() {
    private val viewModel: PostViewModel by viewModels(
        ownerProducer = ::requireParentFragment
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = CardPostBinding.inflate(inflater, container, false)

        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == arguments?.longArg } ?: return@observe
            val holder = PostViewHolder(binding, object : OnInteractionListener {
                override fun onLike(post: Post) {
                    viewModel.like(post.id)
                }

                override fun onShare(post: Post) {
                    viewModel.share(post.id)
                }

                override fun onRemove(post: Post) {
                    viewModel.removeByID(post.id)
                    findNavController().navigateUp()
                }

                override fun onEdit(post: Post) {
                    viewModel.edit(post)
                    findNavController().navigate(
                        R.id.action_cardPostFragment_to_editPostFragment,
                        Bundle().apply {
                            textArg = post.content
                        })
                }

                override fun onPlayMedia(post: Post) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoURL))
                    if (intent.resolveActivity(requireContext().packageManager) != null) {
                        startActivity(intent)
                    }
                    startActivity(intent)
                    viewModel.playMedia(post.id)
                }

                override fun onOpenCardPost(post: Post) {
                }
            })
            holder.bind(post)
        }
        return binding.root
    }
}