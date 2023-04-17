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
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

private const val PRIVACY_URL = "https://sites.google.com/view/reboot297-sensors/home"

open class BaseActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_privacy -> {
                openPrivacy()
                return true
            }

            R.id.action_about -> true //TODO(Viktor) open about screen
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openPrivacy() {
        startActivity(Intent(ACTION_VIEW).apply {
            data = Uri.parse(PRIVACY_URL)
        })
    }
}