package com.example.myapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSavedStateRegistryOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.apiservice.APIService
import com.example.myapp.apiservice.WeatherAPIService
import com.example.myapp.models.response.Current
import com.example.myapp.models.response.Location
import com.example.myapp.models.response.MemesResponse
import com.example.myapp.models.response.WeatherResponse
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class WeatherAPIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {

                Weather()
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Weather(){
    var apiService= WeatherAPIService.getInstance()
    var scope= rememberCoroutineScope()

    var weatherResponse by remember{ mutableStateOf(WeatherResponse()) }
    var getWeather by remember { mutableStateOf(false) }
    var location by remember { mutableStateOf("")}
    var weatherCondition by remember {
        mutableStateOf("")
    }
    var imageId by remember {
        mutableStateOf(R.drawable.back)
    }
    val backgroundImage = painterResource(imageId)


    Box(
        Modifier
            .fillMaxSize()
            .paint(backgroundImage, contentScale = ContentScale.FillBounds), contentAlignment = Alignment.Center){
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
            OutlinedTextField(value = location, onValueChange = {location=it}, label = {Text("Enter your City", fontSize = 20.sp)})
            Button(modifier= Modifier
                .fillMaxWidth()
                .padding(horizontal = 130.dp, vertical = 20.dp)
                .size(height = 50.dp, width = 100.dp),onClick={getWeather=true},){
                Text("Get Weather", fontSize = 18.sp)
            }
            if(getWeather){
                scope.launch {
                    try {
                        weatherResponse= apiService.getWeather(location=location)
                        weatherCondition= weatherResponse.current!!.condition?.text.toString()
//                        if(weatherCondition.contains("rain")) imageId=R.drawable.rainy
//                        if(weatherCondition.contains("cloudy")) imageId=R.drawable.cloudy
                    }
                    catch (ex:Exception){
                        throw Exception(ex.message)
                    }
                }

            }
            if(!weatherCondition.isNullOrBlank() && weatherCondition!="null") {
                Text(weatherCondition , fontSize = 30.sp, fontWeight = FontWeight.Bold,color=Color.Black)
                getWeather=false
            }

        }
    }

}
