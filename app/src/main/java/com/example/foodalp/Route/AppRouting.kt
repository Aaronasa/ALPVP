package com.example.week7.Route

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.week7.View.ListCard
import com.example.week7.View.Detailcard


enum class ListScreen(){
    ListCard,
    Detailcard
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppRouting(){

    val navController = rememberNavController()

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    Scaffold (modifier = Modifier.fillMaxSize()
        .nestedScroll(scrollBehavior.nestedScrollConnection),
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListScreen.ListCard.name,
            modifier = Modifier.padding(innerPadding)
        ){
            //mengassign fungsi composable ke route
            composable(route = ListScreen.ListCard.name){
                ListCard(navController = navController)
            }
            composable(route = ListScreen.Detailcard.name+"/{id}",arguments = listOf(
                navArgument("id"){
                    type = NavType.IntType
                }
            )){ backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                Detailcard(id!!)
            }
        }
    }
}
