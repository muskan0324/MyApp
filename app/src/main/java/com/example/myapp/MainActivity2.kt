package com.example.myapp

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.media.Image
import android.os.Build
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.theme.MyAppTheme
import java.time.LocalDate

class MainActivity2 : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                LazyList()
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LazyList(){
     val fruitsList = mutableListOf<fruitModel>()

    fruitsList.add(fruitModel("Apple",R.drawable.apple) )
    fruitsList.add(fruitModel("Orange",R.drawable.orange))
    fruitsList.add(fruitModel("Banana", R.drawable.banana))
    fruitsList.add(fruitModel("Strawberry", R.drawable.strawberry))
    fruitsList.add(fruitModel("Mango", R.drawable.mango))

    val context= LocalContext.current
    Column() {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(Color.White)
//        ) {
//            items(fruitsList) { model ->
//                ListRow(model = model)
//            }
//        }

        val list = OnDateSetListener { datePicker, year, month, date ->
            Toast.makeText(context,"$year/ ${month+1} / ${date} ", Toast.LENGTH_SHORT)
        }
        Button(
            onClick = { DatePickerDialog(
                context, list,20,2,2)
            .show() },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Select Date")
        }

    }



}
@Composable
fun ListRow(model: fruitModel) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(Color(60,80,80))
    ) {
        Image(
            painter = painterResource(id = model.image),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(100.dp)
                .padding(5.dp)
        )
        Text(
            text = model.name,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White
        )
    }
}


@Composable
fun Greeting4(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    MyAppTheme {
        Greeting4("Android")
    }
}