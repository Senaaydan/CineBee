package com.senaaydan.cinebee_.screens

import android.icu.text.CaseMap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import org.intellij.lang.annotations.JdkConstants


@Composable
fun HomeScreen (){

    Column(modifier =  Modifier.fillMaxSize()
        .background(Color.Blue)
    ,Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "WELCOME")


    }


}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
 HomeScreen()
}

