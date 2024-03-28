package com.example.testandroidpro.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testandroidpro.R
import com.example.testandroidpro.viewmodel.AdViewModel
import com.example.testandroidpro.viewmodel.PdfLoadViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val adViewModel: AdViewModel = viewModel()
    val pdfLoadViewModel: PdfLoadViewModel = viewModel()
    NavHost(navController = navController, startDestination = adViewModel.startDestination) {
        composable(route = "login") {
            when (navController.currentDestination?.route) {
                "login" -> LoginScreen(navController,adViewModel)
            }
        }
        composable(route = context.getString(R.string.HomePage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.HomePage) -> MainScreen(navController,adViewModel,pdfLoadViewModel)
            }
        }
        composable(route = "signup") {
            when (navController.currentDestination?.route) {
                "signup" -> SignupScreen(navController,adViewModel)
            }
        }
        composable(route = "info") {
            when (navController.currentDestination?.route) {
                "info" -> InfoScreen(navController,adViewModel)
            }
        }
        composable(route = "pdf") {
            when (navController.currentDestination?.route) {
                "pdf" -> PdfScreen(navController,adViewModel.filePath)
            }
        }
    }
}

//@Composable
//fun MyApp() {
//    val navController = rememberNavController()
//    val context = LocalContext.current
//    val adViewModel: AdViewModel = viewModel()
//    val pdfLoadViewModel:PdfLoadViewModel = viewModel()
//    NavHost(navController = navController, startDestination = "login"
//    )
//    {
//        composable(route = "login"){
//            LoginScreen(navController,adViewModel)
//        }
//        composable(route = context.getString(R.string.HomePage)){
//            MainScreen(navController,adViewModel,pdfLoadViewModel)
//        }
//        composable(route = "signup"){
//            SignupScreen(navController,adViewModel)
//        }
//        composable(route = "info"){
//            InfoScreen(navController,adViewModel)
//        }
//        composable(route = "pdf"){
//            PdfScreen(navController,adViewModel,pdfLoadViewModel)
//        }
//
//    }
//}