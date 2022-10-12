package com.example.news.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.airbnb.lottie.LottieAnimationView
import com.example.news.R

fun likeAnimation(
    img: LottieAnimationView,
    animation: Int,
    like: Boolean
) : Boolean {
    if(!like) {
        img.setAnimation(animation)
        img.playAnimation()
    } else {
        img
            .animate()
            .alpha(0f)
            .setDuration(200)
            .setListener(object: AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    img.setImageResource(R.drawable.twitter_like)
                    img.alpha = 1f
                }
            })
    }
    return !like
}