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
import com.example.weatherapp.data.request.GetForeCastDTO
import com.example.weatherapp.data.request.GetHourlyWeatherDTO
import com.example.weatherapp.data.response.GetForeCastResponse
import com.example.weatherapp.data.response.HourlyWeatherResponse
import com.example.weatherapp.databinding.FragmentDetailBinding
import com.example.weatherapp.databinding.FragmentListBinding
import com.example.weatherapp.network.Status
import com.example.weatherapp.ui.adapters.DetailAdapter
import com.example.weatherapp.ui.adapters.ListAdapter
import com.example.weatherapp.ui.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var detailAdapter: DetailAdapter
    private  lateinit var binding : FragmentDetailBinding
    val viewModel: MainViewModel by viewModels()
    var date = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = arguments?.getString("date_EXTRA").toString()
        binding.tv3.text = date
        initRequest()
        setUpObserver()
    }

    //make API call
    private fun initRequest() {
        val req = GetHourlyWeatherDTO(
            latitude = 35.68,
            longitude = 139.76,
            hourly = "temperature_2m,precipitation",
            timezone = "Asia/Tokyo"
        )

        viewModel.getHourlyWeather(req)
        binding.progressBar.visibility = View.VISIBLE
    }



    //Create instance of adapter and populate it with data from API
    private fun initRecyclerView(data: HourlyWeatherResponse) {

        detailAdapter = DetailAdapter(data,date)
        binding.rv.apply {
            adapter = detailAdapter
            layoutManager = LinearLayoutManager(requireContext())

        }

    }

    //Observe API response
    private fun setUpObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getHourlyWeather.collectLatest {
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