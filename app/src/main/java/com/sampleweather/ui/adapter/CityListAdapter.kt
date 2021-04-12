package com.sampleweather.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.sampleweather.data.model.Location
import com.sampleweather.databinding.ItemCityBinding
import com.sampleweather.ui.home.HomeFragmentDirections
import com.sampleweather.ui.home.HomeViewModel


class CityListAdapter(private val viewModel: HomeViewModel) :
    RecyclerView.Adapter<CityListAdapter.CityViewHolder>() {

    private val subscribersList = ArrayList<Location>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val currentItem = subscribersList[position]
        holder.bind(currentItem, viewModel)


    }


    override fun getItemCount() = subscribersList.size

    fun setList(locations: List<Location>) {
        subscribersList.clear()
        subscribersList.addAll(locations)
    }


    class CityViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            location: Location,
            viewModel: HomeViewModel,
        ) {
            binding.apply {

                txtCity.text = location.name

                binding.txtCity.setOnClickListener {
                    val action =
                        HomeFragmentDirections.actionHomeFragmentToCityFragment(
                            location.name,
                            location.lat,
                            location.lng
                        )
                    Navigation.findNavController(it).navigate(action)

                }
                binding.imgDelete.setOnClickListener {
                    viewModel.delete(location)
                }
            }
        }
    }
}


