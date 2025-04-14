package com.example.firstinubuntu.fragments

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.drawerlayout.widget.DrawerLayout
import com.example.firstinubuntu.R
import com.example.firstinubuntu.binding
import com.example.firstinubuntu.databinding.FragmentWebBinding
import com.example.firstinubuntu.databinding.NoInternetDialogBinding


class WebFragment : Fragment() {


    private val binding by lazy { FragmentWebBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val getData = arguments?.getInt("keys")

        if (getData == 1) {
            binding.viewAbout.visibility = View.VISIBLE
        } else {
            setupWebView()
        }
        return binding.root
    }

    private fun setupWebView() {
        val webView = binding.myWeb
        webView.visibility = View.VISIBLE
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        if (isInternetAvailable()) {
            webView.loadUrl("https://unsplash.com")
            webView.webViewClient = WebViewClient()
        } else {
            showNoInternetDialog(webView)
        }
    }

    private fun showNoInternetDialog(webView: WebView) {
        val dialogBinding = NoInternetDialogBinding.inflate(layoutInflater)

        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .setCancelable(false)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.btnRetry.setOnClickListener {
            dialog.dismiss()
            setupWebView()
        }

        dialogBinding.btnBack.setOnClickListener {
            dialog.dismiss()
            val drawerLayout =
                activity?.findViewById<DrawerLayout>(com.example.firstinubuntu.R.id.my_drawer)
            drawerLayout?.openDrawer(Gravity.LEFT)
        }

        dialog.show()

        val displayMetrics = resources.displayMetrics
        val marginInPx = (50 * displayMetrics.density).toInt()
        dialog.window?.setLayout(
            displayMetrics.widthPixels - 2 * marginInPx,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

    }

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

}