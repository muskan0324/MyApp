package com.example.myapp

import android.media.MediaPlayer
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapp.ui.theme.MyAppTheme
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.StyledPlayerView

class Day3Activity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Main()
            }
        }
    }
}
@Composable
fun Main(){
    var name by remember { mutableStateOf("") }
    var phoneNo by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var buttonsState = remember { mutableStateListOf(false,false,false) }
    var TermsAndChecked by remember { mutableStateOf(false) }
    var screen14text by remember { mutableStateOf("") }
    var textshowscreen1 by remember { mutableStateOf(false)}
    var showAlert by remember { mutableStateOf(true)}


    var getName = { name }
    var setName = { it:String->name = it }
    var getPhoneno = { phoneNo }
    var setPhoneno = { it:String->phoneNo = it }
    var getEmail = { email }
    var setEmail= { it:String->email = it }
    var getState={ TermsAndChecked }
    var setState =  { TermsAndChecked = !TermsAndChecked }
    var setButtonState = { index:Int,state:Boolean -> buttonsState[index]=state }
    var getButtonState = { index:Int->buttonsState[index] }
    var getscreen14text={screen14text}
    var setscreen14text={it:String->screen14text=it}
    var gettextshowscreen1={textshowscreen1}
    var settextshowscreen1={it:Boolean->textshowscreen1=it}
    var getshowalert={showAlert}
    var setshowalert={it:Boolean->showAlert=it}
    MyApp3(getName,setName,setPhoneno,getPhoneno,getEmail,setEmail,getState,setState,setButtonState,getButtonState,getscreen14text,setscreen14text
    ,gettextshowscreen1,settextshowscreen1,getshowalert,setshowalert)
}
@Composable
fun MyApp3(
    getName: () -> String,
    setName: (it: String) -> Unit,
    setPhoneno: (it: String) -> Unit,
    getPhoneno: () -> String,
    getEmail: () -> String,
    setEmail: (it: String) -> Unit,
    getState: () -> Boolean,
    setState: () -> Unit,
    setButtonState: (index: Int, state: Boolean) -> Unit,
    getButtonState: (index: Int) -> Boolean,
    getscreen14text: () -> String,
    setscreen14text: (String) -> Unit,
    gettextshowscreen1: () -> Boolean,
    settextshowscreen1: (Boolean) -> Unit,
    getshowalert: () -> Boolean,
    setshowalert: (Boolean) -> Unit
) {
    val navController= rememberNavController()
    NavHost(navController = navController,
        startDestination = "first-screen")
    {

        composable(route="first-screen"){
            MainContent1(
                getName = getName,
                setName = setName,
                setPhoneno = setPhoneno,
                getPhoneno = getPhoneno,
                getEmail = getEmail,
                setEmail = setEmail,
                getState = getState,
                setState = setState,
                setButtonState = setButtonState,
                getButtonState = getButtonState,
               navController =  navController,
                getscreen14text,
                gettextshowscreen1,
                getshowalert,
                setshowalert
            )
        }
        composable(route = "second-screen/{name}/{phoneNo}/{email}"){
            navBackStackEntry->
            val name=navBackStackEntry.arguments?.getString("name")
            val phoneNo=navBackStackEntry.arguments?.getString("phoneNo")
            val email=navBackStackEntry.arguments?.getString("email")
            secondScreen(getName = getName, getPhoneNo = getPhoneno,getEmail=getEmail,navController,
            name,phoneNo,email)
        }
        composable(route = "third-screen"){
            thirdScreen(navController)
        }
        composable(route="fourth-screen"){
            fourthScreen(setscreen14text,getscreen14text,gettextshowscreen1,settextshowscreen1,navController)
        }

    }
}
@Composable
fun MainContent1(
    getName: () -> String,
    setName: (it: String) -> Unit,
    setPhoneno: (it: String) -> Unit,
    getPhoneno: () -> String,
    getEmail: () -> String,
    setEmail: (it: String) -> Unit,
    getState: () -> Boolean,
    setState: () -> Unit,
    setButtonState: (index: Int, state: Boolean) -> Unit,
    getButtonState: (index: Int) -> Boolean,
    navController: NavHostController,
    getscreen14text: () -> String,
    gettextshowscreen1: () -> Boolean,
    getshowalert: () -> Boolean,
    setshowalert: (Boolean) -> Unit
) {
    Box(
        modifier=Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,

        ) {
        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TextFieldss(getName, setName, getPhoneno, setPhoneno, getEmail, setEmail)

            CheckboxTC1(setState, getState,setshowalert)
            buttons(
                setButtonState, isTermsCondChecked = getState, getButtonState,
                navController,getName,getEmail,getPhoneno
            )
            if (getshowalert())
                alertDialog(getState,setState,setshowalert)
            if (gettextshowscreen1())
                    Text(text = getscreen14text(), fontSize = 30.sp, fontWeight = FontWeight.Bold)

            }

        }
}
@Composable
fun VideoPlayer(navController: NavHostController){
    val context= LocalContext.current
    val exoPlayer=ExoPlayer.Builder(context).build()
        .also {
           exoPlayer ->
           val mediaItem= MediaItem.Builder()
               .setUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")
               .build()
            exoPlayer.setMediaItem(mediaItem)
            exoPlayer.prepare()
        }

   DisposableEffect(
    AndroidView(factory =
    {
        StyledPlayerView(context).apply {
            player = exoPlayer
        }
    })
   ){
       onDispose { exoPlayer.release() }
   }
}
@Composable
fun CheckboxTC1(setState: () -> Unit, getState: () -> Boolean, setshowalert: (Boolean) -> Unit) {
    Row(Modifier.padding(vertical = 10.dp)) {
        Checkbox(
            checked = getState(),
            onCheckedChange = { setState()
                setshowalert(!getState())
            },
        )
        Text("Please agree to Terms & Conditions",modifier= Modifier.padding(vertical = 12.dp))
    }
}
@Composable
fun buttons(
    setState: (index: Int, state: Boolean) -> Unit,
    isTermsCondChecked: () -> Boolean,
    getState: (index: Int) -> Boolean,
    navController: NavHostController,
    getName: () -> String?,
    getEmail: () -> String?,
    getPhoneno: () -> String?
){

    val context= LocalContext.current
    Box(contentAlignment = Alignment.Center) {
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Row() {
                Button(modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { navController.navigate("second-screen/${getName()}/${getEmail()}/${getPhoneno()}")
                    }, enabled = isTermsCondChecked()
                ) {
                    Text("SHOW")
                }
                Button(modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = { setState(1,!getState(1))
                              navController.navigate("third-screen")}, enabled = isTermsCondChecked()
                ) {
                    Text("MUSIC")
                }
                Button(modifier = Modifier.padding(15.dp),
                    shape = RoundedCornerShape(10.dp),
                    onClick = {navController.navigate("fourth-screen")
                    }, enabled = isTermsCondChecked()
                ) {
                    Text("PASS")
                }
            }
            }

        }
    }

@Composable
fun alertDialog(getState: () -> Boolean, setState: () -> Unit, setshowalert: (Boolean) -> Unit) {
    val context= LocalContext.current
    val at =  AlertDialog(
        onDismissRequest = {
        },

        title = { Text(text = "Terms & Conditions", color = Color.Black, fontSize = 20.sp) },
        text = { Text("Please agree to continue", color = Color.Black, fontSize = 15.sp) },
        confirmButton = {
            TextButton(
                onClick = {
                    Toast.makeText(context, "Agreed to Terms & Conditions", Toast.LENGTH_LONG)
                        .show()
                    setshowalert(false)
                    setState()
                }
            ) {
                Text("Agree", color = Color.Blue, fontSize = 18.sp)
            }

        },
        containerColor = Color.LightGray
    )
}
@Composable
fun secondScreen(
    getName: () -> String,
    getPhoneNo: () -> String,
    getEmail: () -> String,
    navController: NavHostController,
    name: String?,
    phoneNo: String?,
    email: String?
) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp)
            .background(color = Color.White)
            .border(width = 1.dp, color = Color.White)
            .wrapContentSize(Alignment.TopEnd)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigate("first-screen")}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
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
                        text({name}, "Name")
                        text({phoneNo}, "Phone Number")
                        text({email}, "Email")

                    }
                }
            }
        }
    }
}
@Composable
fun thirdScreen(navController: NavHostController) {
    val context= LocalContext.current
    var mp=MediaPlayer.create(context,R.raw.audio)
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center,
    ){

        Column(Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { navController.navigate("first-screen")}) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }


            Row(){
                IconButton(onClick = { mp.start()}) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play"
                    )
                }
                IconButton(onClick = { mp.pause()}) {
                    Icon(
                        painterResource(id = R.drawable.baseline_pause_24),
                        contentDescription = "Play"
                    )
                }
                IconButton(onClick = { mp.stop()
                mp=MediaPlayer.create(context,R.raw.audio)}) {
                    Icon(
                        painterResource(id = R.drawable.baseline_stop_24),
                        contentDescription = "Play"
                    )
                }
//


            }
            VideoPlayer(navController)
        }

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun fourthScreen(
    setscreen14text: (String) -> Unit,
    getscreen14text: () -> String,
    gettextshowscreen1: () -> Boolean,
    settextshowscreen1: (Boolean) -> Unit,
    navController: NavHostController
) {
    Box(modifier=Modifier.fillMaxSize() ,
        contentAlignment = Alignment.Center
    ){
        Column(modifier=Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { navController.navigate("first-screen") }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
            OutlinedTextField(
                value = getscreen14text(),
                onValueChange = { setscreen14text(it) },
                label = { Text("What's on your mind?") })
            Button(modifier = Modifier.padding(10.dp), onClick = {
                setscreen14text(getscreen14text())
                settextshowscreen1(true)
                navController.navigate("first-screen")
            }) {
                Text("Display on Home screen")
            }
        }

    }
}



@Composable
fun Greeting6(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview5() {
    MyAppTheme {
        Greeting6("Android")
    }
}