package filo.mamdouh.weatherforecast.features.favourite.rvadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.CloudsSpeedItemBinding
import filo.mamdouh.weatherforecast.databinding.DetailsRecyclerViewBinding
import filo.mamdouh.weatherforecast.databinding.ParentWeatherForecastItemBinding
import filo.mamdouh.weatherforecast.databinding.SunMoonRiseItemBinding
import filo.mamdouh.weatherforecast.logic.toHourMinute
import filo.mamdouh.weatherforecast.models.CachedData
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

class HomeBaseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    companion object {
        const val VIEW_TYPE_ITEM_1 = 0
        const val VIEW_TYPE_ITEM_2 = 1
        const val VIEW_TYPE_ITEM_3 = 2
        const val VIEW_TYPE_ITEM_4 = 3
    }
    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TYPE_ITEM_1
            1 -> VIEW_TYPE_ITEM_2
            2 -> VIEW_TYPE_ITEM_3
            3 -> VIEW_TYPE_ITEM_4
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
    private var data: List<CachedData> = emptyList()
        fun setForecastItems(data: List<CachedData>) {
            this.data = data
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            0 -> ForeCastViewHolder(LayoutInflater.from(context).inflate(R.layout.parent_weather_forecast_item, parent, false))
            1 -> DetailsViewHolder(LayoutInflater.from(context).inflate(R.layout.details_recycler_view, parent, false))
            2 -> CloudsWindSpeedViewHolder(LayoutInflater.from(context).inflate(R.layout.clouds_speed_item, parent, false))
            3 -> SunriseViewHolder(LayoutInflater.from(context).inflate(R.layout.sun_moon_rise_item, parent, false))
            else -> EmptyViewHolder(parent)
        }
    }
    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val now = getClosedTime(data) ?: return
        when (position) {
            0 -> {
                val viewHolder = holder as ForeCastViewHolder
                val adapter = HomeForeCastAdapter(getDayList(data) , data[0].timeZone,true)
                viewHolder.binding.childWeatherForecastRV.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
                    this.adapter = adapter
                }
                viewHolder.binding.apply {
                    hourlyTextView.setOnClickListener{
                        buttonBackground2.animate().translationX(0f)
                            adapter.updateList(getDayList(data),true)
                        }
                    weeklyTextView.setOnClickListener{
                        buttonBackground2.animate().translationX(500f)
                        adapter.updateList(getWeekList(data),false)
                    }
                }
            }
            1 -> {
                val viewHolder = holder as DetailsViewHolder
                viewHolder.binding.apply {
                    detailsRecyclerView.apply {
                        layoutManager = GridLayoutManager(context,2)
                        adapter = HomeDetailsAdapter(now.main,now.visibility)
                    }
                }
            }
            2 -> {
                val viewHolder = holder as CloudsWindSpeedViewHolder
                viewHolder.binding.apply {
                    winSpeed.apply {
                        speed.text = now.wind.speed.toString()
                        circularProgressBar.animate().rotation(now.wind.deg.toFloat())
                        speedUnit.text = context.getString(R.string.meter_sec)
                    }
                    clouds.apply {
                        textView9.text = buildString {
                            append(now.clouds.all.toString())
                            append(context.getString(R.string.percentage))
                        }
                        linearProgressIndicator.progress = now.clouds.all
                    }
                }
            }
            3 -> {
                val viewHolder = holder as SunriseViewHolder
                viewHolder.binding.apply {
                    sunriseTxt.text = now.sys.sunrise.toHourMinute(now.timeZone)
                    sunsetTxt.text = now.sys.sunset.toHourMinute(now.timeZone)
                    val dayLength = now.sys.sunset - now.sys.sunrise
                    if(dayLength>0) {
                        val sunriseDifference = Instant.now().epochSecond - now.sys.sunrise
                        val sunImgVal = (sunriseDifference / dayLength.toFloat()) * 180
                        imageView5.animate().rotation(sunImgVal).setDuration(1000)
                    }
                    }
                }
            }
        }

    class ForeCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = ParentWeatherForecastItemBinding.bind(itemView)
    }

    class DetailsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = DetailsRecyclerViewBinding.bind(itemView)
    }
    class CloudsWindSpeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = CloudsSpeedItemBinding.bind(itemView)
    }
    class SunriseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = SunMoonRiseItemBinding.bind(itemView)
    }
    class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    private fun getDayList(it: List<CachedData>): List<CachedData> {
        return it.filter { item -> Instant.ofEpochSecond(item.key.dt).atZone(ZoneId.systemDefault()).toLocalDate() == LocalDate.now() }
    }

    private fun getWeekList(it: List<CachedData>): List<CachedData> {
        val dailyTimestamps = mutableMapOf<LocalDate, CachedData>()
        // Iterate through the timestamps
        for (timestamp in it) {
            val date = Instant.ofEpochSecond(timestamp.key.dt).atZone(ZoneId.systemDefault()).toLocalDate()
            // Only add the timestamp if it's not already in the map
            if (!dailyTimestamps.containsKey(date)) {
                dailyTimestamps[date] = timestamp
            }
        }
        // Get the values (timestamps) from the map
        return dailyTimestamps.values.toList()
    }
    private fun getClosedTime(list: List<CachedData>): CachedData?{
        val now = Instant.now().epochSecond
        // Find the closest timestamp
        return list.minByOrNull{ kotlin.math.abs(it.key.dt- now) }
    }
}