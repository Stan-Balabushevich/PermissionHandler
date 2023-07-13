# PermissionsHandler
PermissionsHandler utility class used for handling permissions in an Android application

The provided class is a PermissionsHandler utility class used for handling permissions in an Android application. Its purpose is to simplify the process of requesting and managing runtime permissions required by the app.

Here's an overview of the functionality provided by the PermissionsHandler class:

1. Permission Data Structure: The class defines a data class named `Permission`, which holds information about a specific permission, including its name and whether it has been granted or not.

2. Permission Callbacks: The class allows you to set callback functions for when a permission is granted (`onPermissionGranted`) or denied (`onPermissionDenied`).

3. Requesting Permissions: The `requestPermission` function is used to request a specific permission. It first checks if the permission is already granted. If granted, it invokes the `onPermissionGranted` callback. If not, it checks if the permission should show a rationale to the user. If so, it invokes the `onPermissionDenied` callback. Otherwise, it requests the permission using `ActivityCompat.requestPermissions`.

4. Opening App Settings: The `openAppPermissionsSettings` function opens the app's settings page, allowing the user to manually change the app's permissions.

5. Opening Location Settings: The `openLocationSettings` function opens the device's location settings page, allowing the user to enable or disable location services.

6. Permission Change Dialog: The `showPermissionChangeDialog` function displays a dialog to the user, informing them that a specific permission is required for a certain feature. It provides an option to navigate to the app's settings page to change the permission.

7. Enable Location Dialog: The `showEnableLocationDialog` function displays a dialog to the user, informing them that location services need to be enabled for a certain feature. It provides an option to navigate to the device's location settings page to enable location services.

Overall, the PermissionsHandler class helps encapsulate common permission-related operations and provides methods to handle permission requests, explain the need for permissions, and navigate to relevant settings pages. It simplifies the process of managing permissions in an Android application.
