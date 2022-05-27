package com.example.linklibrary.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linklibrary.R
import com.example.linklibrary.components.AppLogo
import com.example.linklibrary.components.CreateImageProfile
import com.example.linklibrary.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController ) {
    var isPlaying = true
    var currentRotation by remember { mutableStateOf(0f) }

    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(isPlaying) {
        if (isPlaying) {
            // Infinite repeatable rotation when is playing
            rotation.animateTo(
                targetValue = currentRotation + 360f,
                animationSpec = tween(
                    durationMillis = 1250,
                    easing = LinearOutSlowInEasing
                )
//                animationSpec = infiniteRepeatable(
//                    animation = tween(3000, easing = LinearEasing),
//                   repeatMode = RepeatMode.Restart
//                )
            ) {
                currentRotation = value

            }
        } else {
            if (currentRotation > 0f) {
                // Slow down rotation on pause
                rotation.animateTo(
                    targetValue = currentRotation + 50,
                    animationSpec = tween(
                        durationMillis = 1250,
                        easing = LinearOutSlowInEasing
                    )
                ) {
                    currentRotation = value
                }
            }
        }
    }

    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(0.9f, animationSpec = tween(800, easing = {
            OvershootInterpolator(8f)
                .getInterpolation(it)
        }))
        delay(3000)
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()) {
            navController.navigate(AppScreens.LoginScreen.name)
        } else {
            navController.navigate(AppScreens.HomeScreen.name)
            navController.navigate(AppScreens.ActionChoice.name)
        }

    }
    Surface(
        modifier = Modifier
            .padding(15.dp)
            .size(330.dp)
            .scale(scale = scale.value)
            .rotate(rotation.value),

        shape = CircleShape,
        color = Color.White,

        border = BorderStroke(width = 2.dp, color = Color.LightGray)
    )
    {
//        Column(
//            Modifier.padding(1.dp), verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//           AppLogo()
//            Spacer(modifier = Modifier.height(15.dp))
//            Text(
//                text = "\"useful links for android study \"",
//                style = MaterialTheme.typography.h5,
//                color = Color.Gray.copy(alpha = 0.5f)
//            )
//
//        }
        CreateImageProfile()


    }

}