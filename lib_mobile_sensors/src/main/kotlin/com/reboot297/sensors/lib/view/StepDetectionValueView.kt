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
import com.reboot297.sensors.lib.R
import java.util.Date

class StepDetectionValueView(context: Context, attrs: AttributeSet?) :
    MaterialTextView(context, attrs) {

    private val message: String

    init {
        message = context.getString(R.string.step_detection_value)
    }

    fun setValues(values: FloatArray?) {
        text = String.format(message, Date().toString())
    }
}
