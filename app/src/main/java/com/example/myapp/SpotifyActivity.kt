package com.example.myapp

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.myapp.models.response.SpotifyTrendingSongsResponse
import com.example.myapp.ui.theme.MyAppTheme
import com.example.myapp.viewmodels.SpotifyViewModel

class SpotifyActivity : ComponentActivity() {
    var spotifyViewModel:SpotifyViewModel= SpotifyViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                Songs(spotifyViewModel)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Songs(spotifyViewModel: SpotifyViewModel) {
    Box(
        Modifier
            .fillMaxSize()
            .background(color = Color.Black)
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter,){
        Column() {
            Row(horizontalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    modifier=Modifier.padding(vertical = 5.dp),
                    value = spotifyViewModel.getCountryCode(),
                    onValueChange = { spotifyViewModel.setCountryCode(it) },
                    textStyle = TextStyle(color=Color.White, fontWeight = FontWeight.Bold),
                    label = { Text("Enter your country",color=Color.White, fontWeight = FontWeight.Bold) },
                )

                Icon(
                    modifier = Modifier
                        .padding(vertical = 30.dp, horizontal = 15.dp)
                        .clickable { spotifyViewModel.getSpotifySongs() }
                        .size(30.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "searchIcon",
                    tint = Color.White
                )

                Log.d("songs", spotifyViewModel.spotifySongsList.toString())
            }
            if(spotifyViewModel.spotifySongsList.isNotEmpty()) {
                LazyColumn(
                    modifier=Modifier.background(color=Color.Black
                        ,shape=RoundedCornerShape(16.dp))) {
                    itemsIndexed(spotifyViewModel.spotifySongsList) { index, item ->
                        SongDisplay(item,spotifyViewModel)

                    }
                }
            }
        }

    }
}
@Composable
fun SongDisplay(item: SpotifyTrendingSongsResponse, spotifyViewModel: SpotifyViewModel){
    var trackId=getTrackId(item,spotifyViewModel)

    val mediaPlayer = MediaPlayer()


    Card(
            modifier = Modifier
                .padding(6.dp)
            ,
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(Color.DarkGray)


        ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
                .background(Color.DarkGray)
        ) {
            Image(
                painter = rememberImagePainter(data = item.trackMetadata?.displayImageUri,
                    builder = {
                        scale(Scale.FILL)
                    }),
                contentDescription = item.trackMetadata?.trackName,
                modifier = Modifier
                    .fillMaxHeight()
                    .size(130.dp)
                    .padding(horizontal = 10.dp)
                    .clickable {

                        var audioUri = trackId?.let { spotifyViewModel.getTrackDetails(it) }
                        if (!audioUri.isNullOrBlank()) {
                            try {
                                mediaPlayer.setDataSource(audioUri)
                                mediaPlayer.prepare()
                                mediaPlayer.start()

                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                        }
                    }
            )
            Column(verticalArrangement = Arrangement.Center,modifier=Modifier.padding(horizontal = 12.dp, vertical = 10.dp)) {


                textDisplay(item.trackMetadata?.trackName, "Track Name")
                textDisplay(
                    item.trackMetadata?.let { getArtistList(it.artists) },
                    "Artist Name"
                )
                textDisplay(item.trackMetadata?.releaseDate, "Release Date")
            }
        }
    }
}
fun getArtistList(artists: ArrayList<SpotifyTrendingSongsResponse.Artists>):String{
    var artist:String=""
    artists.forEachIndexed { index, item ->
        artist=artist+item.name+" ,"
    }
    return artist.substring(0,artist.length-2)
}
fun getTrackId(item: SpotifyTrendingSongsResponse, spotifyViewModel: SpotifyViewModel):String? {
    var trackUri= item.trackMetadata?.trackUri

    var trackId= trackUri?.let { trackUri.substring(it.lastIndexOf(":")+1) }


    return trackId
}
@Composable
fun  textDisplay(value:String?, label:String) {
    Text(
        "$label : $value",
        style = TextStyle( fontSize = 13.sp, fontWeight = FontWeight.SemiBold, letterSpacing = 1.5.sp),
        color=Color.White,
        modifier = Modifier.padding(vertical = 3.dp, horizontal = 10.dp)
    )
}
