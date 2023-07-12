package com.example.myapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.jetpackroomdb.dao.AccountDAO
import com.example.myapp.jetpackroomdb.dbInstance.AccountDatabase
import com.example.myapp.jetpackroomdb.entity.AccountEntity
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignInSignUP : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                var scope= rememberCoroutineScope()
                var dbInstance=AccountDatabase.getInstance(applicationContext).accountDAO()
                MainApp(scope,dbInstance)
            }
        }
    }
}
@Composable
fun MainApp(scope: CoroutineScope, dbInstance: AccountDAO) {
    var navController= rememberNavController()
    NavHost(navController = navController,
        startDestination = "sign-in"){
        composable(route="sign-in"){
            SignInScreen(scope,dbInstance,navController)
        }
        composable(route="sign-up"){
            SignUpScreen(scope,dbInstance,navController)
        }
        composable(route="home/{mobileNo}"){
                navBackStackEntry->
                     var mobileNo=navBackStackEntry.arguments?.getString("mobileNo")
                    if(mobileNo!=null)
                     HomeScreen(mobileNo,scope,dbInstance,navController)
        }
    }



}

//@SuppressLint("CoroutineCreationDuringComposition")
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    mobileNo: String,
    scope: CoroutineScope,
    dbInstance: AccountDAO,
    navController: NavHostController
) {

    var result by remember {
        mutableStateOf(AccountEntity(0,"","","",""))// Initialize with null or empty list as per your data type
    }
    scope.launch {
        try {
            result = dbInstance.getByMobileNo(mobileNo)
        } catch (ex: Exception) {
            println("cancelled")
        }
    }



    val context= LocalContext.current

    Box(
        Modifier
            .fillMaxSize()
            .padding(20.dp), contentAlignment = Alignment.TopEnd
    ) {
        Column(Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = "Welcome, ${mobileNo}",
                fontWeight = FontWeight.Bold,
                color=Color.Red,
                fontSize = 20.sp
            )
            Text(
                modifier = Modifier.padding(20.dp),
                text = "Details as follows...",
                fontSize = 20.sp
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 100.dp)
                    .background(color = Color.White),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(Color.White),
            )
            {
                text1(result.name, "Name")
                text1(result.mobileNo, "Mobile No")
                text1(result.location, "Location")
            }
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter)
            {
                Button(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 100.dp)
                        .size(150.dp, 50.dp),
                onClick = {
                    navController.navigate("sign-in")
                    Toast.makeText(context,"Signed Out Succesfully",Toast.LENGTH_SHORT).show()
                          } ,
                    colors = ButtonDefaults.buttonColors(Color.Green.copy(0.3f))
                    )
                    {
                        Text("Sign Out", color=Color.DarkGray, fontSize = 20.sp)
                    }
            }

        }
    }
}
@Composable
fun  text1(value: String, label:String){
    Text("$label   :  ${value}",
        style= TextStyle(color=Color.Black, fontSize = 20.sp),
        modifier=Modifier.padding(vertical = 15.dp, horizontal = 10.dp)
    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(scope: CoroutineScope, dbInstance: AccountDAO, navController: NavHostController) {
    var mobileNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var signin by remember { mutableStateOf(false) }
    var toastMsgSignIn by remember { mutableStateOf("") }

    var signInStatus by remember { mutableStateOf(false) }
    var context= LocalContext.current
    var list by remember {
        mutableStateOf(AccountEntity(0,"","","",""))
    }
//    var list = remember {
//        mutableListOf<AccountEntity>()}
    if (signin) {

        scope.launch {
            try {
                list = dbInstance.getByMobileNo(mobileNo)
                if (list==null) {
                    toastMsgSignIn = "User Account doesn't exist.Please Signup to continue."
                    Toast.makeText(context,toastMsgSignIn,Toast.LENGTH_SHORT).show()
                } else {
                    if (password != list.password)
                        toastMsgSignIn = "Incorrect Password."
                    else{
                        toastMsgSignIn="Signed in successfully"
                        signInStatus=true
                    }
                    Toast.makeText(context,toastMsgSignIn,Toast.LENGTH_SHORT).show()
                }
                signin=false

            } catch (ex: Exception) {
                throw Exception(ex.message)
            }

        }
//        Log.d("list",list.toString())

    }



    Box(
        Modifier
            .fillMaxSize()
            .padding(10.dp), contentAlignment = Alignment.Center) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = mobileNo,
                onValueChange = { mobileNo = it },
                label = { Text("Mobile No.",fontSize = 20.sp,color=Color.DarkGray) })
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password",fontSize = 20.sp,color=Color.DarkGray) })
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 40.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    signin = true
                },
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp), colors = ButtonDefaults.buttonColors(Color.Green.copy(0.3f))

                ) {
                    Text("Sign In",fontSize = 20.sp,color=Color.DarkGray)
                }
                Button(onClick = { navController.navigate("sign-up") },
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 5.dp)

                , colors = ButtonDefaults.buttonColors(Color.Green.copy(0.3f))) {
                    Text("Sign Up", fontSize = 20.sp,color=Color.DarkGray)
                }
                if (signInStatus) {
                    signInStatus = false
                    navController.navigate("home/${mobileNo}")
                }
                }
            }
        }
}
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(scope: CoroutineScope, dbInstance: AccountDAO, navController: NavHostController) {
    var mobileNo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var insert by remember { mutableStateOf(false) }

    var signedUpStatus by remember { mutableStateOf(false) }

    if (insert) {
        scope.launch {
            try {
                dbInstance.insert(
                    AccountEntity(
                        name = name,
                        mobileNo = mobileNo,
                        password = password,
                        location = location
                    )
                )
                insert = false
                signedUpStatus = true
            } catch (ex: Exception) {
                throw Exception(ex.message)
            }
        }
    }
    var allMandatoryFieldsFilled =
        mobileNo.isNotBlank() && password.isNotBlank() && location.isNotBlank() && name.isNotBlank()


    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = {
                    Row() {
                        Text("Name")
                        Text("*", color = Color.Red)
                    }
                },
                modifier = Modifier.padding(vertical = 5.dp)
            )
            OutlinedTextField(
                value = mobileNo,
                onValueChange = { mobileNo = it },
                label = {
                    Row() {
                        Text("Mobile No.")
                        Text("*", color = Color.Red)
                    }
                },
                modifier = Modifier.padding(vertical = 5.dp)
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = {
                    Row() {
                        Text("Location")
                        Text("*", color = Color.Red)
                    }
                },
                modifier = Modifier.padding(vertical = 5.dp)
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = {
                    Row() {
                        Text("Password")
                        Text("*", color = Color.Red)
                    }
                },
                modifier = Modifier.padding(vertical = 5.dp)
            )

            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter)
            {
                Button(
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 100.dp)
                        .size(150.dp, 50.dp),
                    onClick = {
                        insert = true
                    },
                    colors = ButtonDefaults.buttonColors(Color.Green.copy(0.3f)),
                    enabled = allMandatoryFieldsFilled
                )
                {
                    Text("Sign Up", color = Color.DarkGray, fontSize = 20.sp)
                }
            }

            if (signedUpStatus) {
                signedUpStatus = false
                navController.navigate("home/${mobileNo}")
            }
        }
    }
}



