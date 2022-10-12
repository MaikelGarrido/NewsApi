package com.example.news.ui.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.news.databinding.FragmentHomeBinding
import com.example.news.ui.fragment.favorites.FavoriteNewsFragment
import com.example.news.ui.fragment.news.NewsFragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import java.lang.RuntimeException

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabLayout()
    }

    /** Método para preparar los TabLayout */
    private fun setupTabLayout() {
        val tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when(position) {
                0 -> tab.text = "Noticias"
                1 -> tab.text = "Noticias favoritas"
            }
        }
        binding.viewPager.adapter = MyTabsAdapter(requireActivity() as AppCompatActivity)
        tabLayoutMediator.attach()
    }

    /** Método para preparar los fragments de los Tabs */
    class MyTabsAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

        override fun getItemCount(): Int { return 2 }

        override fun createFragment(position: Int): Fragment {
            return when(position) {
                0 -> { NewsFragment() }
                1 -> { FavoriteNewsFragment() }
                else -> throw RuntimeException("Posición inválida.")
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}