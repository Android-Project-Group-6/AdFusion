package com.example.testandroidpro.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testandroidpro.R
import com.example.testandroidpro.viewmodel.AdViewModel

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val adViewModel: AdViewModel = viewModel()
//    val pdfLoadViewModel: PdfLoadViewModel = viewModel()
    NavHost(navController = navController, startDestination = adViewModel.startDestination) {
        composable(route = context.getString(R.string.loginPage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.loginPage) -> LoginScreen(navController,adViewModel)
            }
        }
        composable(route = context.getString(R.string.homePage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.homePage) -> MainScreen(navController,adViewModel)
            }
        }
        composable(route = context.getString(R.string.signupPage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.signupPage) -> SignupScreen(navController,adViewModel)
            }
        }
        composable(route = context.getString(R.string.infoPage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.infoPage) -> InfoScreen(navController,adViewModel)
            }
        }
        composable(route = context.getString(R.string.pdfPage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.pdfPage) -> PdfScreen(navController,adViewModel)//dViewModel.userSignOut(navController) }
            }
        }
        composable(route = context.getString(R.string.supportPage)) {
            when (navController.currentDestination?.route) {
                context.getString(R.string.supportPage) -> SupportScreen(navController,adViewModel)
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