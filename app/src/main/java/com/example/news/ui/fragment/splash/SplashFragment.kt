package com.example.news.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.data.db.entities.News
import com.example.news.databinding.FragmentSplashBinding
import com.example.news.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSplashBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSplash()
    }

    private fun setupSplash() = lifecycleScope.launchWhenResumed {
        delay(3000)
        viewModel.news().observe(viewLifecycleOwner) { list ->
            if ((list != null) && (list.isNotEmpty())) {
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            } else {
                topPost()
            }
        }
    }

    private fun topPost() = viewModel.topPost(
        onSuccess = { response ->
            lifecycleScope.launchWhenResumed {
                response.articles.forEach {
                    val new = News(
                        author = it.author,
                        title = it.title,
                        description = it.description,
                        url = it.url,
                        urlToImage = it.urlToImage,
                        publishedAt = it.publishedAt,
                        content = it.content
                    )
                    viewModel.insert(new)
                }
                findNavController().navigate(R.id.action_splashFragment_to_homeFragment)
            }
        },
        onFailure = { code, message -> },
        onConnectionFailure = { message -> }
    )

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}