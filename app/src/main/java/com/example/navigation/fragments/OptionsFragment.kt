package com.example.navigation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.navigation.R
import com.example.navigation.databinding.FragmentOptionsBinding
import com.example.navigation.models.Options
import kotlin.properties.Delegates

class OptionsFragment : BaseFragment("OptionsFragment", R.layout.fragment_options) {
    private var _binding: FragmentOptionsBinding? = null
    private val binding get() = _binding!!
    private var options: Options = Options.DEFAULT
    private var spinnerValues = listOf(2, 3, 4, 5, 6)
    private var fistsCount by Delegates.notNull<Int>()
    private lateinit var adapter: ArrayAdapter<Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fistsCount = it.getInt("fistsCount", Options.DEFAULT.fistCount)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinner()

        binding.okButton.setOnClickListener {
            val action =
                OptionsFragmentDirections.actionOptionsFragmentToMenuFragment(options.fistCount)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupSpinner() = with(binding) {
        adapter = ArrayAdapter(
            this@OptionsFragment.requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            spinnerValues
        )
        fistsCountSpinner.adapter = adapter
        fistsCountSpinner.setSelection(spinnerValues.indexOfFirst { it == fistsCount })

        fistsCountSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val count = spinnerValues[position]
                options = options.copy(fistCount = count)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}