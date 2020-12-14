package cn.edu.weather

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import cn.edu.weather.weather.Forecast
import cn.edu.weather.weather.Weather
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity() {

    val baseURL = "http://t.weather.itboy.net/api/weather/city/"
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val cityCode = intent.getStringExtra("city_code")
        val queue = Volley.newRequestQueue(this)
        val stringRequest=StringRequest(baseURL+cityCode,{
            val gson=Gson()
            val WeatherType=object :TypeToken<Weather>(){}.type
            val weather=gson.fromJson<Weather>(it,WeatherType)
            textView_city.text=weather.cityInfo.city
            textView_province.text=weather.cityInfo.parent
            textView_shidu.text="湿度为:${weather.data.shidu}"
            textView_wendu.text="温度为:${weather.data.wendu}"
            textView_currenttime.text=weather.time
            val firstDay=weather.data.forecast.first()
            when(firstDay.type){
                "晴"->imageView.setImageResource(R.drawable.sun)
                "阴"->imageView.setImageResource(R.drawable.cloud)
                "多云"->imageView.setImageResource(R.drawable.mcloud)
                "小雨"->imageView.setImageResource(R.drawable.rain)
                "雷霆"->imageView.setImageResource(R.drawable.thunder)
                "雪"->imageView.setImageResource(R.drawable.snow)
                else ->imageView.setImageResource(R.drawable.thunder)

            }
            val adapter = ArrayAdapter<Forecast>(this, android.R.layout.simple_list_item_1, weather.data.forecast)
            listview.adapter = adapter
            Log.d("Main2Activity","${weather.cityInfo.city} ${weather.cityInfo.parent}")
        },{
            Log.d("Main2Activity","$it")
        })
        queue.add(stringRequest)
    }
}
