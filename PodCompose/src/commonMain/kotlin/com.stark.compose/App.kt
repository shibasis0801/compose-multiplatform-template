package com.stark.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import okio.Path.Companion.toPath

interface PreferencePathProvider {
    fun getPath(preferenceName: String = "todo.preferences_pb"): String
}

var preferencePathProvider: PreferencePathProvider? = null

private val dataStore: DataStore<Preferences> = PreferenceDataStoreFactory.createWithPath(produceFile = {
    preferencePathProvider?.getPath()?.toPath() ?: throw NullPointerException("Please initialize the PreferencePathProvider")
})


data class TodoItem(val task: String, var isChecked: Boolean = false)

@Composable
fun TodoItem(todoItem: TodoItem) {
    var isChecked by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { isChecked = !isChecked }
        )
        Text(
            text = todoItem.task,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

data class TodoSchedule(
    val morningItems: List<TodoItem>,
    val dayItems: List<TodoItem>,
    val eveningItems: List<TodoItem>,
    val nightItems: List<TodoItem>
)

// Disconnect branch, Track progress in firebase
object Schedule {
    val Odd = TodoSchedule(
        morningItems = listOf(
            TodoItem("Skipping 1000+"),
            TodoItem("Squats 50+"),
            TodoItem("Lunges 50+")
        ),
        dayItems = listOf(
            TodoItem("Work 11-5")
        ),
        eveningItems = listOf(
            TodoItem("Do Theory Courses"),
            TodoItem("Do Applied Courses")
        ),
        nightItems = listOf(
            TodoItem("Build FlatInvoker"),
            TodoItem("Build Mehmaan")
        )
    )

    val Even = TodoSchedule(
        morningItems = listOf(
            TodoItem("Placeholder")
        ),
        dayItems = listOf(
            TodoItem("Work 11-5")
        ),
        eveningItems = listOf(
            TodoItem("Run 2KM +"),
            TodoItem("Biceps & Triceps"),
            TodoItem("PushUps & Heel Raises")
        ),
        nightItems = listOf(
            TodoItem("Build FlatInvoker"),
            TodoItem("Build Mehmaan")
        )
    )

    private val key = booleanPreferencesKey("isOdd")
    private suspend fun isOdd(): Boolean {
        val data = dataStore.data.firstOrNull()
        return data?.get(key) ?: false
    }

    suspend fun nextDay() {
        dataStore.edit {
            it[key] = !isOdd()
        }
    }

    suspend fun get() = if (isOdd()) Odd else Even
}

@Composable
fun Title(text: String) {
    Text(
        text = text,
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TodoList(items: List<TodoItem>) {
    Column {
        items.forEach { item ->
            TodoItem(todoItem = item)
        }
    }
}

@Composable
fun Separator() {
    Divider(
        modifier = Modifier.padding(vertical = 8.dp),
        thickness = 1.dp
    )
}

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun App() {
    var schedule by remember { mutableStateOf(Schedule.Even) }
    LaunchedEffect(Unit) {
        schedule = Schedule.get()
    }

    MaterialTheme {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Title("Morning")
            TodoList(schedule.morningItems)
            Separator()

            Title("Day")
            TodoList(schedule.dayItems)
            Separator()

            Title("Evening")
            TodoList(schedule.eveningItems)
            Separator()

            Title("Night")
            TodoList(schedule.nightItems)

            Button(onClick = {
                GlobalScope.launch {
                    Schedule.nextDay()
                    schedule = Schedule.get()
                }
            }) {
                Text("Next Day")
            }
        }
    }
}
