package com.mking11.androidDemo

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.mking11.androidDemo.ui.theme.AndriodTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val viewModel: MainActivityViewModel by viewModels()

    private val requestCodeStorageReadPermission = 1003

    private var hasPremissions = false

    private val permissions = arrayOf(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.INTERNET,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.apply { }
        checkPermissions()
        setContent {
            AndriodTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {


                    val mapView = rememberMapViewWithLifecycle()
                    Column(){
                        if (hasPremissions) {
                            AndroidView({ mapView }) { mapView ->
                                CoroutineScope(Dispatchers.Main).launch {
                                    val map = mapView.getMapAsync { map ->
                                        map.uiSettings.isZoomControlsEnabled = true

                                        val pickUp = LatLng(-35.016, 143.321)
                                        val destination = LatLng(-32.491, 147.309)
                                        map.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(
                                                destination,
                                                6f
                                            )
                                        )
                                        val markerOptions = MarkerOptions()
                                            .title("Sydney Opera House")
                                            .position(pickUp)
                                        map.addMarker(markerOptions)

                                        val markerOptionsDestination = MarkerOptions()
                                            .title("Restaurant Hubert")
                                            .position(destination)
                                        map.addMarker(markerOptionsDestination)

                                        map.addPolyline(
                                            PolylineOptions().add(
                                                pickUp,
                                                LatLng(-34.747, 145.592),
                                                LatLng(-34.364, 147.891),
                                                LatLng(-33.501, 150.217),
                                                LatLng(-32.306, 149.248),
                                                destination
                                            )
                                        )

                                    }

                                }

                            }
                        }
                    }

                }
            }
        }
    }

    private fun checkPermissions() {
        if (!hasPermissions(this, *permissions)) {
            ActivityCompat.requestPermissions(this, permissions, requestCodeStorageReadPermission)
        } else {
            hasPremissions = true
        }
    }

    fun hasPermissions(context: Context, vararg permissions: String): Boolean =
        permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }


    @Composable
    fun rememberMapViewWithLifecycle(): MapView {
        val context = LocalContext.current
        val mapView = remember {
            MapView(context).apply {
                id = R.id.map
            }
        }

        // Makes MapView follow the lifecycle of this composable
        val lifecycleObserver = rememberMapLifecycleObserver(mapView)
        val lifecycle = LocalLifecycleOwner.current.lifecycle
        DisposableEffect(lifecycle) {
            lifecycle.addObserver(lifecycleObserver)
            onDispose {
                lifecycle.removeObserver(lifecycleObserver)
            }
        }

        return mapView
    }

    //request permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1003) {
            if (grantResults.isEmpty() || grantResults.contains(PackageManager.PERMISSION_DENIED)) {
                checkPermissions()
            }
        }


    }


    @Composable
    fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
        remember(mapView) {
            LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                    else -> throw IllegalStateException()
                }
            }
        }
}

