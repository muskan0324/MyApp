package com.example.myapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.theme.MyAppTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class Day2Activity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                MyApp()
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyApp(){
    var name by remember {
        mutableStateOf("")
    }
    var phoneNo by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var buttonsState = remember {
        mutableStateListOf(false,false,false)
    }
    var termsAndCond by remember {
        mutableStateOf(false)
    }
    if(DataManager.currentPage.value==DataManager.Pages.FIRST){
        Screen1Form(getName = { name }, setName = { name = it },
            getPhoneno = { phoneNo }, setPhoneno = { phoneNo = it },
            getEmail = { email},setEmail= { email = it },
            getState={termsAndCond}, setState =  { termsAndCond = !termsAndCond },
            setButtonState = {index,state -> buttonsState[index]=state}
        , isTermsCondChecked = {termsAndCond },
            getButtonState = {index->buttonsState[index]})
    }
    else if(DataManager.currentPage.value==DataManager.Pages.SECOND){
        SecondScreen(getName = { name },getPhoneNo={phoneNo}) { email }
    }
    else
        ThirdScreen()
}

@Composable
fun SecondScreen(
    getName: () -> String, getPhoneNo: () -> String, getEmail: () -> String) {

    var menuExpanded by remember {
        mutableStateOf(false)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(color = Color.White)
//            .background(color = Color(158, 97, 135))
            .border(width = 1.dp, color = Color.White)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { DataManager.currentPage.value = DataManager.Pages.FIRST }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
                IconButton(
                    onClick = {
                        menuExpanded = !menuExpanded
                    }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",

                        )
                }
                if (menuExpanded) {
                    Box(contentAlignment = Alignment.TopEnd) {
                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false },
//                            modifier = Modifier.offset()
                        ) {
                            // Lazy list content
                            DropdownMenuItem(onClick = {
                                DataManager.currentPage.value = DataManager.Pages.THIRD
                            }, text = { Text("Settings") })
                            DropdownMenuItem(
                                onClick = { /* Handle menu item click */ },
                                text = { Text("Profile") })
                        }
                    }
                }
            }

                LazyColumn(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    items(1) { index ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp)
                                .background(color = Color.White),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(8.dp),
                            colors = CardDefaults.cardColors(Color.White)

                        ) {
                            text(getName, "Name")
                            text(getPhoneNo, "Phone Number")
                            text(getEmail, "Email")

                        }
                    }
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ThirdScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxSize()) {
            IconButton(onClick = { DataManager.currentPage.value = DataManager.Pages.SECOND}) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column() {
                    DateSelection()
                    TimeSelection()
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeSelection(){
    val selectedTime = remember {
        mutableStateOf(LocalTime.now())
    }
    val timePickerListener = TimePickerDialog.OnTimeSetListener { timePicker, i, j ->
        selectedTime.value=LocalTime.of(timePicker.hour,timePicker.minute)
    }
    var context= LocalContext.current

    Row() {
        OutlinedTextField(
            value = selectedTime.value.format(DateTimeFormatter.ISO_TIME),
            readOnly = true,
            onValueChange = {},
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        )
                IconButton(onClick = {
            TimePickerDialog(
                context,
               timePickerListener,
                selectedTime.value.hour,
                selectedTime.value.minute,
                true
            ).show()
        }) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "clock"
            )
        }

    }
}



@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSelection() {
    val context = LocalContext.current

    val selectedDate = remember { mutableStateOf(LocalDate.now()) }

    val datePickerListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        selectedDate.value = LocalDate.of(year, month + 1, dayOfMonth)
    }


    Row() {
                OutlinedTextField(
                    value = selectedDate.value.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")),
                    readOnly = true,
                    onValueChange = {},
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                )
                IconButton(onClick = {
                    DatePickerDialog(
                        context,
                        datePickerListener,
                        selectedDate.value.year,
                        selectedDate.value.monthValue - 1,
                        selectedDate.value.dayOfMonth
                    ).show()
                }) {
                    Icon(
                        imageVector = Icons.Default.DateRange,
                        contentDescription = "date"
                    )
                }

            }
}


@Composable
fun  text(value: () -> String?, label:String){
    Text("$label   :  ${value()}",
        style= TextStyle(color=Color.Black, fontSize = 20.sp),
        modifier=Modifier.padding(vertical = 3.dp, horizontal = 10.dp)
    )
}


//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview5() {
//    MyAppTheme {
//        MyApp()
//    }
//}