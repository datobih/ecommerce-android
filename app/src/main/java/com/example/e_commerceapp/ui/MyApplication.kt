package com.example.e_commerceapp.ui

import android.app.Application
import android.util.Log
import com.example.e_commerceapp.BuildConfig
import com.example.e_commerceapp.CREDENTIAL_CONSTS
import com.paypal.checkout.PayPalCheckout
import com.paypal.checkout.config.CheckoutConfig
import com.paypal.checkout.config.Environment
import com.paypal.checkout.config.SettingsConfig
import com.paypal.checkout.createorder.CurrencyCode
import com.paypal.checkout.createorder.UserAction
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val config=CheckoutConfig(
            application = this,
            clientId = CREDENTIAL_CONSTS.PAYPAL_CLIENT_ID,
            environment = Environment.SANDBOX,
            returnUrl = "com.example.ecommerceapp://paypalpay",
            currencyCode = CurrencyCode.USD,
            userAction = UserAction.PAY_NOW,
            settingsConfig = SettingsConfig(
                loggingEnabled = true
            )
        )

        PayPalCheckout.setConfig(config)
    }


}