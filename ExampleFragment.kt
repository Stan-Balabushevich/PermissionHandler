
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

class ExampleFragment: Fragment() {

    private lateinit var permissionHandler: PermissionsHandler


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPermissionsHandler()
        permissionHandler.requestPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    }

    private fun setUpPermissionsHandler() {
        permissionHandler = PermissionsHandler(requireContext())
        permissionHandler.setPermissionCallbacks(
            onGranted = { permission ->
                if (permission == Manifest.permission.ACCESS_FINE_LOCATION) {
                    // Permission granted you can get user location
                    getLocation()
                }
            },
            onDenied = { permission ->
                if (permission == Manifest.permission.ACCESS_FINE_LOCATION) {
                    //Permission denied show dialog why you need user location
                    permissionHandler.showPermissionChangeDialog(requireContext(), "Location")
                }
            }
        )
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (isLocationEnabled(requireContext())) {
            val fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
            fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                location?.let {
                    // Location is enabled you can get user location
                    val userLatLng = LatLng(location.latitude, location.longitude)
                }
            }
        } else {
            // Location is not enabled show dialog why you need user to enable location
            permissionHandler.showEnableLocationDialog(requireContext())
        }
    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

}
