//package com.example.lucky_note
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.height
//import androidx.compose.material3.Button
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//
//class Recent_memo1 : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val memoContent = intent.getStringExtra("memo_content") ?: ""
//        setContent {
//            MaterialTheme {
//                RecentMemoScreen()
//
//
////                RecentMemoScreen(memoContent)
//
//
//            }
//        }
//    }
//}
//
//
//@Composable
//fun RecentMemoScreen() {
//    // Assuming the 'memoContent' is a string passed from another screen
////    val memoContent = navController.currentBackStackEntry?.arguments?.getString("memoContent") ?: ""
//    val memoContent=""
//    //intent getString
//    //intent putExtra
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = "Your Memo:", fontSize = 24.sp)
//        Spacer(modifier = Modifier.height(10.dp))
//        Text(text = memoContent, fontSize = 18.sp)
//
//        // Add buttons or other elements to interact with the memo content
//        Spacer(modifier = Modifier.height(20.dp))  // Optional for better spacing
//        Button(
//            onClick = {
//                // Handle back button or other actions
////                navController.popBackStack()
//            }
//        ) {
//            Text(text = "Back")
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RecentMemoScreenPreview() {
//    MaterialTheme {
//        RecentMemoScreen()
//    }
//}
//


/////////////////////////////////////////////////////////
//아래작동되는코드임
//
package com.example.lucky_note

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Recent_memo1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val memoContent = intent.getStringExtra("memo_content") ?: ""
        setContent {
            MaterialTheme {
                RecentMemoScreen(
//                    memoContent
                )
            }
        }
    }
}

@Composable
fun RecentMemoScreen(
//    memoContent: String
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val contextDB = LocalContext.current
        val context = LocalContext.current as Activity?
        val db = remember {
            AppDatabase.getDatabase((contextDB))
        }
        var memo1 by remember {
            mutableStateOf("")
        }
        LaunchedEffect(Unit) {
            CoroutineScope(Dispatchers.IO).launch {
                memo1 = db.userDao().getMemo1() ?: memo1
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Your Memo:", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        TextField(value = memo1,
            onValueChange = { newMemo ->
                CoroutineScope(Dispatchers.IO).launch {
                    memo1 = newMemo
                    db.userDao().insertAll(MemoRoomColumns(memo1 = newMemo))
                }
            })
        Text(text = memo1, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        db.userDao().insertAll(MemoRoomColumns(memo1 = memo1))
                    }
                }
            ) {
                Text(text = "Save")
            }

            Button(
                onClick = {
                    context?.finish()
                    // Handle back button or other actions
                }
            ) {
                Text(text = "Back")
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun RecentMemoScreenPreview() {
    MaterialTheme {
        RecentMemoScreen(
//            "Sample Memo Content"
        )
    }
}
