package com.d3.duy.citipocket.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.d3.duy.citipocket.activity.permission.RequestorCallback;

/**
 * Created by daoducduy0511 on 3/4/16.
 */
public abstract class PermissionRequestorActivity extends AppCompatActivity {

    public static final int PERMISSIONS_REQUEST_READ_SMS = 0;
    public static final int PERMISSIONS_REQUEST_RECEIVE_SMS = 1;

    private RequestorCallback callback;

    protected abstract View getViewForSnackbar();

    public void request(int requestCode, RequestorCallback callback) {
        // firstly set callback
        this.callback = callback;

        String permissionString;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_SMS:
                permissionString = Manifest.permission.READ_SMS;
                break;
            case PERMISSIONS_REQUEST_RECEIVE_SMS:
                permissionString = Manifest.permission.RECEIVE_SMS;
                break;
            default:
                return;
        }

        if (ContextCompat.checkSelfPermission(this, permissionString)
                == PackageManager.PERMISSION_GRANTED) {
            callback.onSuccess(requestCode);
            return;
        }

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissionString)) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            Toast.makeText(this, "Granting permission is necessary!", Toast.LENGTH_LONG).show();

        } else {
            ActivityCompat.requestPermissions(this, new String[]{permissionString}, requestCode);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        final String permissionString;
        final int permissionCode;

        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_SMS:
                permissionCode = PERMISSIONS_REQUEST_READ_SMS;
                permissionString = Manifest.permission.READ_SMS;
                break;
            case PERMISSIONS_REQUEST_RECEIVE_SMS:
                permissionCode = PERMISSIONS_REQUEST_RECEIVE_SMS;
                permissionString = Manifest.permission.RECEIVE_SMS;
                break;
            default:
                permissionCode = -1;
                permissionString = "";
        }

        if (permissionCode != -1) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted successfully
                this.callback.onSuccess(permissionCode);

            } else {
                Snackbar.make(this.getViewForSnackbar(),
                        "Permission denied! Please enable it to run correctly",
                        Snackbar.LENGTH_LONG)
                        .setAction("Enable", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                request(permissionCode, callback);
                            }
                        }).show();
            }
        }
    }
}
