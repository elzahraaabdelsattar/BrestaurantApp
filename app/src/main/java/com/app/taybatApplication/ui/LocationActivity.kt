package com.app.taybatApplication.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.CityModel
import com.app.taybatApplication.data.model.ResponseDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.ui.check.CheckActivity
import com.app.taybatApplication.util.hide
import com.app.taybatApplication.util.setSafeOnClickListener
import com.app.taybatApplication.util.show
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    val list = mutableListOf<String>()
    var cityId:String=""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        //setToolBar
        location_progressBar.show()
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="اختر المنطقه"


        //setMap
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        //////

        setLocation()

        continue_locationActivity_button.setSafeOnClickListener {
            val intent = Intent(this,CheckActivity::class.java)
            intent.putExtra("flag","delivery")
            startActivity(intent)
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun setLocation() {
        val prefManager= PrefManager(this)
        val token="Bearer".plus(prefManager.retrieveString(TOKEN))

        RetrofitClient.instance.getCities(token).enqueue(object:Callback<ResponseDto<MutableList<CityModel>?>?>{
            override fun onFailure(
                call: Call<ResponseDto<MutableList<CityModel>?>?>,
                t: Throwable
            ) {
                Toast.makeText(this@LocationActivity,t.message.toString(),Toast.LENGTH_LONG).show()
            }

            override fun onResponse(
                call: Call<ResponseDto<MutableList<CityModel>?>?>,
                response: Response<ResponseDto<MutableList<CityModel>?>?>
            ) {

                if (response.code()==200)
                {
                    if (response.isSuccessful)
                    {
                        // list.add("اختر مدينتك")
                        for (i in 0 until response.body()!!.data!!.size)
                        {
                            list.add(response.body()!!.data!![i].name)
                        }

                        if (number_picker_location != null) {
                            number_picker_location.minValue = 1
                            number_picker_location.maxValue = list.size
                            number_picker_location.displayedValues=list.toTypedArray()
                            number_picker_location.wrapSelectorWheel = true


                            number_picker_location.setOnValueChangedListener { picker, oldVal, newVal ->
                                cityId = newVal.toString()
                                prefManager.saveString("city_id",cityId)
                            }
                        }

                    }
                }
                else
                {
                    Toast.makeText(this@LocationActivity,"Error In Data",Toast.LENGTH_LONG).show()
                }
            }
        })
    }
    override fun onMapReady(googleMap: GoogleMap) {
        val map = googleMap

        val latitude = 24.44088234455447
        val longitude = 39.621286564114406
        val zoomLevel = 10f

        val homeLatLng = LatLng(latitude, longitude)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(homeLatLng, zoomLevel))

        location_progressBar.hide()
        setMapLongClick(map)
    }

    private fun setMapLongClick(map: GoogleMap) {

        val prefManager= PrefManager(this)
        map.setOnMapLongClickListener { latLng ->
            prefManager.saveString("longitude",latLng.longitude.toString())
            prefManager.saveString("latitude",latLng.latitude.toString())

            map.clear()

            map.addMarker(
                MarkerOptions()
                    .position(latLng)

            )


        }

    }
}