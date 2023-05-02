package com.skanderjabouzi.speedtest.ui

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel

/**
 * Interface shared between all ViewModels.
 * This is used to to have a common way of interacting with ViewModels.
 */
interface SharedViewModel {

    // Instead of using e.g. `viewModelScope` from Android, we construct our own.
    // This way, the scope is shared between iOS and Android and its lifecycle
    // is controlled the same way.
    val scope
        get() = CoroutineScope(CoroutineName(""))

    /**
     * Cancels the current scope and any jobs in it.
     * This should be called by the UI when it no longer need the
     * ViewModel.
     */
    fun close() {
        scope.cancel()
    }
}