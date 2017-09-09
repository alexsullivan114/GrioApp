package com.alexsullivan.griointerviewapp.extensions

import android.transition.Transition

fun Transition.addEndListener(block: () -> Unit) {
    val listener = object: Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition?) {
            block()
        }

        override fun onTransitionResume(transition: Transition?) {}
        override fun onTransitionPause(transition: Transition?) {}
        override fun onTransitionCancel(transition: Transition?) {}
        override fun onTransitionStart(transition: Transition?) {}
    }

    addListener(listener)
}