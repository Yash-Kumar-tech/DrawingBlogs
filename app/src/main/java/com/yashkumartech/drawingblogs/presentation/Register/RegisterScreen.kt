package com.yashkumartech.drawingblogs.presentation.Register

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yashkumartech.drawingblogs.presentation.Home.UserViewModel
import com.yashkumartech.drawingblogs.util.Routes

@Composable
fun RegisterScreen(
    userViewModel: UserViewModel = hiltViewModel(),
    navController: NavHostController
) {
    val context = LocalContext.current
    val userName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val maxWidth = 400.dp
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri = uri
            Log.d("ImageUri", uri.toString())
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .height(150.dp)
                .aspectRatio(1f)
        ) {
            AsyncImage(
                model = selectedImageUri,
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .height(130.dp)
                    .aspectRatio(1f)
                    .background(Color.DarkGray),
                contentScale = ContentScale.Crop
            )
            IconButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 8.dp, bottom = 8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)),
                onClick = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(
                            ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                },
            ) {
                Icon(
                    Icons.Rounded.Person,
                    contentDescription = "Select Image from gallery",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Text(text = "Sign up", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = userName.value,
            onValueChange = { userName.value = it },
            label = { Text("User name") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            ),
            modifier = Modifier
                .widthIn(max = maxWidth)
                .fillMaxWidth()
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
            modifier = Modifier
                .widthIn(max = maxWidth)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(Modifier.widthIn(max = maxWidth)) {
            Button(
                onClick = {
                    if (userName.value.isEmpty() || email.value.isEmpty() || password.value.isEmpty()) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                    } else {
                        val auth = Firebase.auth
                        auth.createUserWithEmailAndPassword(email.value, password.value)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    userViewModel.createUser(auth.currentUser, userName.value, selectedImageUri.toString())
                                    Toast.makeText(
                                        context,
                                        "Registered successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    //                            navigator.navigate(HomeScreenDestination)
                                    navController.navigate(Routes.Home.route)
                                } else {
                                    Log.d("User signup", it.exception.toString())
                                    Toast.makeText(context, "Invalid values", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign up")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Box(Modifier.widthIn(max = maxWidth)) {
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
}