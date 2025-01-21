package com.example.ul_project1

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RecipeViewModel : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> get() = _recipes

    private val initialRecipes = mutableListOf<Recipe>()

    //each title matches the corresponding description and image
    fun setRecipes(titles: List<String>, descriptions: List<String>, images: List<Int>) {
        val allRecipes = titles.zip(descriptions.zip(images)) { title, (description, imageId) ->
            Recipe(title, description, imageId)
        }
        initialRecipes.clear()
        initialRecipes.addAll(allRecipes)
        _recipes.value = initialRecipes
    }


    fun filterRecipes(query: String) {
        if (query.length >= 3) { //filter to search if the title or description of each recipe contains the query
            _recipes.value = initialRecipes.filter { recipe ->
                recipe.title.contains(query, ignoreCase = true) || recipe.description.contains(query, ignoreCase = true)
            }
        } else {
            //If the query is less than 3 characters, the original recipe list is returned.
            _recipes.value = initialRecipes
        }
    }

    fun resetRecipes() {
        _recipes.value = initialRecipes //we have the full recipeList
    }

}
