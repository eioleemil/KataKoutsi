package fi.katakoutsi

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkGreen = Color(0xFF12372A)
private val LightGreen = Color(0xFFE5F0E9)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KataKoutsiApp()
        }
    }
}

@Composable
fun KataKoutsiApp() {
    var videoName by remember { mutableStateOf("Ei vielä valittua videota") }
    var feedback by remember {
        mutableStateOf("Suorituspalautteesi näkyy täällä harjoituksen jälkeen.")
    }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            videoName = "Mallivideo valittu"
            feedback = "Mallivideo on valittu. Seuraavaksi lisäämme oman suorituksen analysoinnin."
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
                        text = "🥋 KataKoutsi",
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Harjoittele. Tallenna. Kehity.",
                        color = Color(0xFFD6E9DC),
                        fontSize = 16.sp
                    )
                }

                Text(
                    text = "1. Valitse mallikata",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Valitse puhelimestasi video, jota haluat käyttää mallina.")
                        Text(
                            text = videoName,
                            color = Color.Gray
                        )
                        Button(
                            onClick = { videoPicker.launch("video/*") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = DarkGreen
                            )
                        ) {
                            Text("Valitse mallivideo")
                        }
                    }
                }

                Text(
                    text = "2. Oma kataharjoitus",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = LightGreen),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Kameraharjoitus",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            "Tähän lisätään seuraavaksi kameranäkymä, joka auttaa seuraamaan kata-liikkeitäsi."
                        )
                        OutlinedButton(
                            onClick = {
                                feedback = "Kameratoimintoa ei ole vielä lisätty. Seuraavassa kehitysvaiheessa rakennamme sen."
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Avaa kameraharjoitus")
                        }
                    }
                }

                Text(
                    text = "3. Valmentajan palaute",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGreen
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = feedback,
                            fontSize = 15.sp
                        )
                        Text(
                            text = "Tavoite: tunnistaa liikkeet ja antaa vinkkejä esimerkiksi asentoon, tasapainoon ja liikeratoihin.",
                            color = Color.Gray,
                            fontSize = 13.sp
                        )
                    }
                }

                Text(
                    text = "KataKoutsi • Harjoittele omaan tahtiin",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
