package com.akash.myant.networking

interface OnCompletion<T> {
    fun onComplete(t: T?)
}