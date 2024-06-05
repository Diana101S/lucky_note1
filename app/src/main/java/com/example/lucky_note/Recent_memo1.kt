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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class Recent_memo1 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val memoContent = intent.getStringExtra("memo_content") ?: ""
        setContent {
            MaterialTheme {
                RecentMemoScreen(memoContent)
            }
        }
    }
}

@Composable
fun RecentMemoScreen(memoContent: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Your Memo:", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = memoContent, fontSize = 18.sp)

        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                // Handle back button or other actions
            }
        ) {
            Text(text = "Back")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecentMemoScreenPreview() {
    MaterialTheme {
        RecentMemoScreen("Sample Memo Content")
    }
}
