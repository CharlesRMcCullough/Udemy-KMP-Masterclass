package com.charlesmccullough.dailypulse.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.charlesmccullough.dailypulse.Platform


@Composable
fun AboutScreen(
    onUpButtonClick: () -> Unit
) {
    Column {
        Toolbar(onUpButtonClick)
        ContentView()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
    onUpButtonClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "About Device")
        },
        navigationIcon = {
            IconButton(onClick = onUpButtonClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Up"
                )
            }
        }
    )
}

@Composable
private fun ContentView() {
    val items = makeItems()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) { row ->
            RowView(
                title = row.first, subTitle = row.second
            )
        }
    }
}

@Composable
private fun RowView(
    title: String, subTitle: String
) {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = title, style = MaterialTheme.typography.bodyMedium, color = Color.Gray
        )
        Text(
            text = subTitle, style = MaterialTheme.typography.bodyLarge, color = Color.Gray
        )
    }
    HorizontalDivider(
        thickness = 2.dp, color = Color.LightGray
    )
}

private fun makeItems(): List<Pair<String, String>> {
    val platform = Platform()
    return listOf(
        Pair("Operating System", "${platform.osName} ${platform.osVersion}"),
        Pair("Device", platform.deviceModel),
        Pair("Density", platform.density.toString())
    )
}