package filo.mamdouh.weatherforecast.features.home.rvadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.TitleDetailsItemsBinding
import filo.mamdouh.weatherforecast.models.Main

class DetailsAdapter(val data: Main, val visibilty : Int) : RecyclerView.Adapter<DetailsAdapter.ViewHolder>(){
    lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.title_details_items,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            //Feels Like
            0 -> {
                holder.binding.apply {
                    title.text = context.getString(R.string.feels_like)
                    measure.text = buildString {
                        append(data.feels_like.toString())
                        append(context.getString(R.string.degree))
                    }
                    icon.setImageResource(R.drawable.icons8_temperature_64)
                }
            }
            //Humidity
            1 -> {
                holder.binding.apply {
                    title.text = context.getString(R.string.humidity)
                    measure.text = buildString {
                        append(data.humidity.toString())
                        append(context.getString(R.string.percentage))
                    }
                    icon.setImageResource(R.drawable.icons8_humidity_50)
                }
            }
            //Visibilty
            2 -> {
                holder.binding.apply {
                    title.text = context.getString(R.string.visibility)
                    measure.text = buildString {
                        append(visibilty.toString())
                        append(context.getString(R.string.meter))
                    }
                    icon.setImageResource(R.drawable.icons8_visibility_24)
                }
            }
            // Pressure
            3 -> {
                holder.binding.apply {
                    title.text = context.getString(R.string.pressure)
                    measure.text = buildString {
                        append(data.pressure.toString())
                        append(context.getString(R.string.pressure_unit))
                    }
                    icon.setImageResource(R.drawable.icons8_pressure_100)
                }
            }
        }
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = TitleDetailsItemsBinding.bind(itemView)
    }
}