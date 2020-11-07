package com.dev.numberslight.numbers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.numberslight.NumbersLightApplication
import com.dev.numberslight.databinding.FragmentListBinding
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.numbers.viewmodel.NumbersViewModel
import com.dev.numberslight.util.Resource
import javax.inject.Inject

class NumbersFragment : Fragment(), NumbersAdapter.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NumbersViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NumbersAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NumbersLightApplication).appComponent.listComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = NumbersAdapter(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NumbersViewModel::class.java)
        viewModel.getNumbers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNumber.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNumbers()
    }

    private fun observeNumbers() {
        viewModel.getNumbers().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> showLoading()
                is Resource.Done -> showData(it)
                is Resource.Error -> showError()
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.rvNumber.visibility = View.GONE
    }

    private fun showData(it: Resource.Done<out List<NumberLight>>) {
        binding.progressBar.visibility = View.GONE
        binding.rvNumber.visibility = View.VISIBLE
        adapter.setNumberLights(it.data)
    }

    private fun showError() {
        binding.progressBar.visibility = View.GONE
        binding.rvNumber.visibility = View.GONE
    }

    override fun onNumberClick(position: Int) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}