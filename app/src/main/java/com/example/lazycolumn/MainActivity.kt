package com.example.lazycolumn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lazycolumn.ui.theme.LazyColumnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LazyColumnTheme {
                TelaPrincipal(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeDrawingPadding()
                )
            }
        }
    }
}
data class Tarefa(
    val nome: String,
    val descricao: String,
    var concluido: MutableState<Boolean>
)

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier) {

    var nome by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    val listaTarefas = remember { mutableStateListOf<Tarefa>() }

    Surface (
        color = Color(0xC95E19FF)
    ){

        Column(
            modifier = modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(listaTarefas) { tarefa ->
                    CardTarefa(tarefa)
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome da tarefa") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = descricao,
                onValueChange = { descricao = it },
                label = { Text("Descrição da tarefa") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    listaTarefas.add(Tarefa(nome, descricao, mutableStateOf(false)))
                    nome = ""
                    descricao = ""
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                enabled = nome.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}

@Composable
fun CardTarefa(tarefa: Tarefa) {

    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row (
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(modifier = Modifier.weight(1f)) {

                Text(
                    text = tarefa.nome,
                    fontWeight = FontWeight.Bold,
                    color = if (tarefa.concluido.value)
                        Color(0xFF7E57C2)
                    else
                        Color.Black,
                    style = if (tarefa.concluido.value)
                        MaterialTheme.typography.bodyLarge.copy(
                            textDecoration = TextDecoration.LineThrough
                        )
                    else
                        MaterialTheme.typography.bodyLarge
                )

                Divider(
                    color = Color.Black,
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = tarefa.descricao
                )
            }

            Checkbox(
                checked = tarefa.concluido.value,
                onCheckedChange = {
                    tarefa.concluido.value = it
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFD8B4FE)
                )
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LazyColumnTheme {
        TelaPrincipal()
    }
}