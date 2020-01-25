package pl.mbachorski.todolist.di

import org.koin.dsl.module
import pl.mbachorski.todolist.ActivityProvider

val activityModule = module {
    single { ActivityProvider(get()) }
}