package com.qijiavip.drama.ui.navigation

import android.os.Bundle
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor() {

    private val _navigationChannel = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 1)
    val navigationChannel = _navigationChannel.asSharedFlow()

    fun navigate(route: String, popUpToRoute: String? = null, inclusive: Boolean = false) {
        _navigationChannel.tryEmit(NavigationCommand.Navigate(route, popUpToRoute, inclusive))
    }

    fun navigateWithArgs(route: String, args: Bundle) {
        _navigationChannel.tryEmit(NavigationCommand.NavigateWithArgs(route, args))
    }

    fun goBack() {
        _navigationChannel.tryEmit(NavigationCommand.GoBack)
    }

    fun goBackTo(route: String, inclusive: Boolean = false) {
        _navigationChannel.tryEmit(NavigationCommand.GoBackTo(route, inclusive))
    }

    fun <T> goBackWithResult(key: String, result: T, destinationRoute: String? = null) {
        _navigationChannel.tryEmit(NavigationCommand.GoBackWithResult(key, result, destinationRoute))
    }
}
