package com.example.myapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapp.ui.theme.MyAppTheme

class InstaActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                InstaSignUp()
            }
        }
    }
}
@Composable
fun InstaSignUp(){
    Box(
        modifier= Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(vertical = 25.dp, horizontal = 40.dp),
        contentAlignment = Alignment.TopCenter
    ){
        Column(){
            Image(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp), painter = painterResource(
                id = R.drawable.instagram,), contentDescription = "Instagram Logo"
            , alignment = Alignment.Center
            )
            Text(
                text = "Sign up to see photos and vidoes from your friends.",
                fontWeight = FontWeight.Bold, fontSize = 20.sp,
                style = TextStyle(Color.DarkGray.copy(0.7f)), textAlign = TextAlign.Center
            )
            Button(
                onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color(40,72,164)),
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier
                    .height(65.dp)
                    .fillMaxWidth()
                    .padding(vertical = 13.dp)

                ){
                Row {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "facebook-logo",
                        Modifier.size(height = 25.dp, width = 40.dp)
                    )

                    Text("    Log in with Facebook", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                }
            }
            TextBetweenLines()
            Spacer(modifier = Modifier.height(7.dp))
            TextFields()
            TermsAndConditions()
//            Text(modifier = Modifier.padding(vertical = 5.dp), fontWeight = FontWeight.Bold, fontSize = 14.sp,
//                style = TextStyle(Color.DarkGray.copy(0.5f)), textAlign = TextAlign.Center,text="People who use our service may have uploaded your contact information to Instagram. Learn more")

            SignUpButton()

        }
    }
}

@Composable
fun SignUpButton(){
    Button(
        onClick = { /*TODO*/ }, colors = ButtonDefaults.buttonColors(Color(40,72,164)),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()


    ){
        Text(text="Sign Up", fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun TermsAndConditions(){
    var context= LocalContext.current

    ClickableText( text = buildAnnotatedString {
        append("People who use our service may have uploaded your contact information to Instagram. ")
        withStyle(style = SpanStyle(color = Color(40,72,164))) {
            append("Learn More")
        }
    }, onClick = {Toast.makeText(context,"Learn More",Toast.LENGTH_SHORT).show()
    }, style = TextStyle (fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray.copy(0.5f), textAlign = TextAlign.Center), modifier = Modifier.padding(vertical = 5.dp))


    ClickableText( text = buildAnnotatedString {
        append("By signing up, you agree to our ")
        withStyle(style = SpanStyle(color = Color(40, 72, 164))) {
            append("Terms, Privacy Policy")
        }
        append(" and ")
        withStyle(style = SpanStyle(color = Color(40, 72, 164))) {
            append("Cookies Policy")
        }
    }
        ,onClick = {var msg="Terms , Privacy & Cookies Policy"
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }, style = TextStyle (fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray.copy(0.5f), textAlign = TextAlign.Center), modifier = Modifier.padding(vertical = 5.dp))

}
@Composable
fun TextBetweenLines(){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = "OR",
            modifier = Modifier.padding(horizontal = 8.dp),
            style = TextStyle(color=Color.DarkGray.copy(0.7f)),
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp
        )
        Divider(
            color = Color.LightGray,
            thickness = 1.dp,
            modifier = Modifier.weight(1f)
        )

    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFields(){
    var fullname by remember {
        mutableStateOf("")
    }
    var phoneNoOrEmail by remember {
        mutableStateOf("")
    }
    var  username by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var modifier= Modifier
        .fillMaxWidth()
        .height(55.dp)
        .border(0.dp, shape = RoundedCornerShape(5.dp), color = Color.LightGray)

    var textStyle = TextFieldDefaults.textFieldColors(
        containerColor = Color.LightGray.copy(0.3f),
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent)
    Column (
        Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TextField( modifier = modifier
        ,
            colors = textStyle,
            value = phoneNoOrEmail,
            onValueChange = { phoneNoOrEmail = it },
            label = { Text(text="Mobile number or email address", fontSize = 15.sp, color=Color.DarkGray)},

        )

        Spacer(modifier = Modifier
            .height(5.dp)
            .background(Color.White))

        TextField(modifier = modifier
            ,value = fullname,
            onValueChange = { fullname = it },
            label = { Text("Full Name", fontSize = 15.sp, color=Color.DarkGray)},
            colors = textStyle

        )

        Spacer(modifier = Modifier
            .height(5.dp)
            .background(Color.White))

        TextField(modifier = modifier,value = username,
            onValueChange = { username = it },
            label = { Text("Username", fontSize = 15.sp, color=Color.DarkGray) },
            colors = textStyle
        )

        Spacer(modifier = Modifier
            .height(5.dp)
            .background(Color.White))

        TextField(modifier = modifier,value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontSize = 15.sp, color=Color.DarkGray) },
            colors = textStyle
        )
    }


//    TextField(value = , onValueChange = )
}
@Composable
fun Greeting3(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    MyAppTheme {
        InstaSignUp()
    }
}