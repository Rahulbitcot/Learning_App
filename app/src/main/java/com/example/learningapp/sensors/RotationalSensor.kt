package com.example.learningapp.sensors

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.learningapp.R
import android.widget.TextView

class RotationalSensor : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var rotationSensor: Sensor? = null
    private lateinit var rotationTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotational_sensor)

        rotationTextView = findViewById(R.id.RotationalTextView)

        // Initialize sensor manager
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        // Check if rotation sensor is available
        rotationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR)

        if (rotationSensor == null) {
            rotationTextView.text = "Rotation sensor not available"
        }
    }

    override fun onResume() {
        super.onResume()
        // Register the listener for rotation sensor updates
        rotationSensor?.also { sensor ->
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        // Unregister the sensor when the app is paused
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ROTATION_VECTOR) {
            // Get the rotation matrix from the sensor event
            val rotationMatrix = FloatArray(9)
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values)

            // Get the orientation angles (azimuth, pitch, roll)
            val orientationValues = FloatArray(3)
            SensorManager.getOrientation(rotationMatrix, orientationValues)

            // Convert the orientation values to degrees
            val azimuth = Math.toDegrees(orientationValues[0].toDouble()) // Rotation around the Z axis
            val pitch = Math.toDegrees(orientationValues[1].toDouble()) // Rotation around the X axis
            val roll = Math.toDegrees(orientationValues[2].toDouble()) // Rotation around the Y axis

            rotationTextView.text = "Azimuth: %.2f\nPitch: %.2f\nRoll: %.2f".format(azimuth, pitch, roll)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Handle sensor accuracy changes (optional)
    }
}
