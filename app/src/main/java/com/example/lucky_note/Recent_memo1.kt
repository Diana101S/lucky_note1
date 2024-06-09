package com.example.lucky_note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast

class Recent_memo1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF4CAF50),  // 초록색
                    onPrimary = Color.White,
                    secondary = Color(0xFF8BC34A),  // 밝은 초록색
                    onSecondary = Color.White,
                    background = Color(0xFFF0F0F0),  // 밝은 회색
                    onBackground = Color.Black,
                    surface = Color.White,
                    onSurface = Color.Black
                )
            ) {
                RecentMemoScreen { finish() }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentMemoScreen(onBackClick: () -> Unit) {
    val contextDB = LocalContext.current
    val db = remember { AppDatabase.getDatabase(contextDB) }
    var memo1 by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    // Load memo content from the database when the composable is first displayed
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            memo1 = db.memoDao().getMemo1() ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Memo Details", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Your Memo:",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(10.dp))
                TextField(
                    value = memo1,
                    onValueChange = { newMemo ->
                        memo1 = newMemo
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        scope.launch(Dispatchers.IO) {
                            db.memoDao().insertMemo(Memo(content = memo1))
                            scope.launch(Dispatchers.Main) {
                                Toast.makeText(context, "Memo saved", Toast.LENGTH_SHORT).show()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Save", color = MaterialTheme.colorScheme.onPrimary)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        onBackClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Back", color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun RecentMemoScreenPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF4CAF50),  // 초록색
            onPrimary = Color.White,
            secondary = Color(0xFF8BC34A),  // 밝은 초록색
            onSecondary = Color.White,
            background = Color(0xFFF0F0F0),  // 밝은 회색
            onBackground = Color.Black,
            surface = Color.White,
            onSurface = Color.Black
        )
    ) {
        RecentMemoScreen {}
    }
}
ㅍ