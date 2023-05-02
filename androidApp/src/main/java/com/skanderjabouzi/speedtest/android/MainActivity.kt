package com.skanderjabouzi.speedtest.android

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.skanderjabouzi.speedtest.Greeting
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    GreetingView(Greeting().greet())
                }
            }
        }
    }
}

@Composable
fun GreetingView(
    text: String,
    viewModel: MainViewModel = viewModel()
) {
    val uploadSpeed by viewModel.uploadSpeed.collectAsStateWithLifecycle()
    val downloadSpeed by viewModel.downloadSpeed.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$text")
        Text(text = uploadSpeed.speed.toString())
        Button(
            onClick = { viewModel.startUploadTest() },
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        ) {
            Text(text = "Upload")
        }
        Text(text = downloadSpeed.speed.toString())
        Button(
            onClick = { viewModel.startDownloadTest() },
            modifier = Modifier
                .width(100.dp)
                .height(100.dp)
        ) {
            Text(text = "Upload")
        }
    }


}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
