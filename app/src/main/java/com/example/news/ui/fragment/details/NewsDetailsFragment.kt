package com.example.news.ui.fragment.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.news.R
import com.example.news.data.db.entities.News
import com.example.news.databinding.FragmentNewsDetailsBinding
import com.example.news.ui.dialog.AlertFragment
import com.example.news.utils.WebViewClient
import com.example.news.utils.createProgress
import com.example.news.utils.finishProgress
import com.example.news.viewmodel.ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsDetailsFragment : Fragment() {

    private var _binding: FragmentNewsDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ViewModel by viewModels()

    private lateinit var news: News

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewsDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArguments()
        setupOnClick()
        setupOnBackPressed()
    }

    /** Método para obtener datos del bundle */
    private fun setupArguments() = arguments.let { bundle ->
        if ((bundle != null) && (bundle.containsKey("news"))) {
            if (bundle.getParcelable<News>("news") != null) {
                news = bundle.getParcelable("news")!!
                news.url?.let {
                    createProgress(requireContext())
                    binding.postWebView.webViewClient = WebViewClient
                    binding.postWebView.loadUrl(it)
                }
            }
        }
    }

    /** Método para escuchar las opciones del menú */
    private fun setupOnClick() = binding.topAppBar.setOnMenuItemClickListener {
        when (it.itemId) {
            R.id.save -> { save() }
            R.id.remove -> { remove()  }
        }
        true
    }

    /** Método para guardar en favoritos */
    private fun save() { viewModel.favorite(news.id, true); showDialog("save") }

    /** Método para remover de la DB */
    private fun remove() { viewModel.delete(news.id); showDialog("remove") }

    /** Método para mostrar un DialogFragment */
    private fun showDialog(origen: String) {
        val dialog = AlertFragment()
        val bundle = Bundle()
        bundle.putString("origen", origen)
        dialog.isCancelable = false
        dialog.arguments = bundle
        dialog.show(parentFragmentManager, "")
        dialog.setFragmentResultListener("requestKey") { key, bundle ->
            val result = bundle.getBoolean("resultado")
            if (result) {
                lifecycleScope.launchWhenResumed {
                    findNavController().navigate(R.id.action_newsDetailsFragment_to_homeFragment)
                }
            }
        }
    }

    /** Método para regresar */
    private fun setupOnBackPressed() = binding.topAppBar.setNavigationOnClickListener { findNavController().popBackStack() }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}