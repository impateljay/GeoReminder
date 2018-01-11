package com.jay.georeminder

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.location.places.ui.PlaceAutocomplete

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        try {
            val intent = PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY).build(this)
            startActivityForResult(intent, 1)
        } catch (e: GooglePlayServicesRepairableException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        } catch (e: GooglePlayServicesNotAvailableException) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }

    // A place has been received; use requestCode to track the request.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlaceAutocomplete.getPlace(this, data)
                Log.e("Tag", "Place: " + place.address + place.phoneNumber)
                Toast.makeText(this,place.name.toString() + ",\n" + place.address, Toast.LENGTH_LONG).show();
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                val status = PlaceAutocomplete.getStatus(this, data)
                Log.e("Tag", status.statusMessage)
                Toast.makeText(this, status.statusMessage, Toast.LENGTH_LONG).show()
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
