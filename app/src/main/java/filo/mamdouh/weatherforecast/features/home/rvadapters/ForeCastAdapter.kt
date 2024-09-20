package filo.mamdouh.weatherforecast.features.home.rvadapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.databinding.WeatherForecastItemBinding
import filo.mamdouh.weatherforecast.logic.toDay
import filo.mamdouh.weatherforecast.logic.toDrawable
import filo.mamdouh.weatherforecast.logic.toHour
import filo.mamdouh.weatherforecast.models.ForecastItems

class ForeCastAdapter(private var list: List<ForecastItems>,private val timeZone:Int , private var flag: Boolean) :
    RecyclerView.Adapter<ForeCastAdapter.ForeCastViewHolder>() {
    lateinit var context: Context
        fun updateList(list: List<ForecastItems>, flag: Boolean){
            this.list = list
            this.flag = flag
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForeCastViewHolder {
        context = parent.context
        val binding = LayoutInflater.from(parent.context).inflate(R.layout.weather_forecast_item,parent,false)
        return ForeCastViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ForeCastViewHolder, position: Int) {
            if (flag){
                list[position].apply {
                    holder.binding.apply {
                        dayTxt.animate().translationY(0f)
                        imageView.animate().translationY(0f)
                        dayTxt.text = dt.toHour(timeZone)
                        imageView.setImageResource(weather[0].icon.toDrawable())
                        if (weather[0].icon.contains("d"))
                        itemLayout.background = AppCompatResources.getDrawable(context,R.drawable.sunny_background)
                        else itemLayout.background = AppCompatResources.getDrawable(context,R.drawable.night_background)
                        weatherTxt.text = buildString {
                            append(main.temp.toString() + " ")
                            append(context.getString(R.string.degree))
                        }
                    }
                }
            }
            else{
                list[position].apply {
                    holder.binding.apply {
                        dayTxt.animate().translationY(-200f)
                        imageView.animate().translationY(100f)
                        dayTxt.text = dt.toDay(timeZone)
                        imageView.setImageResource(weather[0].icon.toDrawable())
                        weatherTxt.text = buildString {
                            append(main.temp.toString() + " ")
                            append(context.getString(R.string.degree))
                        }
                    }
                }
            }
        }
    class ForeCastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding = WeatherForecastItemBinding.bind(itemView)
    }
}