package cvdevelopers.takehome.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cvdevelopers.takehome.repository.Repository
import cvdevelopers.takehome.models.Client
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UsersViewModel(
        private val repository: Repository
) : ViewModel() {

    val mUsersList = MutableLiveData<List<Client>>()

    fun getUsers(page: String = "1") = viewModelScope.launch(Dispatchers.IO) {
        Log.d("get users::", "getting users")
        mUsersList.postValue(repository.getSearchResults(page))
    }

    fun clearCache() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.clearDatabase()
        }
    }
}

//picture,first, last, gender, email