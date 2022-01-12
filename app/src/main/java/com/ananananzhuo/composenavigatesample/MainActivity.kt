package com.ananananzhuo.composenavigatesample

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ananananzhuo.composenavigatesample.ui.theme.ComposeNavigateSampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeNavigateSampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Greeting("Android")
                }
            }
        }
    }
}

const val ROUTE_1 = "route1"
const val ROUTE_2 = "route2"
const val ROUTE_3 = "route3"
const val ROUTE_4 = "route4"
const val HOME = "home"

@Composable
fun Greeting(name: String) {
    val controller = rememberNavController()
    controller.addOnDestinationChangedListener { controller, destination, arguments ->
        //路由发生跳转的参数回调
    }
    NavHost(navController = controller, startDestination = HOME) {
        composable(HOME) {
            Home(controller)
        }
        composable(ROUTE_1) {
            Page1(controller = controller)
        }
        composable(ROUTE_2) {
            Page2(controller = controller)
        }
        composable(ROUTE_3) {
            Page3(controller = controller)
        }
        composable(ROUTE_4) {
            Page4(controller = controller)
        }
    }
}

@Composable
fun Home(controller: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            controller.navigate(ROUTE_1)
        }) {
            Text(text = "点击进入页面1")
        }
        Button(onClick = {
            controller.navigate(ROUTE_1)
        }) {
            Text(text = "点击演示singleTop效果")
        }

    }
}

/**
 * 测试进入下个页面，并销毁当前页面的逻辑
 */
@Composable
fun Page1(controller: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是页面1")
        Button(onClick = {
            controller.navigate(ROUTE_2)
        }) {
            Text(text = "点击进入页面2")
        }
        Button(onClick = {
            controller.popBackStack()
            controller.navigate(ROUTE_2)
        }) {
            Text(text = "点击进入页面2,并销毁当前页面")
        }
        Button(onClick = {
            toPage1(controller, ROUTE_1)
        }) {
            Text(text = "点击跳转复用当前页面")
        }
        Button(onClick = {
            toSingleTask(controller, ROUTE_1)
        }) {
            Text(text = "实现singleInstance效果")
        }
    }
    Log.e("tag", "当前页面被重组")
}

/**
 * 失效从当前页面返回到之前的某个页面的效果，并销毁中间页面
 */
@Composable
fun Page2(controller: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是页面2")
        Button(onClick = {
            controller.navigate(ROUTE_3)
        }) {
            Text(text = "点击进入页面3")
        }
        Button(onClick = {
            controller.popBackStack(HOME, false)//第二个参数：true：会把home页面也销毁，  false：会保留home页面
        }) {
            Text(text = "从当前页面直接返回到Home页面")
        }
    }
}

@Composable
fun Page3(controller: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是页面3")
        Button(onClick = {
        }) {

        }

    }
}

@Composable
fun Page4(controller: NavHostController) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "这是页面4")
        Button(onClick = {
        }) {

        }

    }
}

/**
 * 判断顶部页面是否刚好是我们要路由的页面
 */
fun sameAsTopPage(controller: NavHostController,dstRoute: String):Boolean{
    val distination = controller.currentDestination?.route ?: ""
    return distination == dstRoute
}
/**
 * 实现singleTop效果
 */
fun toPage1(controller: NavHostController, dstRoute: String) {
    val distination = controller.currentDestination?.route ?: ""
    if (distination == dstRoute) {
        controller.popBackStack()
        controller.navigate(dstRoute)
    }
}

/**
 * 实现singleTask效果
 */
fun toSingleTask(controller: NavHostController, route: String){
    val findRoute = controller.findDestination(route)
    if (findRoute == null){
        controller.navigate(route)
    }else{
        controller.popBackStack()
        controller.navigate(route)
    }
}