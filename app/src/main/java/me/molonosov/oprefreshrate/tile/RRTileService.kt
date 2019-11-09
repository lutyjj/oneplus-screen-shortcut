package me.molonosov.oprefreshrate.tile

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import me.molonosov.oprefreshrate.service.RefreshRateService

var LABELS_BY_STATE = arrayOf("90Hz", "60Hz", "Auto")

class RRTileService : TileService() {
    private var refreshRateService: RefreshRateService? = null

    init {
        this.refreshRateService = RefreshRateService()
    }

    override fun onClick() {
        super.onClick()

        this.refreshRateService?.toggleStatus()

        this.updateState()
    }

    override fun onStartListening() {
        super.onStartListening()

        this.updateState()
    }

    private fun setState(state: Int) {
        val tile = qsTile

        tile.label = LABELS_BY_STATE[state]

        if (state != 0)
            tile.state = state

        tile.updateTile()
    }

    private fun updateState() {
        val rateStatus = this.refreshRateService?.getStatus()

        if (rateStatus == 0 || rateStatus == 2) {
            return this.setState(rateStatus)
        }
        return this.setState(Tile.STATE_INACTIVE)
    }
}
