/*
 * Copyright (c) 2023. Viktor Pop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.reboot297.sensors.wear.raw

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.reboot297.sensors.R
import com.reboot297.sensors.databinding.ActivityRawDataBinding
import com.reboot297.sensors.wear.raw.accelerometer.AccelerometerActivity
import com.reboot297.sensors.wear.raw.accelerometeruncalibrated.AccelerometerUncalibratedActivity
import com.reboot297.sensors.wear.raw.ambienttemperature.AmbientTemperatureActivity
import com.reboot297.sensors.wear.raw.gamerotationvector.GameRotationVectorActivity
import com.reboot297.sensors.wear.raw.geomagneticrotationvector.GeomagneticRotationVectorActivity
import com.reboot297.sensors.wear.raw.gravity.GravityActivity
import com.reboot297.sensors.wear.raw.gyroscope.GyroscopeActivity
import com.reboot297.sensors.wear.raw.gyroscopeuncalibrated.GyroscopeUncalibratedActivity
import com.reboot297.sensors.wear.raw.light.LightActivity
import com.reboot297.sensors.wear.raw.linearacceleration.LinearAccelerationActivity
import com.reboot297.sensors.wear.raw.magneticfield.MagneticFieldActivity
import com.reboot297.sensors.wear.raw.magneticfielduncalibrated.MagneticFieldUncalibratedActivity
import com.reboot297.sensors.wear.raw.orientation.OrientationActivity
import com.reboot297.sensors.wear.raw.pressure.PressureActivity
import com.reboot297.sensors.wear.raw.proximity.ProximityActivity
import com.reboot297.sensors.wear.raw.relativehumidity.RelativeHumidityActivity
import com.reboot297.sensors.wear.raw.rotationvector.RotationVectorActivity
import com.reboot297.sensors.wear.raw.significantmotion.SignificantMotionActivity
import com.reboot297.sensors.wear.raw.stepcounter.StepCounterActivity
import com.reboot297.sensors.wear.raw.stepdetection.StepDetectionActivity
import com.reboot297.sensors.wear.raw.temperature.TemperatureActivity

class RawDataActivity : ComponentActivity() {
    private lateinit var binding: ActivityRawDataBinding

    private val map =
        mapOf<Int, Class<*>>(
            R.id.accelerometer_button to AccelerometerActivity::class.java,
            R.id.gravity_button to GravityActivity::class.java,
            R.id.gyroscope_button to GyroscopeActivity::class.java,
            R.id.gyroscope_uncalibrated_button to GyroscopeUncalibratedActivity::class.java,
            R.id.linear_acceleration_button to LinearAccelerationActivity::class.java,
            R.id.rotation_vector_button to RotationVectorActivity::class.java,
            R.id.significant_motion_button to SignificantMotionActivity::class.java,
            R.id.step_detector_button to StepDetectionActivity::class.java,
            R.id.step_counter_button to StepCounterActivity::class.java,
            R.id.game_rotation_vector_button to GameRotationVectorActivity::class.java,
            R.id.geomagnetic_rotation_vector_button to GeomagneticRotationVectorActivity::class.java,
            R.id.magnetic_field_button to MagneticFieldActivity::class.java,
            R.id.magnetic_field_uncalibrated_button to MagneticFieldUncalibratedActivity::class.java,
            R.id.orientation_button to OrientationActivity::class.java,
            R.id.proximity_button to ProximityActivity::class.java,
            R.id.ambient_temperature_button to AmbientTemperatureActivity::class.java,
            R.id.temperature_button to TemperatureActivity::class.java,
            R.id.light_button to LightActivity::class.java,
            R.id.pressure_button to PressureActivity::class.java,
            R.id.relative_humidity_button to RelativeHumidityActivity::class.java,
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRawDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.accelerometerUncalibratedButton.isEnabled = true
            binding.accelerometerUncalibratedButton.setOnClickListener {
                startActivity(Intent(this, AccelerometerUncalibratedActivity::class.java))
            }
        } else {
            binding.accelerometerUncalibratedButton.isEnabled = false
            binding.accelerometerUncalibratedButton.text =
                getString(R.string.title_accelerometer_uncalibrated) + "\n" + getString(R.string.warning_api_26)
        }

        map.keys
            .asSequence()
            .map { findViewById<View>(it) }
            .forEach { it.setOnClickListener { view -> openDetails(view) } }
    }

    private fun openDetails(view: View) {
        startActivity(Intent(this, map[view.id]))
    }
}
