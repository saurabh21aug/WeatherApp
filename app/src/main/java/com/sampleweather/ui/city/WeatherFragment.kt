package com.sampleweather.ui.city

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.sampleweather.R
import com.sampleweather.data.model.weather.WeatherData
import com.sampleweather.databinding.CityFragmentBinding
import com.sampleweather.utils.Status.*
import com.sampleweather.utils.hide

import com.sampleweather.utils.show
import com.sampleweather.utils.snackBar

import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
class WeatherFragment : Fragment() {

    private var _binding: CityFragmentBinding? = null
    private val binding get() = _binding!!

    private val args: WeatherFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    private val viewModel: WeatherViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CityFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        try {
            val pref = PreferenceManager.getDefaultSharedPreferences(requireContext())
            val unit = pref.getString("unit", "standard")

            val lat = args.lat
            val lng = args.lng
            val city = args.cityName
            viewModel.cityName = city.trim()

            setupObserver(lat, lng, unit!!)
        } catch (e: Exception) {

        }
    }

    private fun setupObserver(lat: String, lng: String, unit: String) {
        viewModel.getCityData(lat, lng, unit).observe(viewLifecycleOwner, {
            it?.let { resource ->
                when (resource.status) {
                    SUCCESS -> {
                        binding.progressBar.hide()
                        binding.mainContainer.show()
                        resource.data?.let { response -> updateUi(response, unit) }
                    }
                    ERROR -> {
                        binding.progressBar.hide()
                        binding.mainContainer.hide()
                        snackBar(binding.root, it.message.toString())
                    }
                    LOADING -> {
                        binding.mainContainer.hide()
                        binding.progressBar.show()
                    }
                }
            }
        })
    }


    @SuppressLint("SetTextI18n")
    private fun updateUi(response: WeatherData, unit: String) {

        with(binding) {
            val tempUnit = when (unit) {
                "metric" -> "°C"
                "imperial" -> "°F"
                else -> "°K"
            }

            address.text = viewModel.cityName
//            address.text = "${response.name}, ${response.sys.country}"
            updatedAt.text = requireActivity().getString(
                R.string.updated_time,
                convertDateTime("dd/MM/yyyy hh:mm a", response.dt)
            )
            status.text =
                response.weather[0].description.capitalize(Locale.getDefault())
            temp.text = response.main.temp.toString() + tempUnit
            tempMin.text = requireActivity().getString(
                R.string.min_temp,
                response.main.temp_min.toString(),
                tempUnit
            )
            tempMax.text = requireActivity().getString(
                R.string.max_temp,
                response.main.temp_max.toString(),
                tempUnit
            )

            sunrise.text = convertDateTime("hh:mm a", response.sys.sunrise)
            sunset.text = convertDateTime("hh:mm a", response.sys.sunset)
            wind.text = response.wind.speed.toString()

            pressure.text = response.main.pressure.toString()
            humidity.text = response.main.humidity.toString()

            val icon: String = response.weather[0].icon
            Glide.with(this@WeatherFragment)
                .load("http://openweathermap.org/img/w/$icon.png")
                .into(image)
        }

    }

    private fun convertDateTime(format: String, value: Long): String {
        return SimpleDateFormat(
            format,
            Locale.ENGLISH
        ).format(Date(value * 1000))
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val item: MenuItem? = menu.findItem(R.id.item_help)
        if (item != null) item.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}