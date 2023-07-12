package com.example.myapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapp.models.response.MemesResponse
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class APIActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Api()
            }
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Api(){
    var apiService=APIService.getInstance()
    var scope= rememberCoroutineScope()
    var list:ArrayList<MemesResponse> = arrayListOf()

    var memeList by remember {
    mutableStateOf(list)
    }
    scope.launch {
        try{
            memeList=apiService.getMemes()
            Log.d("list",memeList.toString())
        }
        catch (ex:Exception){
            throw Exception(ex.message)
        }
    }

    Box(){
        Column(){
            if(memeList.isNotEmpty()) {
            Text(memeList[0].blank.toString() )

            }
//                item.id?.let { Text(memeList[0]) }
//                item.name?.let { Text(it) }
//                 Text(item.lines.toString())
//                Text(item.overlays.toString())
//                Text(item.styles.toString())
//                item.blank?.let { Text(it) }
//                item.example?.let { Text(it.toString()) }
//                item.source?.let { Text(it) }
//                item.self?.let { Text(it) }


        }
    }
}