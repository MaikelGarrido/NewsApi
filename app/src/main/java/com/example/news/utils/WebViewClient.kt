package com.example.news.utils

import android.webkit.WebView

object WebViewClient : android.webkit.WebViewClient(){
    
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return false
    }

    override fun onPageFinished(view: WebView, url: String) {
        super.onPageFinished(view, url)
        finishProgress()
    }

}