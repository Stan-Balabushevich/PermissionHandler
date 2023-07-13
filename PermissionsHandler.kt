
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class PermissionsHandler(private val context: Context) {

    data class Permission(val name: String, val granted: Boolean)

    private var permissions: List<Permission> = emptyList()
    private var onPermissionGranted: ((String) -> Unit)? = null
    private var onPermissionDenied: ((String) -> Unit)? = null

    fun setPermissionCallbacks(
        onGranted: ((String) -> Unit)? = null,
        onDenied: ((String) -> Unit)? = null
    ) {
        onPermissionGranted = onGranted
        onPermissionDenied = onDenied
    }

    fun requestPermission(permissionName: String) {
        val permission = permissions.find { it.name == permissionName }

        if (permission != null && permission.granted) {
            onPermissionGranted?.invoke(permissionName)
        } else {
            val isPermissionGranted = ContextCompat.checkSelfPermission(context, permissionName) == PackageManager.PERMISSION_GRANTED

            if (isPermissionGranted) {
                onPermissionGranted?.invoke(permissionName)
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, permissionName)) {
                    // Explain to the user why the permission is needed
                    // You can show a dialog, toast, or any other UI element to provide an explanation
                    onPermissionDenied?.invoke(permissionName)
                } else {
                    ActivityCompat.requestPermissions(
                        context,
                        arrayOf(permissionName),
                        PERMISSION_REQUEST_CODE
                    )
                }
            }
        }
    }

    private fun openAppPermissionsSettings(context: Context) {
        val packageName = context.packageName
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", packageName, null)
        context.startActivity(intent)
    }

    private fun openLocationSettings(context: Context) {
        val locationSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        context.startActivity(locationSettingIntent)
    }

    fun showPermissionChangeDialog(context: Context, permissionName: String = "Specific") {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("$permissionName Permission Required")
        dialogBuilder.setMessage("This feature requires a $permissionName permission. Do you want to go to settings and change the permission?")
        dialogBuilder.setPositiveButton("Settings") { dialogInterface: DialogInterface, _: Int ->
            openAppPermissionsSettings(context)
            dialogInterface.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    fun showEnableLocationDialog(context: Context, title: String = "", description: String = "") {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setTitle("Enabled Location Required")
        dialogBuilder.setMessage("This feature requires your location. Do you want to go to settings and enable your location?")
        dialogBuilder.setPositiveButton("Settings") { dialogInterface: DialogInterface, _: Int ->
            openLocationSettings(context)
            dialogInterface.dismiss()
        }
        dialogBuilder.setNegativeButton("Cancel") { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
    }
}
