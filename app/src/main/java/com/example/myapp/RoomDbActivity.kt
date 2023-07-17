package com.example.myapp

import android.accounts.Account
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.ColumnScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapp.jetpackroomdb.dbInstance.AccountDatabase
import com.example.myapp.jetpackroomdb.entity.AccountEntity
import com.example.myapp.ui.theme.ui.theme.MyAppTheme
import kotlinx.coroutines.launch

class RoomDbActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                UI()
            }
        }
    }
}
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UI(){

    var mobileNo by remember{ mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var getpassword by remember { mutableStateOf("") }
    var mobileNo2 by remember { mutableStateOf("") }

    var mobileNo3 by remember { mutableStateOf("") }
    var newpassword by remember { mutableStateOf("") }

    var showPassword by remember { mutableStateOf(false) }
    var id by remember { mutableStateOf("") }
    var dbInstance=AccountDatabase.getInstance(LocalContext.current).accountDAO()
    var scope= rememberCoroutineScope()

    var insert by remember { mutableStateOf(false) }
    var getpass by remember { mutableStateOf(false) }
    var updatepass by remember { mutableStateOf(false) }
    var deleteAccount by remember { mutableStateOf(false) }
    var deleteAll by remember { mutableStateOf(false) }


    if(insert){
        scope.launch {
            try {
                dbInstance.insert(AccountEntity(mobileNo=mobileNo, password = password,name="", location  =""))
                mobileNo=""
                password=""
                insert=false
            }
            catch (ex : Exception ){
                throw Exception(ex.message)
            }
        }
    }
    if(getpass){
        scope.launch {
            try {
                getpassword=dbInstance.getPassword(mobileNo2)
            }
            catch (ex : Exception ){
                throw Exception(ex.message)
            }
        }

    }
    if(updatepass){
        scope.launch {
            try{
                dbInstance.updatePassword(mobileNo3,newpassword)
                updatepass=false
            }
            catch (ex : Exception ){
                throw Exception(ex.message)
            }
        }
    }

    if(deleteAccount){
        scope.launch {
            try{
                dbInstance.deleteEntry(AccountEntity(id.toLong(),"","","",""))
            }
            catch (ex : Exception ){
                throw Exception(ex.message)
            }

        }
    }
    if(deleteAll){
        scope.launch {
            try{
                dbInstance.deleteAll()
            }
            catch (ex : Exception ){
                throw Exception(ex.message)
            }

        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp),contentAlignment = Alignment.Center){
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        , verticalArrangement = Arrangement.Center) {
            OutlinedTextField(value = mobileNo, onValueChange = {mobileNo=it}, label ={ Text("Mobile No.")})
            OutlinedTextField(value = password, onValueChange = {password=it}, label ={ Text("Password")})
            Button(onClick = {insert=true}){
                Text("SAVE")
            }
            OutlinedTextField(value = mobileNo2, onValueChange = {mobileNo2=it}, label ={ Text("Mobile No.")})
            Button(onClick = {getpass=!getpass}){
                Text("GET PASSWORD")
            }
            if(getpass){
                Text(getpassword)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                OutlinedTextField(modifier=Modifier.weight(1f),value = mobileNo3, onValueChange = {mobileNo3=it}, label ={ Text("Mobile No.")})
                OutlinedTextField(
                    modifier=Modifier.weight(1f),
                    value = newpassword,
                    onValueChange = { newpassword = it },
                    label = { Text("New Password") })


            }
            Button(onClick = {updatepass=true}){
                Text("UPDATE PASSWORD")
            }
            Row(){
                OutlinedTextField(modifier=Modifier.weight(1f),value = id, onValueChange = {id=it}, label ={ Text("Id")})
                Button(onClick = {deleteAccount=true}){
                    Text("DELETE")
                }
            }
            Button(onClick = {deleteAll=true}){
                Text("DELETE ALL")
            }




        }
    }

}
