package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.example.myapp.apiservice.TrainAPIService
import com.example.myapp.models.request.TrainRequest
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.viewmodels.TrainViewModel

class TrainAPIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                val viewModel = ViewModelProvider(this).get(TrainViewModel::class.java)
                Train(viewModel)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Train(viewModel: TrainViewModel) {
    var trainName by remember { mutableStateOf("")}
    var getDetails by remember {
        mutableStateOf(false)
    }

    var buttonPressed by remember {
        mutableStateOf(false)
    }
    Box(
        Modifier
            .fillMaxSize(),
//            .paint(backgroundImage, contentScale = ContentScale.FillBounds),
        contentAlignment = Alignment.Center) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = trainName,
                onValueChange = { trainName = it },
                label = { Text("Enter the Train Name", fontSize = 20.sp) })
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 130.dp, vertical = 20.dp)
                    .size(height = 50.dp, width = 100.dp),
                onClick = { getDetails = true
                          buttonPressed=true},
            ) {
                Text("Get Details", fontSize = 18.sp)
            }
            if(getDetails && buttonPressed)
                viewModel.getTrainDetailsList(TrainRequest(trainName = trainName))

            if(viewModel.trainDetailsList.isNotEmpty()){
                Text(viewModel.trainDetailsList.toString())
                getDetails=false
            }
            else if(buttonPressed && viewModel.trainDetailsList.isEmpty()){
                Text("No Data Available")
                getDetails=false
            }

        }
    }
}