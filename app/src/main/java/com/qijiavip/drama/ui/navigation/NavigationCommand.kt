package com.qijiavip.drama.ui.navigation

import android.os.Bundle

sealed class NavigationCommand {
    data class Navigate(val route: String, val popUpToRoute: String? = null, val inclusive: Boolean = false) : NavigationCommand()
    data class NavigateWithArgs(val route: String, val args: Bundle) : NavigationCommand()
    object GoBack : NavigationCommand()
    data class GoBackTo(val route: String, val inclusive: Boolean = false) : NavigationCommand()
    data class GoBackWithResult<T>(val key: String, val result: T, val destinationRoute: String? = null) : NavigationCommand()
}
