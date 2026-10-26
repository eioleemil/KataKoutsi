package fi.katakoutsi

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext

private val DarkGreen = Color(0xFF12372A)
private val LightGreen = Color(0xFFE5F0E9)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { KataKoutsiApp() }
    }
}

@Composable
fun KataKoutsiApp() {
    val context = LocalContext.current
    var youtubeLink by remember { mutableStateOf("") }
    var videoName by remember { mutableStateOf("Ei valittua videotiedostoa") }
    var feedback by remember {
        mutableStateOf("Valitse mallivideo aloittaaksesi.")
    }

    val videoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            videoName = uri.lastPathSegment ?: "Videotiedosto valittu"
            feedback = "Video valittu. Sen liikkeiden automaattinen analysointi lisätään myöhemmin."
        }
    }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFFF5F7F5)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(DarkGreen, RoundedCornerShape(20.dp))
                        .padding(22.dp)
                ) {
                    Text(
                        "🥋 KataKoutsi",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "Harjoittele. Tallenna. Kehity.",
                        color = Color(0xFFD6E9DC)
                    )
                }

                Text(
                    "1. Valitse mallikata",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            "YouTube-mallivideo",
                            fontWeight = FontWeight.SemiBold
                        )
                        OutlinedTextField(
                            value = youtubeLink,
                            onValueChange = { youtubeLink = it },
                            modifier = Modifier.fillMaxWidth(),
                            label = { Text("Liitä YouTube-linkki") },
                            placeholder = {
                                Text("https://www.youtube.com/watch?v=...")
                            },
                            singleLine = true
                        )
                        Button(
                            onClick = {
                                val link = youtubeLink.trim()
                                val valid = runCatching {
                                    val uri = Uri.parse(link)
                                    (uri.scheme == "https" || uri.scheme == "http") &&
                                        (uri.host == "youtube.com" ||
                                         uri.host == "www.youtube.com" ||
                                         uri.host == "m.youtube.com" ||
                                         uri.host == "youtu.be")
                                }.getOrDefault(false)

                                if (valid) {
                                    val intent = Intent(
                                        Intent.ACTION_VIEW,
                                        Uri.parse(link)
                                    )
                                    context.startActivity(intent)
                                    feedback = "Mallivideo avattiin YouTubessa tai selaimessa."
                                } else {
                                    feedback = "Tarkista linkki. Liitä kelvollinen YouTube-videon osoite."
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkGreen
                            )
                        ) {
                            Text("Avaa YouTube-video")
                        }

                        HorizontalDivider()

                        Text(
                            "Tai valitse video puhelimesta",
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            videoName,
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                        OutlinedButton(
                            onClick = { videoPicker.launch("video/*") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Valitse ladattu video")
                        }
                    }
                }

                Text(
                    "2. Oma kataharjoitus",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = LightGreen
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "Kameraharjoitus",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Kameraseuranta ja kehon asentojen tunnistus lisätään seuraavassa kehitysvaiheessa."
                        )
                    }
                }

                Text(
                    "3. Valmentajan palaute",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Text(
                        text = feedback,
                        modifier = Modifier.padding(16.dp),
                        fontSize = 15.sp
                    )
                }

                Text(
                    "KataKoutsi • Harjoittele omaan tahtiin",
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        }
    }
}
