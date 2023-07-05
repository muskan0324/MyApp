package com.example.myapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.myapp.ui.theme.MyAppTheme

import androidx.compose.foundation.layout.Row
import android.content.Context
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

class CalcActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           MyAppTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Calculator(applicationContext)
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Calculator(applicationContext: Context){
    var n1 by remember {
        mutableStateOf("")
    }
    var n2 by remember {
        mutableStateOf("")
    }
    var result by remember{
        mutableStateOf("")
    }
    var bool by remember{
        mutableStateOf(false)
    }
    val backgroundImage = painterResource(R.drawable.background)

    Box (Modifier.fillMaxSize().paint(backgroundImage, contentScale = ContentScale.FillBounds)){

        Column(
            Modifier
                .fillMaxSize()
                .paint(backgroundImage),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = n1,
                onValueChange = {
                    n1 = it
                },
                label = { Text(text = "Enter a number", fontSize = 30.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )
            TextField(
                value = n2,
                onValueChange = {
                    n2 = it
                },
                label = { Text(text = "Enter a number", fontSize = 30.sp) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                textStyle = TextStyle(fontSize = 25.sp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row() {
                Button(
                    onClick = { /*TODO*/
                        result = (n1.toInt() + n2.toInt()).toString()
                        bool =
                            true //Toast.makeText(applicationContext,"added", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.size(width = 100.dp, height = 50.dp)
                ) {
                    Text(text = "ADD", fontSize = 20.sp)
                }
                Button(onClick = { /*TODO*/
                    result = (n1.toInt() - n2.toInt()).toString()
                    bool = true
                }, modifier = Modifier.size(width = 100.dp, height = 50.dp)) {
                    Text(text = "SUB", fontSize = 20.sp)
                }
                Button(onClick = { /*TODO*/
                    result = (n1.toInt() * n2.toInt()).toString()
                    bool = true
                }, modifier = Modifier.size(width = 100.dp, height = 50.dp)) {
                    Text(text = "MUL", fontSize = 20.sp)
                }
                Button(onClick = { /*TODO*/
                    result = String.format("%.2f", n1.toDouble() / n2.toDouble())
                    bool = true
                }, modifier = Modifier.size(width = 100.dp, height = 50.dp)) {
                    Text(text = "DIV", fontSize = 20.sp)
                }
            }
            if (bool) {
                Row() {
                    Text(text = result, color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 40.sp,style=TextStyle(background = Color.White))
                }
            }


        }
    }
}

@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview2() {
//    HousingSessionTheme {
//        Greeting2("Android")
//    }
//}
