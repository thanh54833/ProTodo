package com.example.protodo.Shortcuts

import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentActivity
import com.example.protodo.R
import com.example.protodo.Utils.Log
import com.example.protodo.editor.WriteAct

class ShortcutUtils(var context: Context, var shortCuts: List<ShortCutModel>) {
    private var sM: ShortcutManager? = null

    fun build() {
        if (Build.VERSION.SDK_INT >= 25) {
            createShortcut(context, shortCuts)
        } else {
            removeShortcuts(context)
        }
    }

    @TargetApi(25)
    private fun createShortcut(context: Context, shortCuts: List<ShortCutModel>) {
        (context as? FragmentActivity)?.apply {
            val listShortCuts = mutableListOf<ShortcutInfo>()
            shortCuts.forEachIndexed { _index, _shortcut ->
                sM = getSystemService(ShortcutManager::class.java)
                val intent1 = Intent(context, _shortcut.startIntent)
                //Todo : Thanh chưa làm truyên param ...
                intent1.putExtra("", "")
                intent1.action = Intent.ACTION_VIEW
                val shortcut1: ShortcutInfo = ShortcutInfo.Builder(this, "shortcut$_index")
                    .setIntent(intent1)
                    .setLongLabel(_shortcut.longLabel)
                    .setShortLabel(_shortcut.shortLabel)
                    .setDisabledMessage(_shortcut.disabledMessage)
                    .setIcon(Icon.createWithResource(context, _shortcut.icon))
                    .build()
                listShortCuts.add(shortcut1)
            }
            sM?.dynamicShortcuts = listShortCuts
        }
    }

    @TargetApi(25)
    private fun removeShortcuts(context: Context) {
        (context as? FragmentActivity)?.apply {
            val shortcutManager = getSystemService(ShortcutManager::class.java)
            shortcutManager.disableShortcuts(listOf("shortcut1"))
            shortcutManager.removeAllDynamicShortcuts()
        }
    }
}