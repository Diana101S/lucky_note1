package com.example.lucky_note

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
                MemoScreen { memoContent ->
                    startActivity(
                        Intent(this, Recent_memo1::class.java)
                    )
                }
            }
        }
    }
}

@Composable
fun MemoScreen(onMemoClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        val contextDB = LocalContext.current
        val db = remember {
            AppDatabase.getDatabase(contextDB)
        }
        var memo1 by remember {
            mutableStateOf("")
        }
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

        //Text(text = memo1)

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Recently added",
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(20.dp))
        MemoRow(onMemoClick, listOf("메모 내용 1", "메모 내용 2", "메모 내용 3", "메모 내용 4"))

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
    onMemoClick: (String) -> Unit,
    inputList: List<String>
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(inputList) { memo ->
            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(70.dp)
                    .background(Color.LightGray)
                    .clickable {
                        onMemoClick(memo)
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
        MemoScreen { }
    }
}
