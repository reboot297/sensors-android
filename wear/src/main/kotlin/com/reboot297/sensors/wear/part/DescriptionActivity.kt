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

package com.reboot297.sensors.wear.part

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import com.reboot297.sensors.databinding.ActivityDetailsDescriptionBinding

class DescriptionActivity : ComponentActivity() {

    private lateinit var binding: ActivityDetailsDescriptionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val description = intent.getIntExtra(com.reboot297.sensors.wear.part.DescriptionActivity.Companion.KEY_RESOURCE_ID, -1)

        binding.valueView.setText(description)
    }

    companion object {
        private const val KEY_RESOURCE_ID = "resourceId"
        fun start(context: Context, @StringRes descriptionId: Int) {
            context.startActivity(
                Intent(context, com.reboot297.sensors.wear.part.DescriptionActivity::class.java).apply {
                    putExtra(com.reboot297.sensors.wear.part.DescriptionActivity.Companion.KEY_RESOURCE_ID, descriptionId)
                },
            )
        }
    }
}
