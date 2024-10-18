import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hostelaccounting3.context.Context
import com.example.hostelaccounting3.login.login
import com.example.hostelaccounting3.login.register

@Composable
fun Context.LoginOrRegister(onSuccess: () -> Unit) {
    var needLogin by remember { mutableStateOf(true) }

    when {
        needLogin -> Login(onSuccess) {
            needLogin = false
        }

        else -> Register(onSuccess) {
            needLogin = true
        }
    }
}

@Composable
fun Context.Login(onSuccess: () -> Unit, onWantRegister: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    var showWarning by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(value = login, onValueChange = { login = it }, label = { Text("Login") })
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") })
        Button(onClick = {
            val error = login(login, password)?.let {
                showWarning = true
                errorMessage = it
            }
            if (error == null) {
                onSuccess()
            }
        }) {
            Text("Login")
        }
        TextButton(onClick = {
            onWantRegister()
        }) {
            Text("Register (if you don't have an account)")
        }

        if (showWarning) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        }
    }
}

@Composable
fun Context.Register(onSuccess: () -> Unit, onWantLogin: () -> Unit) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showWarning by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.Center
    ) {
        TextField(value = login, onValueChange = { login = it })
        TextField(value = password, onValueChange = { password = it })
        Button(onClick = {
            val error = register(login, password)?.let {
                showWarning = true
                errorMessage = it
            }
            if (error == null) {
                onSuccess()
            }
        }) {
            Text("Register")
        }
        Button(onClick = { onWantLogin() }) {
            Text("Login (if you already have an account)")
        }

        if (showWarning) {
            Text(text = errorMessage)
        }
    }
}