package com.example.linklibrary.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.linklibrary.R
import com.example.linklibrary.model.MBook
import com.example.linklibrary.navigation.AppScreens
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Text(text = "Link.Library",
        modifier = modifier.padding(top = 25.dp),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f),
    fontFamily = FontFamily.Cursive)
}


@Composable
fun CreateImageProfile(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
            .size(220.dp)
            .padding(5.dp),
        shape = CircleShape,
        border = BorderStroke(0.5.dp, Color.DarkGray),
        elevation = 4.dp,
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),



        ) {

        Image(
            painter = painterResource(id = R.drawable.checklist_background),
            contentDescription = "profile image",
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.None
        )


        Column(
            Modifier.padding(1.dp), verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AppLogo()
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(bottom = 50.dp),
                text = "\"useful links for study \"",
                style = MaterialTheme.typography.h5,
                color = Color.Red.copy(alpha = 0.5f)
            )

        }


    }
}

@Composable
fun NoteRow(
    modifier: Modifier = Modifier,
    note: String,
    title:String,
    list: List<MBook>,
    onNoteClicked: (String) -> Unit
) {
    Surface(
        modifier
            .padding(4.dp)
            .clip(RoundedCornerShape(topEnd = 33.dp, bottomStart = 33.dp))
            .fillMaxWidth(),
        color = Color(0xFFDFE6EB),
        elevation = 6.dp
    ) {
        Column(modifier
            .clickable {
                onNoteClicked(note)
            }
            .padding(horizontal = 14.dp, vertical = 6.dp),
            horizontalAlignment = Alignment.Start) {
            Text(
                text = "section: $title",
                style = MaterialTheme.typography.subtitle2
            )
            Text(text = note, style = MaterialTheme.typography.subtitle1)





        }
    }

}

@Composable
fun LinkLibraryAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked:() -> Unit = {}
) {

    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically){
            if (showProfile) {
                Icon(imageVector = Icons.Default.Favorite,
                    contentDescription = "Logo Icon",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.9f)
                )

            }
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "arrow back",
                    tint = Color.Red.copy(alpha = 0.7f),
                    modifier = Modifier.clickable { onBackArrowClicked.invoke() })
            }
            Spacer(modifier = Modifier.width(40.dp) )
            Text(text = title,
                color = Color.Red.copy(alpha = 0.7f),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            )


        }


    },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance()
                    .signOut().run {
                        navController.navigate(AppScreens.LoginScreen.name)
                    }
            }) {
                if (showProfile) Row() {
                    Icon(imageVector = Icons.Filled.Logout ,
                        contentDescription = "Logout" ,
                        // tint = Color.Green.copy(alpha = 0.4f)
                    )
                }else Box {}



            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp)

}

@Composable
fun EmailInput(
    modifier: Modifier = Modifier,
    emailState: MutableState<String>,
    labelId: String = "Email",
    enabled: Boolean = true,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    InputField(modifier = modifier,
        valueState = emailState,
        labelId = labelId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction)


}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    isSingleLine: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    onAction: KeyboardActions = KeyboardActions.Default
) {

    OutlinedTextField(value = valueState.value,
        onValueChange = { valueState.value = it},
        label = { Text(text = labelId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction)


}

@Composable
fun PasswordInput(
    modifier: Modifier,
    passwordState: MutableState<String>,
    labelId: String,
    enabled: Boolean,
    passwordVisibility: MutableState<Boolean>,
    imeAction: ImeAction = ImeAction.Done,
    onAction: KeyboardActions = KeyboardActions.Default,
) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()
    OutlinedTextField(value = passwordState.value,
        onValueChange = {
            passwordState.value = it
        },
        label = { Text(text = labelId)},
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = MaterialTheme.colors.onBackground),
        modifier = modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction),
        visualTransformation = visualTransformation,
        trailingIcon = {PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction)

}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {
    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible}) {
        Icons.Default.Close

    }

}

@Composable
fun ChoiceButton(
    backgroundColor:Color= Color.Green.copy(alpha = 0.09f),
    contentColor:Color=Color.White,
    onClick:() -> Unit,
    navController: NavController,
    modifier: Modifier= Modifier
        .clip(RoundedCornerShape(5))
        .size(width = 175.dp, height = 80.dp),


    text:String

) {
   Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
                .copy(alpha = 0.09f),
            contentColor = contentColor,

        )
    ) {

        Text(
            text = text,
        )

    }

}
@Composable
fun ImageButton(

){


    Image(
        painter = painterResource(id = R.drawable.checklist_background),
        contentDescription = "profile image",

        )
    Image(
        painter = BitmapPainter(ImageBitmap.imageResource(R.drawable.checklist_background)),
        contentDescription = "Зимний лес"
    )


}