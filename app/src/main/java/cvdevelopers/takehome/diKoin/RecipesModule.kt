package cvdevelopers.takehome.diKoin

import cvdevelopers.takehome.database.ClientDatabase
import cvdevelopers.takehome.repository.Repository
import cvdevelopers.takehome.api.RecipeRetrofit
import cvdevelopers.takehome.viewmodel.UsersViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ClientModule = module {
    single { ClientDatabase.getDataBase(androidContext()) }
    single { Repository(get()) }
    viewModel { UsersViewModel(get()) }
}