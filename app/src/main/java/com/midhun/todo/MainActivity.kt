package com.midhun.todo

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.midhun.todo.database.TaskDatabase
import com.midhun.todo.repository.TaskRepository
import com.midhun.todo.screens.AddEditTask
import com.midhun.todo.screens.HomeScreen
import com.midhun.todo.screens.componets.RequestPermission
import com.midhun.todo.ui.theme.TodoTheme
import com.midhun.todo.viewModel.TaskViewModel
import com.midhun.todo.viewModel.TaskViewModelFactory

class MainActivity : ComponentActivity() {
   private lateinit var viewModel: TaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val database = TaskDatabase.getDatabase(this)

        val repository = TaskRepository(database.taskDao())

        viewModel = ViewModelProvider(this,TaskViewModelFactory(repository,applicationContext))[TaskViewModel::class.java]
        setContent {


            val isDarkMode = isSystemInDarkTheme()
            var darkModeEnabled by rememberSaveable  {
                mutableStateOf(isDarkMode)
            }

            TodoTheme(darkModeEnabled) {
                RequestPermission()
                Navigation(modifier = Modifier,viewModel) { darkModeEnabled = !darkModeEnabled }
            }
        }
    }
}
@Composable
private fun Navigation(modifier: Modifier, viewModel: TaskViewModel, onToggleTheme:()-> Unit){
    val navController = rememberNavController()

    NavHost(navController, startDestination = "Home") {
        composable("Home") {
            HomeScreen(modifier, viewModel = viewModel, navController, onToggleTheme = {onToggleTheme()})
        }
        composable("Add/{taskID}", arguments = listOf(navArgument("taskID"){
            type = NavType.IntType
        })){ backStackEntry->

            val id= backStackEntry.arguments?.getInt("taskID")
            AddEditTask(modifier,viewModel,navController,id?:0)
        }
    }
}


