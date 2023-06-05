package me.taste2plate.app.customer.ui.location

import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.database.*
import com.maps.route.extensions.drawRouteOnMap
import com.maps.route.model.Route
import me.rozkmin.directions.Directions
import me.rozkmin.directions.DirectionsSdk
import me.taste2plate.app.customer.R
import me.taste2plate.app.customer.ui.WooDroidActivity
import me.taste2plate.app.customer.viewmodels.CustomerViewModel
import me.taste2plate.app.models.Driver
import me.taste2plate.app.models.order.Order


class TrackLocationActivity : WooDroidActivity<CustomerViewModel>(), OnMapReadyCallback{

    private var mCurrLocationMarker: Marker? = null
    private var mMap: GoogleMap? = null
    lateinit var order: Order
    override lateinit var viewModel: CustomerViewModel
    val route:ArrayList<Route> = ArrayList()
    private lateinit var database: DatabaseReference
    val directions : Directions by lazy {
        DirectionsSdk("AIzaSyAj0qAw3RL4AhbO3ly22EFMqThPbm7dBT4")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_location)
        viewModel = getViewModel(CustomerViewModel::class.java)

        order = intent.getSerializableExtra("order")!! as Order

        if (!this::order.isInitialized) {
            showError("Invalid order id")
            finish()
        }
        setupMap()

        database = FirebaseDatabase.getInstance().reference
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        database.child("userlocation${order._id}").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                print(snapshot.toString())
                val latitude = (snapshot.value as HashMap<*, *>)["latitude"] as? Double
                val longitude = (snapshot.value as HashMap<*, *>)["longitude"] as? Double
                if(latitude!=null && longitude!=null) {
                    updateMap(Driver(lat = latitude.toString(), lng = longitude.toString()))
                    mMap!!.drawRouteOnMap(
                        "AIzaSyAj0qAw3RL4AhbO3ly22EFMqThPbm7dBT4", //your API key
                        source = LatLng(latitude, longitude),
                        markers = false,
                        destination = LatLng(
                            order.address!!.position.coordinates[0],
                            order.address!!.position.coordinates[1]
                        ),
                        context = this@TrackLocationActivity
                    )
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    private fun updateMap(data: Driver?) {

        mMap?.let {
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker!!.remove()
            }
            val latLng = data!!.lat?.toDouble()?.let { it1 ->
                data.lng?.toDouble()?.let { it2 ->
                    LatLng(
                        it1,
                        it2
                    )
                }
            }
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng!!)
            markerOptions.title("Current Position")
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.track_icon))
            mCurrLocationMarker = it.addMarker(markerOptions)

            //move map camera
            it.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            it.animateCamera(CameraUpdateFactory.zoomTo(16f))
        }
    }
}
