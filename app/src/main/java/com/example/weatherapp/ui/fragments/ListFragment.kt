package com.example.weatherapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.network.Status
import com.example.weatherapp.data.request.GetForeCastDTO
import com.example.weatherapp.data.response.GetForeCastResponse
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.ui.adapters.ListAdapter
import com.example.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    val viewModel: MainViewModel by viewModels()
    private lateinit var listAdapter: ListAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRequest()
        setUpObserver()
    }

    //make API call
    private fun initRequest() {
        val req = GetForeCastDTO(
            latitude = 35.68,
            longitude = 139.76,
            daily = "weathercode,temperature_2m_max,temperature_2m_min",
            timezone = "Asia/Tokyo"
        )

        viewModel.getForeCast(req)
        binding.progressBar.visibility = View.VISIBLE
    }

    //Create instance of list adapter and populate it with data from API
    private fun initRecyclerView(data: GetForeCastResponse) {

        listAdapter = ListAdapter(data) {
            val bundle = Bundle()
            bundle.putString("date_EXTRA", it)
            findNavController().navigate(R.id.detailFragment, bundle)
        }
        binding.rv.apply {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

    }

    //Observe API response
    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getForeCast.collectLatest {
                when (it.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.visibility = View.GONE

                        it.data?.let { it1 -> initRecyclerView(it1) }

                    }

                    Status.ERROR -> {
                        binding.progressBar.visibility = View.GONE

                    }

                    Status.LOADING -> {

                    }
                }
            }
        }


    }


}