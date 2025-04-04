package com.example.ejerciciotextoeditable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var text by remember { mutableStateOf("") }
    var selectedFont by remember { mutableStateOf(FontFamily.SansSerif) }
    var selectedColor by remember { mutableStateOf(Color.Black) }
    var showFontMenu by remember { mutableStateOf(false) }
    var showColorMenu by remember { mutableStateOf(false) }
    var confirmedTexts by remember { mutableStateOf(listOf<Triple<String, FontFamily, Color>>()) }
    var formattedText by remember { mutableStateOf("") }
    var appliedFont by remember { mutableStateOf(FontFamily.SansSerif) }
    var appliedColor by remember { mutableStateOf(Color.Black) }

    val fontOptions = listOf(
        FontFamily.SansSerif,
        FontFamily.Serif,
        FontFamily.Monospace,
        FontFamily.Cursive,
    )

    val colorOptions = listOf(
        Color.Black to "Negro",
        Color.Red to "Rojo",
        Color.Blue to "Azul",
        Color.Green to "Verde",
        Color.Magenta to "Magenta"
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {

    Image(
        painter = painterResource(id = R.drawable.valentine1),
        contentDescription = "Fondo de la aplicación",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Ingresar el texto") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            DropdownSelector(
                label = "Cambiar Fuente",
                options = fontOptions,
                onSelect = { selectedFont = it},
                showMenu = showFontMenu,
                onToggle = { showFontMenu = !showFontMenu }
            )
            Spacer(modifier = Modifier.width(16.dp))
            DropdownSelectorWithLabels(
                label = "Cambiar Color",
                options = colorOptions,
                onSelect = { selectedColor = it.first },
                showMenu = showColorMenu,
                onToggle = { showColorMenu = !showColorMenu }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            formattedText = text
            appliedFont = selectedFont
            appliedColor = selectedColor
        },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4FBAD7))
        ) {
            Text("Aplicar cambios")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { confirmedTexts = confirmedTexts + Triple(text, selectedFont, selectedColor) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC5))
        ){
            Text("Confirmar texto")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            Text(text = formattedText, fontSize = 30.sp, color = appliedColor, fontFamily = appliedFont)
        }
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(modifier = Modifier
                .wrapContentSize()
                .background(Color.White, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                confirmedTexts.forEach { (savedText, savedFont, savedColor) ->
                    Text(
                        text = savedText,
                        fontSize = 30.sp,
                        color = savedColor,
                        fontFamily = savedFont
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
    }
}

@Composable
fun <T> DropdownSelector(label: String, options: List<T>, onSelect: (T) -> Unit, showMenu: Boolean, onToggle: () -> Unit) {
    Column {
        Button(onClick = onToggle) {
            Text(label)
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = onToggle) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.toString()) },
                    onClick = {
                        onSelect(option)
                        onToggle()
                    }
                )
            }
        }
    }
}

@Composable
fun DropdownSelectorWithLabels(label: String, options: List<Pair<Color, String>>, onSelect: (Pair<Color, String>) -> Unit, showMenu: Boolean, onToggle: () -> Unit) {
    Column {
        Button(onClick = onToggle) {
            Text(label)
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = onToggle) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.second) },
                    onClick = {
                        onSelect(option)
                        onToggle()
                    }
                )
            }
        }
    }
}


