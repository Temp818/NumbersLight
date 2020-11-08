package com.dev.numberslight.numbers

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.numberslight.MainActivity
import com.dev.numberslight.NumbersLightApplication
import com.dev.numberslight.databinding.FragmentListBinding
import com.dev.numberslight.model.NumberLight
import com.dev.numberslight.numbers.viewmodel.NumbersViewModel
import com.dev.numberslight.util.Resource
import com.squareup.haha.perflib.Main
import javax.inject.Inject

class NumbersFragment : Fragment(), NumbersAdapter.Listener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: NumbersViewModel

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: NumbersAdapter

    private lateinit var listener: Listener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NumbersLightApplication).appComponent.listComponent()
            .create()
            .inject(this)
        listener = context as Listener
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
        binding.btnRetry.setOnClickListener {
            viewModel.getNumbers()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNumbers()
    }

    private fun observeNumbers() {
        viewModel.numbers.observe(viewLifecycleOwner, {
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
        binding.btnRetry.visibility = View.GONE
    }

    private fun showData(it: Resource.Done<out List<NumberLight>>) {
        binding.progressBar.visibility = View.GONE
        binding.rvNumber.visibility = View.VISIBLE
        binding.btnRetry.visibility = View.GONE
        adapter.setNumberLights(it.data, listener.shouldHighlight())
    }

    private fun showError() {
        binding.progressBar.visibility = View.GONE
        binding.rvNumber.visibility = View.GONE
        binding.btnRetry.visibility = View.VISIBLE
    }

    override fun onNumberClick(number: NumberLight) {
        listener.onNumberClick(number)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "TAG_NUMBER"
    }

    interface Listener {
        fun onNumberClick(number: NumberLight)
        fun shouldHighlight(): Boolean
    }
}