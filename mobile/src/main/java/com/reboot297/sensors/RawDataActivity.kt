/*
 * Copyright (c) 2023. Viktor Pop
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *           http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.reboot297.sensors

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import com.reboot297.sensors.databinding.ActivityRawDataBinding
import com.reboot297.sensors.raw.environment.AmbientTemperatureDetailsActivity
import com.reboot297.sensors.raw.environment.LightDetailsActivity
import com.reboot297.sensors.raw.environment.PressureDetailsActivity
import com.reboot297.sensors.raw.environment.RelativeHumidityDetailsActivity
import com.reboot297.sensors.raw.environment.TemperatureDetailsActivity
import com.reboot297.sensors.raw.motion.AccelerometerDetailsActivity
import com.reboot297.sensors.raw.motion.AccelerometerUncalibratedDetailsActivity
import com.reboot297.sensors.raw.motion.GravityDetailsActivity
import com.reboot297.sensors.raw.motion.GyroscopeDetailsActivity
import com.reboot297.sensors.raw.motion.GyroscopeUncalibratedDetailsActivity
import com.reboot297.sensors.raw.motion.LinearAccelerationDetailsActivity
import com.reboot297.sensors.raw.motion.RotationVectorDetailsActivity
import com.reboot297.sensors.raw.motion.SignificantMotionsDetailsActivity
import com.reboot297.sensors.raw.motion.StepCounterDetailsActivity
import com.reboot297.sensors.raw.motion.StepDetectorDetailsActivity
import com.reboot297.sensors.raw.position.GameRotationVectorDetailsActivity
import com.reboot297.sensors.raw.position.GeomagneticRotationVectorDetailsActivity
import com.reboot297.sensors.raw.position.MagneticFieldDetailsActivity
import com.reboot297.sensors.raw.position.MagneticFieldUncalibratedDetailsActivity
import com.reboot297.sensors.raw.position.OrientationDetailsActivity
import com.reboot297.sensors.raw.position.ProximityDetailsActivity

class RawDataActivity : BaseActivity() {

    private lateinit var binding: ActivityRawDataBinding

    private val map = mapOf<Int, Class<*>>(
        R.id.accelerometer_item_view to AccelerometerDetailsActivity::class.java,
        R.id.gravity_item_view to GravityDetailsActivity::class.java,
        R.id.gyroscope_item_view to GyroscopeDetailsActivity::class.java,
        R.id.gyroscope_uncalibrated_item_view to GyroscopeUncalibratedDetailsActivity::class.java,
        R.id.linear_acceleration_item_view to LinearAccelerationDetailsActivity::class.java,
        R.id.rotation_vector_item_view to RotationVectorDetailsActivity::class.java,
        R.id.significant_motions_item_view to SignificantMotionsDetailsActivity::class.java,
        R.id.step_detector_item_view to StepDetectorDetailsActivity::class.java,
        R.id.step_counter_item_view to StepCounterDetailsActivity::class.java,

        R.id.game_rotation_vector_item_view to GameRotationVectorDetailsActivity::class.java,
        R.id.geomagnetic_rotation_vector_item_view to GeomagneticRotationVectorDetailsActivity::class.java,
        R.id.magnetic_field_item_view to MagneticFieldDetailsActivity::class.java,
        R.id.magnetic_field_uncalibrated_item_view to MagneticFieldUncalibratedDetailsActivity::class.java,
        R.id.orientation_item_view to OrientationDetailsActivity::class.java,
        R.id.proximity_item_view to ProximityDetailsActivity::class.java,

        R.id.ambient_temperature_item_view to AmbientTemperatureDetailsActivity::class.java,
        R.id.temperature_item_view to TemperatureDetailsActivity::class.java,
        R.id.light_item_view to LightDetailsActivity::class.java,
        R.id.pressure_item_view to PressureDetailsActivity::class.java,
        R.id.relative_humidity_item_view to RelativeHumidityDetailsActivity::class.java,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRawDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.accelerometerUncalibratedItemView.isEnabled = true
            binding.accelerometerUncalibratedItemView.setOnClickListener {
                startActivity(Intent(this, AccelerometerUncalibratedDetailsActivity::class.java))
            }
        } else {
            binding.accelerometerUncalibratedItemView.isEnabled = false
            binding.accelerometerUncalibratedItemView.text =
                getString(R.string.title_accelerometer_uncalibrated) + "\n" + getString(R.string.warning_api_26)
        }

        map.keys.asSequence()
            .map { findViewById<View>(it) }
            .forEach { it.setOnClickListener { view -> openDetails(view) } }
    }

    private fun openDetails(view: View) {
        startActivity(Intent(this, map[view.id]))
    }
}