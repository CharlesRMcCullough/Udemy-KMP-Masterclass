package com.charlesmccullough.dailypulse

class CancellableHandle(
    private val onCancel: () -> Unit,
) {
    fun cancel() {
        onCancel()
    }
}