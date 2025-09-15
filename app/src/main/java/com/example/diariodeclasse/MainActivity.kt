package com.example.diariodeclasse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diariodeclasse.data.Aluno
import com.example.diariodeclasse.data.DataSource
import com.example.diariodeclasse.ui.theme.DiarioDeClasseTheme
import kotlinx.coroutines.sync.Mutex
import kotlin.math.exp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiarioDeClasseTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        DiarioDeClasseTopBar()
                    }
                ) { innerPadding ->
                    DiarioDeClasseApp(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .statusBarsPadding()
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun DiarioDeClassePreviewDark() {
    DiarioDeClasseTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            DiarioDeClasseApp(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun DiarioDeClasseApp(
    modifier: Modifier = Modifier
) {
    val layoutDirection = LocalLayoutDirection.current
    var expanded by remember { mutableStateOf(false) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            ),
    ) {
        ListaDeAlunos(
            modifier = Modifier,
            listaDeAlunos = DataSource().carregarAlunos()
        )
    }
}

@Composable
fun ListaDeAlunos(
    modifier: Modifier = Modifier,
    listaDeAlunos: List<Aluno>
) {
    LazyColumn(modifier = modifier) {
        items(listaDeAlunos) { aluno ->
            CardAluno(
                modifier = modifier,
                fotoAluno = aluno.foto,
                nomeAluno = aluno.nome,
                cursoAluno = aluno.curso
            )
        }
    }
}

@Composable
fun CardAluno(
    modifier: Modifier = Modifier,
    @DrawableRes fotoAluno: Int,
    nomeAluno: String,
    cursoAluno: String
) {
    var expandir by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = fotoAluno),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = nomeAluno
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = cursoAluno
                )

            }

            DetalhesAlunoButton(
                onClick = { expandir = !expandir },
                expanded = true,
                modifier = modifier
                    .weight(1.5f)
                    .wrapContentSize(align = Alignment.CenterEnd)
            )
        }

        if (expandir)
            DetalhesAluno(

            )
    }
}

@Composable
private fun DetalhesAlunoButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier

) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
    ) {
        Icon(
            imageVector = Icons.Filled.ExpandMore,
            contentDescription = stringResource(R.string.expand_button_content_description),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}


@Composable
fun DetalhesAluno() {
    Column {
        Text(
            "Nota: 100"
        )
        Text(
            "Faltas: 12%"
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiarioDeClasseTopBar(modifier: Modifier = Modifier) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                "Diario de Classe"
            )

        },
        modifier = Modifier
    )

}