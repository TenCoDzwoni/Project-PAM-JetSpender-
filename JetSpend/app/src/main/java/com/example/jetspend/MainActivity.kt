package com.example.jetspend

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetspend.ui.theme.JetSpendTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetSpendTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    JetSpend()
                }
            }
        }
    }
}

@SuppressLint("MutableCollectionMutableState")
@Composable
fun JetSpend() {
    val Rozrywka = "rozrywka"
    val Jedzenie = "jedzenie"
    val Transport = "transport"

    var id by remember { mutableStateOf("0") }
    var inputNumber by remember { mutableStateOf("0") }
    var kategoria by remember { mutableStateOf(Rozrywka) }
    var licznik by remember { mutableIntStateOf(1) }
    var inExpended by remember { mutableStateOf(false)}
    var SpendRozrywka by remember { mutableIntStateOf(0) }
    var SpendJedzenie by remember { mutableIntStateOf(0) }
    var SpendTransport by remember { mutableIntStateOf(0) }
    var SpendSum by remember { mutableIntStateOf(0) }
    var Spends by remember { mutableStateOf(mutableListOf(Triple(0, Rozrywka, 0))) }
    fun createSpend(licznik: Int){
        Spends.add(Triple(licznik, kategoria, inputNumber.toInt()))
        when (kategoria){
            "rozrywka" ->{
                SpendRozrywka+=inputNumber.toInt()
            }
            "jedzenie" ->{
                SpendJedzenie+=inputNumber.toInt()
            }
            "transport" ->{
                SpendTransport+=inputNumber.toInt()
            }
        }
        SpendSum+=inputNumber.toInt()
    }

    fun deleteSpend(id: Int){
        for((szukaj, wydatek) in Spends.withIndex()){
            if (wydatek.first == id){
                when (wydatek.second){
                    "rozrywka" ->{
                        SpendRozrywka-=wydatek.third
                    }
                    "jedzenie" ->{
                        SpendJedzenie-=wydatek.third
                    }
                    "transport" -> {
                        SpendTransport -= wydatek.third
                    }
                }
                SpendSum-=wydatek.third
                Spends.removeAt(szukaj)
                break
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

    ){
        Spacer(modifier = Modifier.height(50.dp))
        Text(text = "Kwota:")
        OutlinedTextField(value = inputNumber, onValueChange = {inputNumber = it})
        Spacer(modifier = Modifier.height(20.dp))

            Text(text = "Kategoria:")
            Box {

                Button(onClick = { inExpended= true }) {
                    Text(text = kategoria)
                    Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "arrow")
                }
                DropdownMenu(expanded = inExpended, onDismissRequest = { inExpended=false }) {
                    DropdownMenuItem(text = { Text(text = Rozrywka)}, onClick = { inExpended = false
                        kategoria= Rozrywka })
                    DropdownMenuItem(text = { Text(text = Jedzenie)}, onClick = { inExpended = false
                        kategoria= Jedzenie})
                    DropdownMenuItem(text = { Text(text = Transport)}, onClick = { inExpended = false
                        kategoria= Transport})
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box {
                Button(onClick = { createSpend(licznik)
                licznik+=1}) {
                    Text(text = "Dodaj")
                }

            }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Rozrywka: $SpendRozrywka")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Jedzenie: $SpendJedzenie")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Transport: $SpendTransport")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Suma: $SpendSum")
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "ID do usunięcia:")
        OutlinedTextField(value = id, onValueChange = {id = it})
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { deleteSpend(id.toInt()) }) {
            Text(text = "Usuń")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(text = "Wydatki: $Spends")
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JetSpendTheme {

    }
}