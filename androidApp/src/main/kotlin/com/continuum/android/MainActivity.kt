package com.continuum.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.continuum.android.ui.navigation.AppNavGraph
import com.continuum.android.ui.theme.ContinuumTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContinuumTheme {
                AppNavGraph()
            }
        }
    }
}
