package com.app.taybatApplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.taybatApplication.R
import com.app.taybatApplication.data.local.PrefManager
import com.app.taybatApplication.data.local.TOKEN
import com.app.taybatApplication.data.model.Branch
import com.app.taybatApplication.data.model.BranchDto
import com.app.taybatApplication.data.remote.RetrofitClient
import com.app.taybatApplication.util.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_about_us.*
import kotlinx.android.synthetic.main.activity_branches.*
import kotlinx.android.synthetic.main.content_branches.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.android.synthetic.main.content_main.tool_bar
import kotlinx.android.synthetic.main.tool_bar_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BranchesActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    private val linearLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this)
    }


    //private lateinit var branchesList:List<Branch>
    private lateinit var recyclerView: RecyclerView
    private lateinit var mapAdapter: RecyclerView.Adapter<MapAdapter.ViewHolder>

    private val recycleListener = RecyclerView.RecyclerListener { holder ->
        val mapHolder = holder as MapAdapter.ViewHolder
        mapHolder.clearView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_branches)



        init()
        setUpMapView()

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun init() {
        ////
        setSupportActionBar(tool_bar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        tool_bar.title = ""
        title_main_toolBar.text="الفروع"
        toggle= ActionBarDrawerToggle(this,drawer_layout_banches,R.string.open,R.string.close)
        drawer_layout_banches.addDrawerListener(toggle)
        toggle.syncState()
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white_color));
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        nav_view_branches.setNavigationItemSelectedListener(this);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
        {
            return true
        }
        return super.onOptionsItemSelected(item)
    }



    private fun setUpMapView() {

        branches_progressBar.show()

        val prefManager = PrefManager(this)
        val token = "Bearer".plus(prefManager.retrieveString(TOKEN))
        RetrofitClient.instance.getAllBranches(token)
            .enqueue(object : Callback<BranchDto> {
                override fun onFailure(call: Call<BranchDto>, t: Throwable) {
                    branches_progressBar.hide()
                    Toast.makeText(this@BranchesActivity,t.message.toString(),Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<BranchDto>, response: Response<BranchDto>) {
                    if (response.code()==200)
                    {
                        if (response.isSuccessful)
                        {
                            branches_progressBar.hide()
                           val branchesList=response.body()!!.branch
                            mapAdapter = MapAdapter(branchesList)

                            // Initialise the RecyclerView.
                            recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                                setHasFixedSize(true)
                                layoutManager = linearLayoutManager
                                adapter = mapAdapter
                                setRecyclerListener(recycleListener)
                            }

                        }
                        else{

                            branches_progressBar.hide()
                            Toast.makeText(this@BranchesActivity,"error in data",Toast.LENGTH_LONG).show()

                        }
                    }
                }
            })
    }


    inner class MapAdapter(private var branchesData:List<Branch>) : RecyclerView.Adapter<MapAdapter.ViewHolder>() {

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindView(position)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val inflated = LayoutInflater.from(parent.context)
                .inflate(R.layout.items_branches, parent, false)
            return ViewHolder(inflated)
        }

        override fun getItemCount() = branchesData.size

        /** A view holder for the map and title. */
        inner class ViewHolder(view: View) :
            RecyclerView.ViewHolder(view),
            OnMapReadyCallback {

            private val layout: View = view
            private val mapView: MapView = layout.findViewById(R.id.lite_listrow_map)
            private val title: TextView = layout.findViewById(R.id.lite_listrow_text)
            private val address: TextView = layout.findViewById(R.id.lite_listrow_address)
            private lateinit var map: GoogleMap
            private lateinit var latLng: LatLng

            /** Initialises the MapView by calling its lifecycle methods */
            init {
                with(mapView) {
                    // Initialise the MapView
                    onCreate(null)
                    // Set the map ready callback to receive the GoogleMap object
                    getMapAsync(this@ViewHolder)
                }
            }

            private fun setMapLocation() {
                if (!::map.isInitialized) return
                with(map) {
                    moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 13f))
                    addMarker(MarkerOptions().position(latLng))
                    mapType = GoogleMap.MAP_TYPE_NORMAL

                }
            }

            override fun onMapReady(googleMap: GoogleMap?) {
                MapsInitializer.initialize(applicationContext)
                // If map is not initialised properly
                map = googleMap ?: return
                setMapLocation()
            }

            /** This function is called when the RecyclerView wants to bind the ViewHolder. */
            fun bindView(position: Int) {
                branchesData[position].let {
                    latLng = LatLng(it.latitude!!,it.longitude!!)
                    mapView.tag = this
                    title.text = it.name
                    address.text=it.address


                    setMapLocation()
                }
            }

            /** This function is called by the recycleListener, when we need to clear the map. */
            fun clearView() {
                with(map) {
                    // Clear the map and free up resources by changing the map type to none
                    clear()
                    mapType = GoogleMap.MAP_TYPE_NONE
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home->
            {
                navigateToFoodMenuActivity()
            }
            R.id.nav_bag->
            {
                navigateToBag()
            }
            R.id.nav_offers->
            {
                navigateToOffers()
            }
            R.id.nav_reservation->
            {
                navigateToReservation()
            }
            R.id.nav_myorders->
            {
                navigateToMyOrders()
            }
            R.id.nav_suggestion->
            {
                navigateToComplaint()
            }
            R.id.nav_review->
            {
                navigateToReviewActivity()
            }
            R.id.nav_about_us->
            {
                navigateToBoutUs()
            }
            R.id.nav_branches->
            {
                setUpMapView()
            }
            R.id.nav_logout->
            {
                ModelConveter.logOut(this)
            }

        }
        drawer_layout_banches.closeDrawer(GravityCompat.START)
        return true
    }

}