package com.mooncascade.common.extensions.contexts

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

/**
 * this file used to handle some [Fragment] extensions using contexts receivers in the [Fragment] lifecycle
 * ```
 * navigateTo(R.id.action_HomeFragment_to_PlaceDetailsFragment)
 * ```
 */

context(Fragment)
fun Fragment.navigateTo(@IdRes resId: Int) = findNavController().navigate(resId)