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

package com.reboot297.sensors.lib.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.textview.MaterialTextView

class DeviceOrientationValueView(
    context: Context,
    attrs: AttributeSet?,
) : MaterialTextView(context, attrs) {
    fun setValues(values: FloatArray?) {
        text = values?.let { formatValue(it) }
    }

    private fun formatValue(array: FloatArray): String =
        StringBuilder()
            .append("Azimuth: ")
            .append(
                Math.toDegrees(array[0].toDouble()).toFloat(),
            ).append("\n")
            .append("Pitch: ")
            .append(
                Math.toDegrees(array[1].toDouble()).toFloat(),
            ).append("\n")
            .append("Roll: ")
            .append(Math.toDegrees(array[2].toDouble()))
            .toString()
}
