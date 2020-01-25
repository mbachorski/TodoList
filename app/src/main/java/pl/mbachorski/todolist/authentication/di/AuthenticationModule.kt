package pl.mbachorski.todolist.authentication.di

import org.koin.dsl.module
import pl.mbachorski.todolist.ActivityProvider
import pl.mbachorski.todolist.authentication.AuthenticationService
import pl.mbachorski.todolist.authentication.FirebaseAuthenticationService

val authenticationModule = module {
    factory { FirebaseAuthenticationService(get<ActivityProvider>().activeActivity) as AuthenticationService }
}