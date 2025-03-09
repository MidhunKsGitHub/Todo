package com.midhun.todo.screens.componets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midhun.todo.R
import com.midhun.todo.ui.theme.Pink40
import com.midhun.todo.ui.theme.Purple40
import com.midhun.todo.ui.theme.PurpleGrey40


@Composable
fun UpcomingCard(modifier: Modifier,msg: String){
    Column() {
        Text("Upcoming Tasks",  fontSize = 14.sp)
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color.LightGray),
            colors = CardColors(
                containerColor = Color.White,
                contentColor = Color.Black,
                disabledContainerColor = Color.White,
                disabledContentColor = Color.White
            )
        ) {
            Row(
                Modifier.fillMaxWidth()
                    .height(140.dp)
                    .background(
                        brush =
                        Brush.horizontalGradient(
                            colors = listOf(
                                Purple40,
                                PurpleGrey40,
                                Pink40
                            )
                        )
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            )
            {

                Image(
                    painterResource(R.drawable.add_notes_red),
                    contentDescription = "",
                    modifier.height(110.dp)
                )
                Text(
                    text = msg,
                    color = Color.White,
                    fontSize = 13.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}