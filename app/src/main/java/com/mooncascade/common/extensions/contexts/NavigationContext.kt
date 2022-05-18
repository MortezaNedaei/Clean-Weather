package com.mooncascade.common.extensions.contexts

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController

/**
 * this file used to handle some [Fragment] extensions using contexts receivers in the [Fragment] lifecycle
 * ```
 * navigateTo(R.id.action_HomeFragment_to_PlaceDetailsFragment)
 * ```
 */

context(Fragment)
fun Fragment.navigateTo(@IdRes resId: Int) = findNavController().navigate(resId)

context(Fragment)
fun Fragment.navigateTo(@IdRes resId: Int, args: Bundle?) =
    findNavController().navigate(resId, args)

context(Fragment)
fun Fragment.navigateTo(directions: NavDirections) =
    findNavController().navigate(directions.actionId, directions.arguments, null)


context(Fragment)
fun Fragment.navigateTo(directions: NavDirections, navigatorExtras: Navigator.Extras) =
    findNavController().navigate(directions.actionId, directions.arguments, null, navigatorExtras)