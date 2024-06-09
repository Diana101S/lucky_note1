package com.example.lucky_note

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MemoScreen()
            }
        }
    }
}

@Composable
fun MemoScreen() {
    val contextDB = LocalContext.current
    val db = remember { AppDatabase.getDatabase(contextDB) }
    var memo1 by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            try {
                memo1 = db.memoDao().getMemo1() ?: "메모 내용이 없습니다"
            } catch (e: Exception) {
                memo1 = "오류 발생: ${e.message}"
            }
        }
    }

    val getMemoContent = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            result.data?.getStringExtra("memo_content")?.let {
                memo1 = it
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Recently added",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        MemoRow(memo1) {
            val intent = Intent(contextDB, Recent_memo1::class.java).apply {
                putExtra("memo_content", memo1)
            }
            getMemoContent.launch(intent)
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Category",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        MemoCategory()
    }
}

@Composable
fun MemoRow(
    memo1: String,
    onMemoClick: () -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(listOf(memo1, "메모 내용 2", "메모 내용 3", "메모 내용 4")) { memo ->
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .background(Color.LightGray)
                    .clickable {
                        onMemoClick()
                    },
                contentAlignment = Alignment.Center
            ) {
                Text(text = memo)
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun MemoCategory() {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        itemsIndexed(listOf("카테고리 내용 1", "카테고리 내용 2")) { index, category ->
            Column {
                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .height(250.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = category)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .height(250.dp)
                        .background(Color.LightGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = if (index == 0) "카테고리 내용 3" else "카테고리 내용 4")
                }
                Spacer(modifier = Modifier.height(10.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MemoScreenPreview() {
    MaterialTheme {
        MemoScreen()
    }
}
