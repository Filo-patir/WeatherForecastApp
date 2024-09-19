package filo.mamdouh.weatherforecast.features.home.rvadapters

import android.content.Context
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
import filo.mamdouh.weatherforecast.models.CurrentWeather
import filo.mamdouh.weatherforecast.models.ForecastItems
import java.time.Instant

class BaseRecyclerViewAdapter(val forecastItems: List<ForecastItems>,val timeZone: Int, val data: CurrentWeather) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        return when (viewType) {
            0 -> ForeCastViewHolder(parent)
            1 -> DetailsViewHolder(parent)
            2 -> CloudsWindSpeedViewHolder(parent)
            3 -> SunriseViewHolder(parent)
            else -> EmptyViewHolder(parent)
        }
    }

    override fun getItemCount(): Int {
        return 4
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (position) {
            0 -> {
                val viewHolder = holder as ForeCastViewHolder
                val adapter = ForeCastAdapter(forecastItems,timeZone, false)
                viewHolder.binding.childWeatherForecastRV.apply {
                    layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
                    this.adapter = adapter
                }
                viewHolder.binding.apply {
                    hourlyTextView.setOnClickListener{
                        buttonBackground.animate().translationX(0f)
                            adapter.updateList(forecastItems)
                        }
                    weeklyTextView.setOnClickListener{
                        buttonBackground.animate().translationX(500f)
                        adapter.updateList(forecastItems)
                    }
                }
            }
            1 -> {
                val viewHolder = holder as DetailsViewHolder

                viewHolder.binding.apply {
                    detailsRecyclerView.apply {
                        layoutManager = GridLayoutManager(context,2)
                        adapter = DetailsAdapter(data.main,data.visibility)
                    }
                }
            }
            2 -> {
                val viewHolder = holder as CloudsWindSpeedViewHolder
                viewHolder.binding.apply {
                    winSpeed.apply {
                        speed.text = data.wind.speed.toString()
                        circularProgressBar.animate().rotation(data.wind.deg.toFloat())
                        speedUnit.text = context.getString(R.string.meter_sec)
                    }
                    clouds.apply {
                        linearProgressIndicator.progress = data.clouds.all
                    }
                }
            }
            3 -> {
                val viewHolder = holder as SunriseViewHolder
                viewHolder.binding.apply {
                    sunriseTxt.text = data.sys.sunrise.toHourMinute(timeZone)
                    sunsetTxt.text = data.sys.sunset.toHourMinute(timeZone)
                    val dayLength = data.sys.sunset - data.sys.sunrise
                    val sunImgVal= ((Instant.now().epochSecond-data.sys.sunrise)/dayLength).toFloat().coerceIn(0f,1f) *180
                    val moonImgVal= ((Instant.now().epochSecond-data.sys.sunset)/dayLength).toFloat().coerceIn(0f,1f) *180
                    sunImg.animate().translationX((sunImgVal))
                    sunImg.animate().translationY((sunImgVal))
                    moonImg.animate().translationX(moonImgVal)
                    moonImg.animate().translationY(moonImgVal)
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

}