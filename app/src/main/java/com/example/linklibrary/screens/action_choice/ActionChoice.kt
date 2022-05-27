package com.example.linklibrary.screens.action_choice

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linklibrary.components.ChoiceButton
import com.example.linklibrary.navigation.AppScreens

@Preview
@Composable
fun ActionChoice(navController: NavController= NavController(LocalContext.current)) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize(),
        color = Color.Green.copy(alpha = 0.3f),
        elevation = 4.dp
       
        ) {
        Column(verticalArrangement = Arrangement.Center) {
            Row(Modifier.fillMaxWidth()
            , horizontalArrangement = Arrangement.SpaceBetween) {

                ChoiceButton(
                    onClick = { navController.navigate(AppScreens.UpdateScreen.name) },
                    navController =navController ,
                    text = "go to update")

                ChoiceButton(
                    onClick = { navController.navigate(AppScreens.HomeScreen.name) },
                    navController =navController ,
                    text = "go to Links")
            }
        }


    }


}