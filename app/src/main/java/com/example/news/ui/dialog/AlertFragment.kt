package com.example.news.ui.dialog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.news.R
import com.example.news.databinding.FragmentAlertBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlertFragment : DialogFragment() {

    private var _binding: FragmentAlertBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAlertBinding.inflate(layoutInflater, container, false)
        val window: Window? = dialog?.window
        window?.setBackgroundDrawableResource(R.color.transparent)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupArguments()
    }

    /** Método para obtener datos del bundle */
    private fun setupArguments() = arguments.let { bundle ->
        if ((bundle != null) && (bundle.containsKey("origen"))) {
            when(bundle.getString("origen")) {
                "save" -> { save() }
                "remove" -> { remove() }
            }
        }
    }

    /** Método para preparar vista favorito */
    private fun save() {
        binding.animationView.setAnimation(R.raw.like)
        binding.title.text = getString(R.string.favoritos)
        binding.message.text = getString(R.string.favoritos2)
        binding.materialButton2.setOnClickListener { onClick() }
    }

    /** Método para preparar vista eliminado */
    private fun remove() {
        binding.animationView.setAnimation(R.raw.trash)
        binding.title.text = getString(R.string.remove)
        binding.message.text = getString(R.string.remove2)
        binding.materialButton2.setOnClickListener { onClick() }
    }

    /** Método para enviar valor al bundle */
    private fun onClick() {
        val bundle = bundleOf("resultado" to true)
        setFragmentResult("requestKey", bundle)
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}