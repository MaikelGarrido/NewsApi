package com.example.news.ui.fragment.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.data.db.entities.News
import com.example.news.databinding.FragmentNewsBinding
import com.example.news.databinding.ItemNewsBinding
import com.example.news.ui.fragment.news.adapter.NewsAdapter
import com.example.news.utils.OnClick
import com.example.news.utils.likeAnimation
import com.example.news.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsFragment : Fragment(), OnClick {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!

    private val newsAdapter: NewsAdapter by lazy { NewsAdapter(this) }
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupNews()
    }

    private fun setupRecyclerView() = binding.rvNews.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = newsAdapter
    }

    private fun setupNews() = lifecycleScope.launchWhenResumed {
        viewModel.news().observe(viewLifecycleOwner) { list ->
            if ((list != null) && (list.isNotEmpty())) {
                binding.lottieAnimationView.visibility = View.GONE
                binding.text.visibility = View.GONE
                binding.rvNews.visibility = View.VISIBLE
                newsAdapter.submitList(list)
            } else {
                binding.rvNews.visibility = View.GONE
                binding.lottieAnimationView.visibility = View.VISIBLE
                binding.text.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(news: News) {
        val bundle = Bundle()
        bundle.putParcelable("news", news)
        findNavController().navigate(R.id.action_homeFragment_to_newsDetailsFragment, bundle)
    }

    override fun onClickFavorite(news: News, viewBinding: ItemNewsBinding) {
        if (news.favorite) {
            likeAnimation(viewBinding.like,R.raw.like,false)
            viewModel.favorite(news.id, false)
        } else {
            likeAnimation(viewBinding.like,R.raw.like,true)
            viewModel.favorite(news.id, true)
        }
    }

}