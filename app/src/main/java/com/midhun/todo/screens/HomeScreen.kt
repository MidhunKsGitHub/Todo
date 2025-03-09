package com.midhun.todo.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.midhun.todo.R
import com.midhun.todo.model.TaskModel
import com.midhun.todo.screens.componets.UpcomingCard
import com.midhun.todo.ui.theme.Pink40
import com.midhun.todo.ui.theme.Purple40
import com.midhun.todo.ui.theme.PurpleGrey40
import com.midhun.todo.ui.theme.SemiBoldFont
import com.midhun.todo.viewModel.TaskViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(modifier: Modifier, viewModel: TaskViewModel,navController: NavController,onToggleTheme:()->Unit) {


    val tasks by viewModel.tasks.collectAsState()

    var selectedOption by remember {
        mutableStateOf("pending")
    }

    var b1 by remember {
        mutableStateOf(Pink40)
    }
    var t1 by remember {
        mutableStateOf(Color.White)
    }

    var b2 by remember {
        mutableStateOf(Color.White)
    }
    var t2 by remember {
        mutableStateOf(Color.Black)
    }


    val isDarkMode = isSystemInDarkTheme()

    var darkModeEnabled by rememberSaveable  {
        mutableStateOf(isDarkMode)
    }


    Scaffold(floatingActionButton = { ExtendedFloatingActionButton(onClick = {
    navController.navigate("Add/-1")
    }, containerColor = Pink40) {
        Icon(Icons.Default.Add,"add")
        Text("Add Task")

    } }) { innerPadding ->

        LazyColumn(
            modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    brush =
                    Brush.horizontalGradient(
                        colors = listOf(
                            Purple40,
                            PurpleGrey40,
                            Pink40
                        )
                    )
                )) {

            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Hey ,", fontSize = 16.sp, color = Color.White)
                            Spacer(Modifier.height(5.dp))
                            Text("Welcome User !", fontSize = 18.sp, color = Color.White)
                        }
                        Card(
                            modifier = modifier.padding(8.dp).size(40.dp),
                            colors = CardColors(
                                containerColor = Color.White,
                                contentColor = Color.Black,
                                disabledContainerColor = Color.White,
                                disabledContentColor = Color.White
                            )
                        ) {

                            IconButton({
                                onToggleTheme()
                                darkModeEnabled = !darkModeEnabled
                            }) {
                                if (darkModeEnabled)
                                    Image(
                                        painterResource(R.drawable.baseline_light_mode_24),
                                        contentDescription = "dayNight"
                                    )
                                else
                                    Image(
                                        painterResource(R.drawable.baseline_nightlight_24),
                                        contentDescription = "dayNight"
                                    )
                            }
                        }
                    }

                    //header ends

                    Spacer(Modifier.height(50.dp))

                    if (tasks.isNotEmpty()) {
                        tasks.forEach { i ->

                            UpcomingCard(
                                modifier,
                                "Upcoming Task \n scheduled on \n ${
                                    convertDate(i.dueDate)
                                } at ${convertTime(i.dueDate)}"
                            )
                            return@Column
                        }

                    } else {
                        UpcomingCard(
                            modifier,
                            "There is no \n upcoming task \n scheduled"
                        )
                    }


                    Spacer(Modifier.height(10.dp))


                }
                //all task
                Column(Modifier.fillMaxWidth()) {

                    Row {
                        Spacer(Modifier.width(20.dp))
                        Text("All Tasks", fontSize = 14.sp)
                    }


                    Spacer(Modifier.height(30.dp))

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color.White),

                            ) {

                            Row {
                                Text(
                                    "Pending", modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(b1)
                                        .widthIn(min = 120.dp)
                                        .padding(5.dp)
                                        .clickable {
                                            selectedOption = "pending"
                                            b2 = Color.White
                                            t2 = Color.Black
                                            b1 = Pink40
                                            t1 = Color.White
                                        },
                                    color = t1,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(Modifier.width(10.dp))
                                Text(
                                    "Completed", modifier
                                        .clip(RoundedCornerShape(16.dp))
                                        .widthIn(min = 120.dp)
                                        .background(b2)
                                        .padding(5.dp)
                                        .clickable {
                                            selectedOption = "completed"
                                            b1 = Color.White
                                            t1 = Color.Black
                                            b2 = Pink40
                                            t2 = Color.White

                                        },
                                    color = t2,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            }

                        }

                    }
                }

                Spacer(Modifier.height(30.dp))
            }



            val listNew: List<TaskModel>

            val pendingList = tasks.filter {
                !it.isCompleted
            }


            val completedList = tasks.filter {
                it.isCompleted
            }

            listNew = if (selectedOption == "pending") {
                pendingList
            } else {
                completedList
            }

            if (listNew.isNotEmpty())
                items(listNew) { items ->
                    ListItems(modifier, items, viewModel, navController)
                }
        }

    }
}
@Composable
fun ListItems(modifier: Modifier = Modifier,taskModel: TaskModel,viewModel: TaskViewModel,navController: NavController) {
    val shouldShowDialog = remember { mutableStateOf(false) } // 1
    val colors = MaterialTheme.colorScheme

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 5.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        border = BorderStroke(1.dp, Color.White),
        colors = CardColors(
            containerColor = colors.surface,
            contentColor = colors.onPrimary,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        )
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier
                    .fillMaxHeight()
                    .width(10.dp)
                    .background(Pink40)
            ) {
                Text("")
            }
            // Image(painter = painterResource(R.drawable.ideas_flow), contentDescription = "",Modifier.size(120.dp))
            Column(modifier = Modifier.padding(10.dp)) {
                Text(
                    if (taskModel.isCompleted) "Completed" else "Pending",
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 12.sp,
                    color = colors.onBackground
                )

                Row {
                    Text(
                        taskModel.title,
                        fontFamily = SemiBoldFont,
                        fontSize = 14.sp,
                        color = colors.onBackground
                    )

                }
                Spacer(Modifier.height(5.dp))
                Text(
                    taskModel.description,
                    fontSize = 12.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = colors.onBackground
                )
                Spacer(Modifier.height(7.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                )
                {
                    Image(
                        painter = painterResource(R.drawable.ic_time),
                        contentDescription = "",
                        modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        convertTime(taskModel.dueDate),
                        fontSize = 10.sp,
                        color = colors.onBackground
                    )
                    Spacer(Modifier.width(10.dp))
                    Image(
                        painter = painterResource(R.drawable.ic_date),
                        contentDescription = "",
                        modifier.size(13.dp)
                    )
                    Spacer(Modifier.width(10.dp))
                    Text(
                        convertDate(taskModel.dueDate),
                        fontSize = 10.sp,
                        color = colors.onBackground
                    )
                }
            }
        }
        HorizontalDivider(
            Modifier.padding(horizontal = 15.dp, vertical = 7.dp),
            color = Color.LightGray
        )
        Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
            Text("Edit", fontSize = 12.sp, modifier = Modifier
                .padding(5.dp)
                .clickable { navController.navigate("Add/${taskModel.id}") }, color = Pink40
            )
            Text("Delete",
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(5.dp)
                    .clickable { shouldShowDialog.value = true },
                color = Color.Red.copy(alpha = 0.5F)
            )
        }
    }

  if(shouldShowDialog.value){
      com.midhun.todo.screens.componets.AlertDialog(shouldShowDialog, onClick = {
          viewModel.deleteTask(taskModel)
      })
  }

}

fun convertDate(time:Long):String{
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(time))
}

fun convertTime(time:Long):String{
    val sdf = SimpleDateFormat("HH:mm a", Locale.getDefault())
    return sdf.format(Date(time))
}