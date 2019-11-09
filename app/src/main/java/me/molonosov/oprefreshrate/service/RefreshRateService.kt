package me.molonosov.oprefreshrate.service

import android.Manifest
import android.content.Context
import android.provider.Settings
import android.widget.Toast
import androidx.core.content.ContextCompat
import me.molonosov.oprefreshrate.GlobalApplication

class RefreshRateService {
    private var appContext: Context? = null

    init {
        this.appContext = GlobalApplication.getAppContext()
    }

    fun getStatus(): Int {
        var value = -1

        try {
            value = Settings.Global.getInt(this.appContext?.contentResolver, "oneplus_screen_refresh_rate")
        } catch (err: Error) { }

        return value
    }

    private fun setStatus(status: Int) {
        val permission = ContextCompat.checkSelfPermission(this.appContext as Context, Manifest.permission.WRITE_SECURE_SETTINGS)

        if (permission == -1) {
            Toast.makeText(this.appContext, "WRITE_SECURE_SETTINGS Permission needed", Toast.LENGTH_LONG).show()
            return
        }

        Settings.Global.putInt(this.appContext?.contentResolver, "oneplus_screen_refresh_rate", status)
    }

    fun toggleStatus() {
        val currentStatus = this.getStatus()

        if (currentStatus == 0) return this.setStatus(1)
        else if (currentStatus == 1) return this.setStatus(2)

        return this.setStatus(0)
    }
}