package com.sevenseas.monocalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sevenseas.monocalculator.ui.theme.DarkerCelestialBlue
import com.sevenseas.monocalculator.ui.theme.DarkerSaffron
import com.sevenseas.monocalculator.ui.theme.MonoCalculatorTheme
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonoCalculatorTheme(
                darkTheme = true,
                dynamicColor = false
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CalculatorApp()
                }
            }
        }
    }
}

@Composable
fun CalculatorApp() {
    UserInterface()
}

@Composable
fun UserInterface() {
    var input by remember { mutableStateOf("0") }

    val buttons = listOf(
        "AC", "C", "%", "÷",
        "7", "8", "9", "✖",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        " ", "0", ".", "=",
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = input,
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            style = MaterialTheme.typography.displayMedium.copy(
                textAlign = TextAlign.End
            ),
            color = Color.White
        )

        buttons.chunked(4).forEach { rowButtons ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                rowButtons.forEach { label ->
                    Button(
                        onClick = {
                            when (label) {
                                "AC" -> input = "0"
                                "C" -> input = if (input.length > 1) input.dropLast(1) else "0"
                                "%" -> input += "%"
                                "÷" -> input += "/"
                                "7" -> input = if (input == "0") "7" else input + "7"
                                "8" -> input = if (input == "0") "8" else input + "8"
                                "9" -> input = if (input == "0") "9" else input + "9"
                                "✖" -> input += "*"
                                "4" -> input = if (input == "0") "4" else input + "4"
                                "5" -> input = if (input == "0") "5" else input + "5"
                                "6" -> input = if (input == "0") "6" else input + "6"
                                "-" -> input += "-"
                                "1" -> input = if (input == "0") "1" else input + "1"
                                "2" -> input = if (input == "0") "2" else input + "2"
                                "3" -> input = if (input == "0") "3" else input + "3"
                                "+" -> input += "+"
                                "0" -> input = if (input == "0") "0" else input + "0"
                                "." -> input += "."
                                "=" -> {
                                    try {
                                        val expression = ExpressionBuilder(input).build()
                                        val result = expression.evaluate()
                                        input = if (result % 1 == 0.0) {
                                            result.toInt().toString()
                                        } else {
                                            result.toString()
                                        }
                                    } catch (_: Exception) {
                                        input = "Error"
                                    }
                                }

                            }
                        },
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f),
                        shape = RectangleShape,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = when (label) {
                                "AC", "C", "%", "÷", "✖", "-", "+", "=" -> MaterialTheme.colorScheme.primary
                                else -> MaterialTheme.colorScheme.secondary
                            },
                            contentColor = MaterialTheme.colorScheme.background
                        ),
                        border = BorderStroke(1.dp, when (label) {
                            "AC", "C", "%", "÷", "✖", "-", "+", "=" -> DarkerSaffron
                            else -> DarkerCelestialBlue
                        })
                    ) {
                        Text(
                            text = label,
                            fontSize = 35.sp
                        )
                    }
                }
            }
        }
    }
}