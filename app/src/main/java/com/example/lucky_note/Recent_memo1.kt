package com.example.lucky_note

import android.app.Activity
import android.content.Intent
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast

class Recent_memo1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memoContent = intent.getStringExtra("memo_content") ?: ""
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
                RecentMemoScreen(memoContent) { resultMemo ->
                    val intent = Intent().apply {
                        putExtra("memo_content", resultMemo)
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecentMemoScreen(initialMemoContent: String, onSave: (String) -> Unit) {
    val contextDB = LocalContext.current
    val db = remember { AppDatabase.getDatabase(contextDB) }
    var memo1 by remember { mutableStateOf(initialMemoContent) }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val fontFamily = FontFamily(Font(R.font.noto_sans_kr_regular))

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
                    color = MaterialTheme.colorScheme.onBackground,
                    fontFamily = fontFamily
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
                                onSave(memo1)
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
                        onSave(memo1)
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
