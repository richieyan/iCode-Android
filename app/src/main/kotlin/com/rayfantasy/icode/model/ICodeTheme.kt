/*
 * Copyright 2016 Alex Zhang aka. ztc1997
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.rayfantasy.icode.model

import android.content.Context
import android.databinding.ObservableInt
import com.rayfantasy.icode.R
import com.rayfantasy.icode.extension.colorAnim
import org.jetbrains.anko.defaultSharedPreferences

object ICodeTheme {
    const val PREF_ICODE_THEME = "pref_icode_theme"
    const val THEME_BLUE = 0
    const val THEME_RED = 1
    const val THEME_PURPLE = 2
    const val THEME_GRAY = 3
    const val THEME_YELLOW = 4
    const val THEME_DEEPBLUE = 5
    const val THEME_DEFAULT = THEME_RED
    internal val colorPrimaryRes = intArrayOf(R.color.colorPrimary_blue, R.color.colorPrimary_red,
            R.color.colorPrimary_purple, R.color.colorPrimary_gray, R.color.colorPrimary_yellow, R.color.colorPrimary_deepblue)
    internal val colorPrimaryDarkRes = intArrayOf(R.color.colorPrimaryDark_blue, R.color.colorPrimaryDark_red,
            R.color.colorPrimaryDark_purple, R.color.colorPrimaryDark_gray, R.color.colorPrimaryDark_yellow, R.color.colorPrimaryDark_deepblue)
    internal val colorAccentRes = intArrayOf(R.color.colorAccent_blue, R.color.colorAccent_red,
            R.color.colorAccent_purple, R.color.colorAccent_gray, R.color.colorAccent_yellow, R.color.colorAccent_deepblue)
    internal val colorHighLightRes = intArrayOf(R.color.high_light_blue, R.color.high_light_red,
            R.color.high_light_purple, R.color.high_light_gray, R.color.high_light_yellow, R.color.high_light_deepblue)

    fun init(ctx: Context) {
        val theme = ctx.defaultSharedPreferences.getInt(PREF_ICODE_THEME, THEME_DEFAULT)
        ctx.changeTheme(theme)
    }

    val colorPrimary = ObservableInt()

    val colorPrimaryDark = ObservableInt()

    val colorAccent = ObservableInt()

    val colorHighLight = ObservableInt()

    val icon = ObservableInt()
}

fun Context.changeTheme(theme: Int) = with(ICodeTheme) {
    changeColor(colorPrimary, resources.getColor(colorPrimaryRes[theme]))
    changeColor(colorPrimaryDark, resources.getColor(colorPrimaryDarkRes[theme]))
    changeColor(colorAccent, resources.getColor(colorAccentRes[theme]))
    changeColor(colorHighLight, resources.getColor(colorHighLightRes[theme]))
    defaultSharedPreferences.edit().putInt(PREF_ICODE_THEME, theme).apply()
}

private fun changeColor(observableInt: ObservableInt, color: Int) {
    val i = observableInt.get()
    if (i == color) return
    if (i != 0) {
        colorAnim(i, color, 300, { observableInt.set(it) })
    } else
        observableInt.set(color)
}
