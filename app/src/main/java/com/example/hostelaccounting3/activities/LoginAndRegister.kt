import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
fun Context.LoginForm(
    onSubmit: (login: String, password: String) -> Unit,
    onWantChange: () -> Unit,
    submitButtonText: String,
    changeModeButtonText: @Composable () -> Unit
) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var showWarning by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 64.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = login,
            onValueChange = { login = it },
            label = { Text("Логин") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(modifier = Modifier.fillMaxWidth(), onClick = { onSubmit(login, password) }) {
            Text(submitButtonText)
        }
        TextButton(
            modifier = Modifier.fillMaxWidth(), onClick = onWantChange
        ) {
            changeModeButtonText()
        }

        if (showWarning) {
            Text(
                text = errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun Context.Login(onSuccess: () -> Unit, onWantRegister: () -> Unit) = LoginForm({ login, password ->
    val error = login(login, password)
    println("Login error: $error")
    if (error == null) {
        onSuccess()
    }
}, onWantRegister, "Войти", {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Зарегистрироваться")
        Text("(если вы ещё не зарегистрировались)")
    }
})

@Composable
fun Context.Register(onSuccess: () -> Unit, onWantLogin: () -> Unit) = LoginForm({ login, password ->
    val error = register(login, password)
    println("Register error: $error")
    if (error == null) {
        onSuccess()
    }
}, onWantLogin, "Зарегистрироваться", { Text("Войти (если вы уже зарегистрированы)") })