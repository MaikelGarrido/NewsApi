package com.example.news.ui.fragment.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.news.R
import com.example.news.databinding.FragmentFavoriteNewsBinding
import com.example.news.ui.fragment.favorites.adapter.FavoriteNewsAdapter
import com.example.news.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FavoriteNewsFragment : Fragment() {

    private var _binding : FragmentFavoriteNewsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by viewModels()
    private val favoriteNewsAdapter: FavoriteNewsAdapter by lazy { FavoriteNewsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteNewsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupFavoriteNews()
    }

    /** Método para preparar el recyclerview */
    private fun setupRecyclerView() = binding.rvNews.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = favoriteNewsAdapter
    }

    /** Método para preparar verificar lista y setear en el adapter */
    private fun setupFavoriteNews() = viewModel.newsFavorite().observe(viewLifecycleOwner) { list ->
        if ((list != null) && (list.isNotEmpty())) {
            binding.lottieAnimationView.visibility = View.GONE
            binding.text.visibility = View.GONE
            binding.rvNews.visibility = View.VISIBLE
            favoriteNewsAdapter.submitList(list)
        } else {
            binding.rvNews.visibility = View.GONE
            binding.lottieAnimationView.visibility = View.VISIBLE
            binding.text.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}