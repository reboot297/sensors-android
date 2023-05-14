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

package com.reboot297.sensors.sections.sensor_values

import android.widget.TextView
import androidx.annotation.StringRes
import java.util.Date

class TriggerSensorValue(
    @StringRes
    private val formatMessageId: Int
) : SensorValueSection {
    override fun onDrawValues(view: TextView, values: FloatArray) = with(view) {
        text = values.firstOrNull()?.let {
            context.getString(formatMessageId, Date())
        }
    }
}