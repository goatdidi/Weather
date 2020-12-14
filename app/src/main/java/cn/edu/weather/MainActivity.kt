package cn.edu.weather

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.io.BufferedReader
import java.io.InputStreamReader
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        thread {
            val str = readFileFromRaw(R.raw.citycode)
            val gson = Gson()
            val CityType = object : TypeToken<List<CityItem>>() {}.type
            var cities: List<CityItem> = gson.fromJson(str, CityType)
            cities = cities.filter { it.city_code != "" }
            runOnUiThread {
                val adapter = ArrayAdapter<CityItem>(this, android.R.layout.simple_list_item_1, cities)
                listView.adapter = adapter
                listView.setOnItemClickListener { _, _, i, _ ->
                    val cityCode =cities[i].city_code
                    val intent=Intent(this,Main2Activity::class.java)
                    intent.putExtra("city_code",cityCode)
                    startActivity(intent)
                }
            }
            Log.d("MainActivity", "$cities")

        }
    }
    fun readFileFromRaw(rawName: Int): String? {
        try {
            val inputReader = InputStreamReader(resources.openRawResource(rawName))
            val bufReader = BufferedReader(inputReader)
            var line: String? = ""
            var result: String? = ""
            while (bufReader.readLine().also({ line = it }) != null) {
                result += line
            }
            return result
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}
