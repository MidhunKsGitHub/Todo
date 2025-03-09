package com.midhun.todo.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.midhun.todo.R
import com.midhun.todo.model.TaskModel
import com.midhun.todo.ui.theme.Pink40
import com.midhun.todo.ui.theme.Purple40
import com.midhun.todo.ui.theme.PurpleGrey40
import com.midhun.todo.ui.theme.SemiBoldFont
import com.midhun.todo.viewModel.TaskViewModel

@Composable
fun AddEditTask(modifier: Modifier,viewModel: TaskViewModel,navController: NavController,taskID:Int) {

    val context = LocalContext.current

    val task = taskID.let {
        viewModel.getTaskById(it).collectAsState(initial = null).value
    }
    var id by remember {
        mutableStateOf("")
    }

    var title by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var dueDate by remember {
        mutableStateOf(System.currentTimeMillis())
    }

    var selectedDate by remember {
        mutableStateOf(false)
    }
    var selectedTime by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(task) {

        task.let {
            title = it?.title ?: ""
            description = it?.description ?: ""
            dueDate = it?.dueDate ?: System.currentTimeMillis()
        }
    }

Scaffold { innerPadding->

    Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {


        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().height(65.dp)
        ) {
            Text(
                if (taskID == -1) "Add Task" else "Edit Task",
                fontSize = 16.sp,
            )
        }

        HorizontalDivider()


        Column(modifier = Modifier.fillMaxSize().padding(15.dp)) {

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = title,
                onValueChange = {
                    title = it
                },
                label = {
                    Text("title")
                }
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = description,
                onValueChange = {
                    description = it
                },
                label = {
                    Text("desc")
                }
            )

            OutlinedTextField(
                trailingIcon = {

                    IconButton(
                        onClick = {
                            selectedDate = true
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_date),
                            contentDescription = "test"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                value = convertDate(dueDate),
                onValueChange = {
                    dueDate = it.toLong()
                },
                readOnly = true,
                label = {
                    Text("date")
                }
            )

            OutlinedTextField(
                trailingIcon = {
                    IconButton(
                        onClick = {
                            selectedTime = true
                        }
                    ) {

                        Image(
                            painter = painterResource(R.drawable.ic_time),
                            contentDescription = "test"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                value = convertTime(dueDate),
                onValueChange = {
                    dueDate = it.toLong()
                },
                readOnly = true,
                label = {
                    Text("time")
                }
            )



            Spacer(Modifier.height(50.dp))



            Button(onClick = {
                if (taskID == -1) {
                    val newTask = TaskModel(0, title, description, dueDate)
                    viewModel.addTask(newTask)
                } else {
                    val newTask = TaskModel(taskID, title, description, dueDate)
                    viewModel.updateTask(newTask)
                }

                navController.popBackStack()
            },  modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(
                brush =   Brush.horizontalGradient(
                    colors = listOf(
                        Purple40,
                        PurpleGrey40,
                        Pink40
                    )
                ),

                ), colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            )) {
                Text("Save")
            }

            Spacer(Modifier.height(15.dp))

            if (taskID != -1) {
                Button(onClick = {

                    val newTask = TaskModel(taskID, title, description, dueDate, true)
                    viewModel.updateTask(newTask)
                    navController.popBackStack()
                }, modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(
                    brush =   Brush.horizontalGradient(
                        colors = listOf(
                            Purple40,
                            PurpleGrey40,
                            Pink40
                        )
                    ),

                ), colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                )) {
                    Text("Complete Task")
                }
            }

        }

        if (selectedDate) {
            DatePickerDialog(context, { date ->
                dueDate = date
            }
            ) {
                selectedDate = false
            }
        }

        if (selectedTime) {
            TimePickerDialog(context, dueDate, { time ->
                dueDate = time
            }) {
                selectedTime = false
            }
        }
    }
}
}

