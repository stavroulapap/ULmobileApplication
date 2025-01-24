package com.example.ul_project1

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class UiState(
    val isLoading: Boolean = false,
    val recipes: List<Recipe> = emptyList()
)

class RecipeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> get() = _uiState

    //list with initial recipes
    private val initialRecipes = mutableListOf<Recipe>()

    //variable for current search
    var currentQuery: String? = null

    fun setRecipes(titles: List<String>, descriptions: List<String>, images: List<Int>) {
        val allRecipes = titles.zip(descriptions.zip(images)) { title, (description, imageId) ->
            Recipe(title, description, imageId)
        }
        initialRecipes.clear()
        initialRecipes.addAll(allRecipes)
        _uiState.value = UiState(isLoading = false, recipes = initialRecipes)
    }

    fun filterRecipes(query: String) {
        viewModelScope.launch {
            delay(2000) //delay 2sec before the filter
            val filtered = if (query.length >= 3) {
                //filter if the length of query is 3 or more
                initialRecipes.filter { recipe ->
                    recipe.title.contains(query, ignoreCase = true) ||
                            recipe.description.contains(query, ignoreCase = true)
                }
            } else {
                //if the query is less than 3 characters, the original recipe list is returned
                initialRecipes
            }
            _uiState.value = UiState(isLoading = false, recipes = filtered)
        }
    }


    fun resetRecipes() {
        _uiState.value = UiState(isLoading = false, recipes = initialRecipes)
    }

    fun setLoading(isLoading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = isLoading)
    }
}
