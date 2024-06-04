import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                SplashScreen(onGoogleLoginClick = {})
            }
        }
    }
}

@Composable
fun SplashScreen(onGoogleLoginClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White), // 깔끔한 흰색 배경
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            Text(
                text = "Find your luck in every note.",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(32.dp))
            GoogleLoginButton(onLoginClick = onGoogleLoginClick)
        }
    }
}

@Composable
fun GoogleLoginButton(onLoginClick: () -> Unit) {
    Button(
        onClick = { onLoginClick() },
        shape = CircleShape,
        modifier = Modifier
            .padding(vertical = 16.dp)
            .width(200.dp)
            .background(Color(0xFF0088FF)), // 파란색 배경
    ) {
        Text(
            text = "Google로 로그인",
            color = Color.White
        )
    }
}

@Composable
fun Logo() {
    // Replace with your actual logo image
    Image(
        painter = painterResource(id = R.drawable.logo),
        contentDescription = "Logo",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(120.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    MaterialTheme {
        SplashScreen(onGoogleLoginClick = {})
    }
}
