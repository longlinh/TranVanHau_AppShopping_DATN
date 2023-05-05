package com.example.appshoppingdatn.presentation.ui.fragment.location

import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.appshoppingdatn.R
import com.example.appshoppingdatn.databinding.FragmentLocationBinding
import com.example.appshoppingdatn.presentation.ui.base.fragment.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationFragment : BaseFragment<FragmentLocationBinding>(){
    var mMap : GoogleMap ?= null
    override fun getLayoutResId(): Int {
        return R.layout.fragment_location
    }

    override fun initControls(view: View, savedInstanceState: Bundle?) {
        onCLickBack()
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync { p0 ->
            mMap = p0
            val cs1 = LatLng(21.016930854424768, 105.7801066902156)
            val cs2 = LatLng(21.007294010716894, 105.73707127834186)
            val cs3 = LatLng(21.01899940938758, 105.78425749156816)
            mMap!!.addMarker(
                MarkerOptions().position(cs1).title("Cơ sở 1")
                    .snippet("CT4-Tòa Sông Đà-Mỹ Đình-Hà Nội")
                    .icon(BitmapDescriptorFactory.defaultMarker())
            )
            mMap!!.addMarker(
                MarkerOptions().position(cs2).title("Cơ sở 2")
                    .snippet("Tòa S202 Vinhome Smath City")
                    .icon(BitmapDescriptorFactory.defaultMarker())
            )
            mMap!!.addMarker(
                MarkerOptions().position(cs3).title("Cơ sở 3")
                    .snippet("Tòa Keangnam Landmark Tower")
                    .icon(BitmapDescriptorFactory.defaultMarker())
            )
            mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
            val cameraPosition = CameraPosition.Builder().target(cs1).zoom(15f).build()
            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }
    }

    private fun onCLickBack() {
        binding.imgBackMap.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }
}