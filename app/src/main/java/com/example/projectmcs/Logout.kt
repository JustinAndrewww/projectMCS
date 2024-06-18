package com.example.projectmcs

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmcs.databinding.ActivityLogoutBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class Logout : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setting onClickListener to navigate to LoginPage
        binding.textLogin.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        // Initializing map fragment and setting up the map
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync { googleMap ->
            val position = LatLng(-6.20201, 106.78113)
            val markerOptions = MarkerOptions()
                .position(position)
                .title("Puff and Poof")

            googleMap.addMarker(markerOptions)
            val cameraUpdate = CameraUpdateFactory.newLatLng(position)
            googleMap.moveCamera(cameraUpdate)
            googleMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f))
        }
    }
}
