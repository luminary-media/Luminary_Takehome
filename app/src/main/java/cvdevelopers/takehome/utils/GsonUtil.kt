package cvdevelopers.takehome.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import cvdevelopers.takehome.models.Client
import java.lang.reflect.Type
import java.util.ArrayList

val listType: Type = object : TypeToken<ArrayList<Client>>() {}.type
val gson = Gson()

fun listToJson(list: List<Client>): String = gson.toJson(list, listType)
fun listFromJson(string: String): List<Client> = gson.fromJson(string, listType)