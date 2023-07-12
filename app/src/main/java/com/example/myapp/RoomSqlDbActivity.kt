package com.example.myapp

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.app.TimePickerDialog.OnTimeSetListener
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.jetpackroomdb.NewsChannelEntity
import com.example.myapp.jetpackroomdb.NewsChannelDAO
import com.example.myapp.jetpackroomdb.NewsChannelDatabase
import com.example.myapp.jetpackroomdb.NewsChannelHelper
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.time.LocalTime

class RoomSqlDbActivity : ComponentActivity() {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("CoroutineCreationDuringComposition")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContent {
               MyAppTheme() {
                   val newsChannelHelper=NewsChannelHelper()
                   val dbDAOInstance =   NewsChannelDatabase.getInstance(applicationContext).newschannelDAO()
                   DBApp(dbDAOInstance,newsChannelHelper)


//                        Column {
//
//                            Log.i("jvjghvhjvh", "onCreate: ")
//
//
//                            var s by remember {
//                                mutableStateOf(false)
//                            }
//                            var ret by remember { mutableStateOf(false) }
//
//                            if (s) {
//                                val scope = rememberCoroutineScope()
//
//                                scope.launch {
//
//                                    try {
//                                        db.insert(TodoEntity(itemName = "some new data", isDone = false))
//
//                                    } catch (ex: Exception) {
//                                        println("cancelled")
//                                    }
//                                }
//
//                            }
//                            if (ret) {
//                                val scope = rememberCoroutineScope()
//
//                                scope.launch {
//
//                                    try {
//                                        db.getAll()
//                                    } catch (ex: Exception) {
//                                        println("cancelled")
//                                    }
//                                }
//
//                            }
//                        }
                    }
                }
            }
        }


@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DBApp(dbDAOInstance: NewsChannelDAO, newsChannelHelper: NewsChannelHelper) {
    var channelName by remember {
        mutableStateOf("")
    }
    var programName by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()

    val selectedTime = remember {
        mutableStateOf(LocalTime.of(LocalTime.now().hour, LocalTime.now().minute))
    }
    var listener = OnTimeSetListener { timepicker, hh, mm ->
        selectedTime.value = LocalTime.of(hh, mm)
    }

    var timePicker = TimePickerDialog(
        LocalContext.current,
        listener,
        selectedTime.value.hour,
        selectedTime.value.minute,
        false
    )
    var modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 10.dp)

    var toast = Toast.makeText(
        LocalContext.current,
        "Program Details saved successfully",
        Toast.LENGTH_SHORT
    )

    val todoitemsrows by remember {
        derivedStateOf {
            runBlocking {
                withContext(Dispatchers.IO) {
                    dbDAOInstance.getByChannelName("SONY")
                }
            }
        }
    }
    var show by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
        ) {
            OutlinedTextField(
                modifier = modifier,
                value = channelName,
                onValueChange = { channelName = it },
                label = {
                    Text("Channel Name")
                })
            OutlinedTextField(
                modifier = modifier,
                value = programName,
                onValueChange = { programName = it },
                label = { Text("Program Name") })

            Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    value = selectedTime.value.toString(),
                    onValueChange = {},
                    label = { Text("Time") })
                IconButton(modifier = Modifier.padding(top = 15.dp, start = 10.dp),
                    onClick = { timePicker.show() }) {

                    Icon(
                        painter = painterResource(id = R.drawable.baseline_access_time_24),
                        contentDescription = "clock-icon",
                    )
                }
            }
            Button(modifier = modifier.padding(horizontal = 70.dp), onClick = {
                scope.launch {
                    newsChannelHelper.deleteItem(
                        10L, dbDAOInstance = dbDAOInstance)
                    channelName = ""
                    programName = ""
                    selectedTime.value = LocalTime.of(LocalTime.now().hour, LocalTime.now().minute)
                }
                toast.show()
            }) {
                Text(text = "Save", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            Button(modifier = modifier.padding(horizontal = 70.dp), onClick = { show = true }) {
                Text(text = "Retrieve", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            if (show) {
                todoitemsrows.forEach {
                    Text("${it.channelName} - ${it.programName} - ${it.telecastTime}")
                }


            }
        }
    }
}

