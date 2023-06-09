package com.yashkumartech.drawingblogs.presentation.Login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.presentation.Home.UserViewModel
import com.yashkumartech.drawingblogs.util.Routes


@Composable
fun LoginScreen(
    viewModel: UserViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val maxWidth = 400.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .widthIn(max = maxWidth)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.widthIn(max = 400.dp).fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box (Modifier.widthIn(max = maxWidth)){
            Button(
                onClick = {
                    if (email.value.isEmpty() || password.value.isEmpty()) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    } else {
                        val auth = Firebase.auth
                        auth.signInWithEmailAndPassword(
                            email.value, password.value
                        ).addOnCompleteListener {
                            if (it.isSuccessful) {
                                viewModel.setUser(auth.currentUser)
                                navController.navigate(Routes.Home.route)
                            } else {
                                Toast.makeText(context, "Incorrect credentials", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Login")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = Modifier.widthIn(max = maxWidth)) {
            TextButton(
                onClick = {
                    navController.navigate(Routes.Register.route)
                },
                Modifier.fillMaxWidth()
            ) {
                Text("Don't have an account yet? Sign up by clicking here")
            }
        }
    }
}