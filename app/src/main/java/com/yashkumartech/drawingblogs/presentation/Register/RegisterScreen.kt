package com.yashkumartech.drawingblogs.presentation.Register

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.presentation.Home.HomeScreenViewModel
import com.yashkumartech.drawingblogs.util.Routes

@Composable
fun RegisterScreen(
    userViewModel: HomeScreenViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val userName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Sign up", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text("User name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if(userName.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                } else {
                    val auth = Firebase.auth
                    auth.createUserWithEmailAndPassword(email.value, password.value).addOnCompleteListener {
                        if(it.isSuccessful) {
                            userViewModel.createUser(auth.currentUser, userName.value)
                            Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
//                            navigator.navigate(HomeScreenDestination)
                            navController.navigate(Routes.Home.route)
                        } else {
                            Log.d("User signup", it.exception.toString())
                            Toast.makeText(context, "Invalid values", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Sign up")
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
//                navigator.navigate(LoginScreenDestination)
                navController.navigate(Routes.Login.route)
            }
        ) {
            Text("Already have an account? Login here")
        }
    }
}