package com.example.reconocimientodevoz

import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.example.reconocimientodevoz.ui.theme.ReconocimientoDeVozTheme

class MainActivity : ComponentActivity(), RecognitionListener {
    var dato by mutableStateOf("")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.RECORD_AUDIO),
            1
        )

        setContent {
            ReconocimientoDeVozTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ReconocimientoDeVoz("Android")
                }
            }
        }
    }

    override fun onReadyForSpeech(p0: Bundle?) {
        Log.i("reconocimiento", "onReadyForSpeech")
    }

    override fun onBeginningOfSpeech() {
        Log.i("reconocimiento", "onBeginningOfSpeech")
    }

    override fun onRmsChanged(p0: Float) {
        Log.i("reconocimiento", "onRmsChanged")
    }

    override fun onBufferReceived(p0: ByteArray?) {
        Log.i("reconocimiento", "onBufferReceived")
    }

    override fun onEndOfSpeech() {
        Log.i("reconocimiento", "onEndOfSpeech")
    }

    override fun onError(p0: Int) {
        Log.i("reconocimiento", "onError")
    }

    override fun onResults(results: Bundle?) {
        var resultados = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var primerResultado = resultados?.get(0)
        dato = primerResultado!!
    }

    override fun onPartialResults(results: Bundle?) {
        var resultados = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
        var primerResultado = resultados?.get(0)
        dato = primerResultado!!
    }

    override fun onEvent(p0: Int, p1: Bundle?) {
        Log.i("reconocimiento", "onEvent")
    }


    @Composable
    fun ReconocimientoDeVoz(name: String, modifier: Modifier = Modifier) {
        var speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this)
        speechRecognizer.setRecognitionListener(this)
        Column {
            Text(
                dato
            )
            Button(onClick = {
                var intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-MX")
                speechRecognizer.startListening(intent)
            }) {
                Text(
                    "comienza a dictar"
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ReconocimientoDeVozPreview() {
        ReconocimientoDeVozTheme {
            ReconocimientoDeVoz("Android")
        }
    }
}