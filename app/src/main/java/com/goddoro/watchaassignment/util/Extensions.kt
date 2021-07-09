package com.goddoro.watchaassignment.util


/**
 * created By DORO 2021/07/09
 */

fun HashMap<String, out Any?>.filterValueNotNull(): HashMap<String, Any> {
    return this.filterNot { it.value == null } as HashMap<String, Any>
}