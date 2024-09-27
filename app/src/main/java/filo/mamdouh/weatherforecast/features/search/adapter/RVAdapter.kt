package filo.mamdouh.weatherforecast.features.search.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.SearchLocationContract
import filo.mamdouh.weatherforecast.databinding.SavedLocationItemBinding
import filo.mamdouh.weatherforecast.logic.toDrawable
import filo.mamdouh.weatherforecast.models.CurrentWeather

class RVAdapter(private val listener: SearchLocationContract.View) : ListAdapter<CurrentWeather, RVAdapter.ViewHolder>(
    SavedLocationDiffUtils()
) {
    private lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.saved_location_item, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            val item = getItem(position)
            locationTxt.text = buildString {
                append(item.name)
                append(", ")
                append(item.sys.country)
            }
            mainTemp.text = item.main.temp.toString()
            iconImg.setImageResource(item.weather[0].icon.toDrawable())
            highLowValue.text = String.format(context.getString(R.string.high_and_low), item.main.temp_max, item.main.temp_min)
            deleteBtn.setOnClickListener {
//                listener.onDeleteClicked()
            }

        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = SavedLocationItemBinding.bind(itemView)
    }
}