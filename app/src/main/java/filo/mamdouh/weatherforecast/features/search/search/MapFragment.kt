package filo.mamdouh.weatherforecast.features.search.search

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.SearchLocationContract
import filo.mamdouh.weatherforecast.databinding.FragmentMapBinding
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.features.dialogs.LocationConfirmationDialog
import filo.mamdouh.weatherforecast.models.Location
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment() , SearchLocationContract.Listener{

    private lateinit var binding: FragmentMapBinding
    private lateinit var mMap: MapView
    private lateinit var marker: Marker

    private val viewModel by hiltNavGraphViewModels<MapViewModel>(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().load(
            context,
            activity?.getSharedPreferences(getString(filo.mamdouh.weatherforecast.R.string.app_name), MODE_PRIVATE)
        )
        mMap = binding.osmmap
        binding.editTextText.setOnKeyListener{ v, keyCode, event -> onSearchListener(v, keyCode, event)}
        mapSetup()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.locationList.collectLatest {
                    when(it){
                        is NetworkResponse.Failure -> {Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()}
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            val data = it.data as Location
                            if (data.isEmpty())
                                mMap.overlays.remove(marker)
                            else setMarker(GeoPoint(data[0].lat, data[0].lon))
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        mMap.onResume() //needed for compass, my location overlays, v6.0.0 and up
    }

    override fun onPause() {
        super.onPause()
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        mMap.onPause()  //needed for compass, my location overlays, v6.0.0 and up
    }

    private fun mapSetup(){
        marker = Marker(mMap)
        marker.setOnMarkerClickListener { marker, mapView ->
                LocationConfirmationDialog.showDialog(
                    requireContext(),
                    this@MapFragment,
                    marker?.position.toString()
                )
                false
            }
        val mMyLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(context), mMap)
        mMap.apply {
            zoomController.apply {
                minZoomLevel = 5.0
                setVisibility(CustomZoomButtonsController.Visibility.NEVER)
            }
            overlays.add(marker)
            setTileSource(TileSourceFactory.MAPNIK)
            overlays.add(mMyLocationOverlay)
            overlays.add(RotationGestureOverlay(this))
            setMultiTouchControls(true)
            controller.setZoom(15.0)
            controller.setCenter(GeoPoint(31.199004, 29.894378))
            mMyLocationOverlay.enableMyLocation()
            mMyLocationOverlay.isDrawAccuracyEnabled = true
            mMyLocationOverlay.runOnFirstFix {
                lifecycleScope.launch (Dispatchers.Main) {
                    controller.setCenter(mMyLocationOverlay.myLocation)
                    controller.animateTo(mMyLocationOverlay.myLocation)
                }
            }
            overlays.add(MapEventsOverlay(object: MapEventsReceiver {
                override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                    return false
                }

                override fun longPressHelper(p: GeoPoint?): Boolean {
                    if (p != null) {
                        setMarker(p)
                    }
                    return false
                }

            }))
        }
    }

    override fun onSearchLocationClicked() {
        Log.d("Filo", "onSearchLocationClicked: ")
    }

    fun setMarker(position: GeoPoint){
        mMap.overlays.remove(marker)
        marker.position = position
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mMap.controller.setCenter(position)
        mMap.controller.animateTo(position)
        mMap.overlays.add(marker)
        LocationConfirmationDialog.showDialog(requireContext(),this@MapFragment, position.toString())
    }

    private fun onSearchListener(view: View,keyCode: Int, event: KeyEvent) : Boolean{
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            viewModel.getData(binding.editTextText.text.toString())
            return true
        } else {
            return false
        }
    }
}