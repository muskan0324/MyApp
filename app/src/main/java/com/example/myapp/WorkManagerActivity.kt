package com.example.myapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.workerClass.MyWorker
import java.time.Duration
import java.util.concurrent.TimeUnit

class WorkManagerActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var workManager =WorkManager.getInstance(applicationContext)
                    val constraints =
                        Constraints.Builder().setRequiresBatteryNotLow(requiresBatteryNotLow = true)
                            .build()
                    // val taskData = Data.Builder().putString("MESSAGE_STATUS", "Notify Done.").build()


                    val request = OneTimeWorkRequest.Builder(MyWorker::class.java).setConstraints(constraints)
                        .build()
                    val periodicRequest=PeriodicWorkRequest.Builder(MyWorker::class.java, repeatInterval = 15L,TimeUnit.MINUTES).build()
                    var bt by remember { mutableStateOf(false) }
                    if (bt) {
                        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("p1",ExistingPeriodicWorkPolicy.KEEP,periodicRequest)
                        workManager.getWorkInfoByIdLiveData(request.id).observe(this){
                                println("========== Work status: $it.status  \n")
                            }


                    }
                    WorkManager.getInstance(applicationContext).cancelAllWork()
                    Column() {
                        Button(onClick = {
                            bt = !bt
                        }) {
                            Text(text = "show notification!")
                        }
                    }
                }
            }
        }
    }
}

