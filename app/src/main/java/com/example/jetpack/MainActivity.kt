package com.example.jetpack

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jetpack.ui.theme.JetpackTheme

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION), 100)
        setContent {
            JetpackTheme {
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("ios")
//                }
                val viewModel = viewModel<MainViewModel>()
                val searchText by viewModel.searchText.collectAsState()
                val isSearching by viewModel.isSearching.collectAsState()
                val persons by viewModel.persons.collectAsState()

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),

                ) {
                    TextField(
                        value = searchText,
                        onValueChange = viewModel::onSearchTextChange,
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text(text = "Search") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LazyColumn(modifier = Modifier.fillMaxWidth().weight(1f)) {
                        items(persons) { person ->
                            Text(
                                text = "${person.firstName} ${person.lastName}",
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetpackTheme {
        Greeting("Android")
    }
}