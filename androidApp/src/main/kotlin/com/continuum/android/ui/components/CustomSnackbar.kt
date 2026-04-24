package com.continuum.android.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.continuum.android.ui.theme.B3
import com.continuum.android.ui.theme.Lift
import com.continuum.android.ui.theme.MedRadius
import com.continuum.android.ui.theme.T1

@Composable
fun CustomSnackbar(data: SnackbarData) {
    Snackbar(
        snackbarData = data,
        modifier = Modifier.padding(12.dp),
        shape = RoundedCornerShape(MedRadius.dp),
        containerColor = Lift,
        contentColor = T1,
    )
}
