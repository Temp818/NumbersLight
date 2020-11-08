package com.dev.numberslight.detail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dev.numberslight.NumbersLightApplication
import com.dev.numberslight.R
import com.dev.numberslight.databinding.FragmentDetailBinding
import com.dev.numberslight.detail.viewmodel.DetailViewModel
import com.dev.numberslight.model.Detail
import com.dev.numberslight.util.Resource
import com.squareup.picasso.Picasso
import javax.inject.Inject

class DetailFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: DetailViewModel

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var name: String? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as NumbersLightApplication).appComponent.detailComponent()
            .create()
            .inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailViewModel::class.java)
        arguments?.let {
            name = it.getString(ARG_NAME)
        }
        getDetail(name)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvInformation.visibility = if (name == null) View.VISIBLE else View.GONE
        binding.btnRetry.visibility = View.GONE
        binding.progressBar.visibility = View.GONE

        binding.btnRetry.setOnClickListener {
            getDetail(name)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeDetail()
    }

    fun getDetail(name: String?) {
        name?.let {
            viewModel.getDetail(it)
            this.name = it
        }
    }

    private fun observeDetail() {
        viewModel.detail.observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Loading -> showLoading()
                is Resource.Done -> showData(it)
                is Resource.Error -> showError()
            }
        })
    }

    private fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.tvDetail.visibility = View.GONE
        binding.ivImage.visibility = View.GONE
        binding.tvInformation.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE
    }

    private fun showData(it: Resource.Done<out Detail>) {
        binding.progressBar.visibility = View.GONE
        binding.tvDetail.visibility = View.VISIBLE
        binding.ivImage.visibility = View.VISIBLE
        binding.tvDetail.text = it.data.text
        binding.tvInformation.visibility = View.GONE
        binding.btnRetry.visibility = View.GONE

        Picasso.get().load(it.data.image)
            .placeholder(R.drawable.image_placeholder)
            .error(R.drawable.image_error)
            .into(binding.ivImage)
    }

    private fun showError() {
        binding.progressBar.visibility = View.GONE
        binding.tvDetail.visibility = View.GONE
        binding.ivImage.visibility = View.GONE
        binding.tvInformation.visibility = View.GONE
        binding.btnRetry.visibility = View.VISIBLE
    }

    override fun onDetach() {
        super.onDetach()
        name = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    
    companion object {
        private const val ARG_NAME = "NAME"
        const val TAG = "TAG_DETAIL"

        @JvmStatic
        fun newInstance(name: String?) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_NAME, name)
                }
            }
    }
}