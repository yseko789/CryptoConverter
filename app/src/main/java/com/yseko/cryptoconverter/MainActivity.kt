package com.yseko.cryptoconverter


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.yseko.cryptoconverter.ui.theme.CryptoConverterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CryptoConverterTheme {
                SearchScreen()
            }
        }
    }
}

