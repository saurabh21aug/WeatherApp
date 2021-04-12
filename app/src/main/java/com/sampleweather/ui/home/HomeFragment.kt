package com.sampleweather.ui.home

import android.location.Geocoder
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.sampleweather.R
import com.sampleweather.data.model.Location
import com.sampleweather.databinding.HomeFragmentBinding
import com.sampleweather.ui.adapter.CityListAdapter
import com.sampleweather.utils.hide
import com.sampleweather.utils.show
import com.sampleweather.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale.getDefault

@AndroidEntryPoint
class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: HomeFragmentBinding? = null
    private val binding: HomeFragmentBinding
        get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var cityListAdapter: CityListAdapter

    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initUi()
        setupObserver()

        binding.btnSave.setOnClickListener {
            try {
                if (!viewModel.addresses.isNullOrEmpty()) {
                    viewModel.insert(
                        Location(
                            0,
                            viewModel.addresses[0].getAddressLine(0),
                            viewModel.addresses[0].latitude.toString(),
                            viewModel.addresses[0].longitude.toString()
                        )
                    )
                } else
                    requireActivity().toast("City data not found")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun initUi() {
        mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        with(binding) {
            recyclerView.apply {
                recyclerView.layoutManager = LinearLayoutManager(context)
                cityListAdapter = CityListAdapter(viewModel)
                adapter = cityListAdapter
            }
        }
    }

    private fun setupObserver() {
        viewModel.subscribers.observe(viewLifecycleOwner, {
            if (it.isEmpty()) {

                binding.recyclerView.hide()
                binding.txtEmpty.show()
            } else {
                binding.recyclerView.show()
                binding.txtEmpty.hide()
                cityListAdapter.setList(it)
                cityListAdapter.notifyDataSetChanged()
            }
        })
    }



    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap!!

        mMap.setOnCameraIdleListener {
            val lat: Double = mMap.cameraPosition.target.latitude
            val lng: Double = mMap.cameraPosition.target.longitude

            try {
                val geoCoder = Geocoder(requireContext(), getDefault())
                viewModel.addresses = geoCoder.getFromLocation(lat, lng, 1)
                if (!viewModel.addresses.isNullOrEmpty()) {
                    println(viewModel.addresses[0])
                    binding.txtLatlong.text = viewModel.addresses[0].getAddressLine(0)
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item: MenuItem? = menu.findItem(R.id.item_setting)
        if (item != null) item.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}