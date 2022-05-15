package com.mooncascade.common.extensions.contexts

import org.json.JSONObject

/**
 * this file used to create json using contexts receivers in [JSONObject] lifecycle
 *
 * ```
 * val json = jsonBuilder {
 *      "name" by "MoonCascade"
 *      "description" by "Tech Company"
 *      "creator" by {
 *      "name" by "Morteza"
 *      "lastname" by "Nedaei"
 *      "age" by "26"
 *     }
 *  }
 * ```
 */


context(JSONObject)
        infix fun String.by(
    build: JSONObject.() -> Unit
): JSONObject = put(this, JSONObject().build())

context(JSONObject)
        infix fun String.by(
    value: Any
): JSONObject = put(this, value)

/**
 * @return new json object
 */
fun jsonBuilder(build: JSONObject.() -> Unit) = JSONObject().apply { build() }

