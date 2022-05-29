package com.example.linklibrary.screens.action_choice

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.linklibrary.R
import com.example.linklibrary.components.ChoiceButton
import com.example.linklibrary.components.ComponentButton
import com.example.linklibrary.components.LinkLibraryAppBar
import com.example.linklibrary.navigation.AppScreens
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonState
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSButtonType
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSCustomLoadingEffect
import com.simform.ssjetpackcomposeprogressbuttonlibrary.SSJetPackComposeProgressButton
import com.simform.ssjetpackcomposeprogressbuttonlibrary.utils.six
import com.simform.ssjetpackcomposeprogressbuttonlibrary.utils.ten
import com.simform.ssjetpackcomposeprogressbuttonlibrary.utils.two

@Preview
@Composable
fun ActionChoice(navController: NavController= NavController(LocalContext.current)) {
//    Surface(
//        modifier = Modifier
//            .padding(4.dp)
//            .fillMaxSize(),
//        color = Color.Green.copy(alpha = 0.3f),
//        elevation = 4.dp
//
//    ) {
        Scaffold(
            topBar = {
            LinkLibraryAppBar(
                title = "action choice",
                //icon = Icons.Default.ArrowBack,
                showProfile = true,
                navController = navController
            ){}
        }) {
            Surface(
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize(),
                color = Color.Green.copy(alpha = 0.3f),
                elevation = 4.dp

            ) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                var submitButtonState by remember { mutableStateOf(SSButtonState.IDLE) }
                ComponentButton(navController = navController, onClick = {
                    submitButtonState = SSButtonState.LOADING
                    navController.navigate(AppScreens.UpdateScreen.name)
                }, text = "go to update")

                ComponentButton(navController = navController, onClick = {
                    submitButtonState = SSButtonState.LOADING
                    navController.navigate(AppScreens.HomeScreen.name)
                }, text = "go to Links")

                ComponentButton(navController = navController, onClick = {
                    submitButtonState = SSButtonState.LOADING
                    navController.navigate(AppScreens.InfoScreen.name)
                }, text = "go to info")


            }

        }
    }
}