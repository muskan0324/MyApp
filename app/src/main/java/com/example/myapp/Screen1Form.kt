package com.example.myapp

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Screen1Form(
    getName: () -> String,
    setName: (it: String) -> Unit,
    setPhoneno: (it: String) -> Unit,
    getPhoneno: () -> String,
    getEmail: () -> String,
    setEmail: (it: String) -> Unit,
    getState: () -> Boolean,
    setState: () -> Unit,
    setButtonState: (index: Int,state:Boolean) -> Unit,
    isTermsCondChecked: () -> Boolean,
    getButtonState: (index:Int)->Boolean
) {
    Box(
        modifier= Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            TextFieldss(getName,setName,getPhoneno,setPhoneno,getEmail,setEmail)

            CheckboxTC(setState, getState)
            Buttons(setButtonState, isTermsCondChecked = isTermsCondChecked ,getName,getEmail,getPhoneno, getButtonState)

//            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldss(
    getName: () -> String,
    setName: (it: String) -> Unit,
    getPhoneno: () -> String,
    setPhoneno: (it:String) -> Unit,
    getEmail: () -> String,
    setEmail: (it:String) -> Unit
) {
    OutlinedTextField(
        value = getName(),
        onValueChange = { setName(it) },
        label = { Text("Name") })

    OutlinedTextField(
        value = getPhoneno(),
        onValueChange = { setPhoneno(it) },
        label = { Text("Phone Number") })
    OutlinedTextField(
        value = getEmail(),
        onValueChange = { setEmail(it) },
        label = { Text("Email") })
}
@Composable
fun CheckboxTC(setState: () -> Unit, getState: () -> Boolean) {
    Row(Modifier.padding(vertical = 10.dp)) {
        Checkbox(
            checked = getState(),
            onCheckedChange = { setState()
                             },
        )
        Text("Terms & Conditions",modifier= Modifier.padding(vertical = 12.dp))
    }
}
@Composable
fun Buttons(setState:(index:Int,state:Boolean)->Unit,
            isTermsCondChecked:()->Boolean,
            getName: () -> String,
            getPhoneNo: () -> String,
            getEmail: () -> String,
            getState: (index:Int)->Boolean
){

    val context= LocalContext.current
    Box(contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Row() {
                Button(modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        setState(0,true)
                        if(getState(0)){
                            Toast.makeText(
                                context,
                                "${getName()} : ${getPhoneNo()} : ${getEmail()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }}, enabled = isTermsCondChecked()
                ) {
                    Text("TO")
                }
                Button(modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { setState(1,!getState(1)) }, enabled = isTermsCondChecked()
                ) {
                    Text("T")
                }
                Button(modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {
                        setState(2,true)
                        if(getState(2))
                            DataManager.currentPage.value = DataManager.Pages.SECOND
                    }, enabled = isTermsCondChecked()
                ) {
                    Text("LC")
                }
            }
            if (getState(1) && isTermsCondChecked()) {
                Row() {
                    Text(
                        modifier = Modifier.padding(horizontal = 80.dp),
                        text = " ${getName()} , ${getPhoneNo()}, ${getEmail()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold

                    )
                }
            }

        }
    }

}