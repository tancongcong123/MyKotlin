package com.apricity.mykotlin.ext

fun CharSequence?.default(default: String = "") = if (this.isNullOrBlank()) default else this.toString()
fun String?.default(default: String = "") = if (this.isNullOrBlank()) default else this.toString()