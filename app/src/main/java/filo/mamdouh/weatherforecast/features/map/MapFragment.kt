package filo.mamdouh.weatherforecast.features.map

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import filo.mamdouh.weatherforecast.R
import filo.mamdouh.weatherforecast.contracts.SearchLocationContract
import filo.mamdouh.weatherforecast.databinding.FragmentMapBinding
import filo.mamdouh.weatherforecast.datastorage.network.NetworkResponse
import filo.mamdouh.weatherforecast.features.dialogs.LocationConfirmationDialog
import filo.mamdouh.weatherforecast.logic.toLocationItem
import filo.mamdouh.weatherforecast.models.Location
import filo.mamdouh.weatherforecast.models.LocationItem
import filo.mamdouh.weatherforecast.models.SearchRoot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private var location: LocationItem? = null
    private lateinit var suggestions: ArrayAdapter<String>
    private val searchQueryFlow = MutableSharedFlow<String>()

    private val viewModel by hiltNavGraphViewModels<MapViewModel>(R.id.nav_graph)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    @OptIn(FlowPreview::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Configuration.getInstance().load(
            context,
            activity?.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE)
        )
        mMap = binding.osmmap
        suggestions = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, emptyList())
        binding.editTextText.apply {
            setOnKeyListener{ _, keyCode, event -> onSearchListener(keyCode, event)}
            addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    lifecycleScope.launch {
                        searchQueryFlow.emit(s.toString())
                    }
                }

            })
            threshold = 2
            setAdapter(suggestions)
           showSoftInputOnFocus = true
        }
        lifecycleScope.launch {
            searchQueryFlow.debounce(1000).distinctUntilChanged().collect{
                Log.d("Filo", "onViewCreated: $it")
                if(it.isNotEmpty())
                    viewModel.getSuggestions(it)
            }
        }
        setSuggestionListener()
        mapSetup()
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.locationList.collectLatest {
                    when(it){
                        is NetworkResponse.Failure -> {Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()}
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> onSuccess(it.data)
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
        mMap.overlays.remove(marker)
        mMap.onPause()  //needed for compass, my location overlays, v6.0.0 and up

    }

    private fun mapSetup(){
        marker = Marker(mMap)
        marker.setOnMarkerClickListener { marker, _ ->
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
                        viewModel.getLocation(p.latitude, p.longitude)
                    }
                    return false
                }

            }))
        }
    }

    override fun onSearchLocationClicked() {
        Toast.makeText(context, "Location Saved", Toast.LENGTH_SHORT).show()
        viewModel.saveLocation(location)
        Navigation.findNavController(binding.osmmap).navigateUp()
    }

    private fun setMarker(locationItem: LocationItem){
        val position = GeoPoint(locationItem.lat, locationItem.lon)
        mMap.overlays.remove(marker)
        marker.position = position
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        mMap.controller.setCenter(position)
        mMap.controller.animateTo(position)
        mMap.overlays.add(marker)
        LocationConfirmationDialog.showDialog(requireContext(),this@MapFragment, "${locationItem.name} , ${locationItem.country}")
    }

    private fun onSearchListener(keyCode: Int, event: KeyEvent) : Boolean{
        if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
            viewModel.getCoordinates(binding.editTextText.text.toString())
            return true
        } else {
            return false
        }
    }
    private fun onSuccess(data : Any?){
        if (data is Location) {
            if (data.isEmpty())
                mMap.overlays.remove(marker)
            else {
                location = data[0]
                setMarker(location!!)
            }
        }
        else {
            data as SearchRoot
            if (data.isEmpty())
                mMap.overlays.remove(marker)
            else {
                location = data[0].toLocationItem()
                setMarker(location!!)
            }
        }

    }
    private fun setSuggestionListener(){
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED){
                viewModel.suggestionList.collectLatest { it ->
                    when(it){
                        is NetworkResponse.Failure -> {Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()}
                        is NetworkResponse.Loading -> {}
                        is NetworkResponse.Success -> {
                            val data = it.data as SearchRoot
                            Log.d("Filo", "setSuggestionListener: ${data.size}")
                            suggestions.clear()
                            data.forEach {
                                suggestions.add("${it.name}, ${it.country}")
                            }
                            binding.editTextText.refreshAutoCompleteResults()
                            binding.editTextText.setOnItemClickListener { _, _, position, _ ->
                                viewModel.getLocation(data[position].latitude, data[position].longitude)
                            }
                        }
                    }
                }
            }
        }
    }
}