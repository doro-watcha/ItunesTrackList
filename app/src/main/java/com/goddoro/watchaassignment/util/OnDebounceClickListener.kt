package com.goddoro.watchaassignment.util

import android.view.View


typealias OnClickListener = (View) -> Unit

class OnDebounceClickListener(private val listener: OnClickListener) : View.OnClickListener {
    override fun onClick(v: View?) {
        val now = System.currentTimeMillis()
        if (now < lastTime + INTERVAL) return
        lastTime = now
        v?.run(listener)
    }

    companion object {
        private const val INTERVAL: Long = 300L
        private var lastTime: Long = 0L
    }
}

infix fun View.setOnDebounceClickListener(listener: OnClickListener) {
    this.setOnClickListener(OnDebounceClickListener {
        it.run(listener)
    })
}