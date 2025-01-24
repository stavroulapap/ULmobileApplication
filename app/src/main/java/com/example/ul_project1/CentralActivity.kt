package com.example.ul_project1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CentralActivity : AppCompatActivity() {

    //gets RecipeViewModel
    private val recipeViewModel: RecipeViewModel by viewModels()
    private lateinit var credentialsManager: CredentialsManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_central)

        //get CredentialsManager from Application
        credentialsManager = (applicationContext as MyApp).credentialsManager

        val searchView = findViewById<SearchView>(R.id.searchView)
        val recipesRecyclerView = findViewById<RecyclerView>(R.id.recipelist)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val logoutButton = findViewById<Button>(R.id.logoutButton)


        val adapter = RecipesAdapter(emptyList()) //is responsible for displaying the list items
        recipesRecyclerView.adapter = adapter
        recipesRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            recipeViewModel.uiState.collect { uiState ->
                if (uiState.isLoading) {
                    progressBar.visibility = View.VISIBLE //show progress when loading
                    recipesRecyclerView.visibility = View.GONE //hide recipes while loading
                } else {
                    progressBar.visibility = View.GONE //hide progress when loading is complete
                    recipesRecyclerView.visibility = View.VISIBLE //show recipes when loading is complete
                }
                adapter.updateRecipes(uiState.recipes)
            }
        }

        loadAllRecipes(progressBar)

        //handle search query changes
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    recipeViewModel.currentQuery = it //stores the current search
                    //show loading state and hide recipes for 2 seconds
                    recipeViewModel.setLoading(true)
                    lifecycleScope.launch {
                        //wait for 300ms after user stops typing
                        delay(300)
                        if (it.isEmpty()) {
                            recipeViewModel.resetRecipes() //if searchBar is empty, we have the full list
                        } else {
                            recipeViewModel.filterRecipes(it) //filter recipes based on query
                        }
                        //hide loading and show recipes after filtering
                        recipeViewModel.setLoading(false)
                    }
                }
                return true
            }
        })

        logoutButton.setOnClickListener {
            setLogoutButton(logoutButton)
        }
    }

    override fun onResume() {
        super.onResume()
        val currentQuery = recipeViewModel.currentQuery
        if (currentQuery.isNullOrEmpty()) {
            recipeViewModel.resetRecipes() //reset all the recipes if there is no search
        } else {
            recipeViewModel.filterRecipes(currentQuery) //filter when there is search
        }
    }


    //set the initial recipes and show progress bar for 2 seconds
    private fun loadAllRecipes(progressBar: ProgressBar) {
        lifecycleScope.launch {
            progressBar.visibility = View.VISIBLE
            delay(2000) // Wait for 2 seconds before showing the recipes
            val titles = listOf(
                "Black Karaage", "Seafood Udon", "Tonkotsu Ramen", "Takoyaki", "Tempura",
                "Yakitori Shrimp", "Yakisoba"
            )
            val descriptions = listOf(
                getString(R.string.recipe_black_karaage_desc),
                getString(R.string.recipe_seafood_udon_desc),
                getString(R.string.recipe_tonkotsu_ramen_desc),
                getString(R.string.recipe_takoyaki_desc),
                getString(R.string.recipe_tempura_desc),
                getString(R.string.recipe_yakitori_shrimp_desc),
                getString(R.string.yakisoba_desc)
            )
            val images = listOf(
                R.drawable.black_karaage_1, R.drawable.seafood_udon_2, R.drawable.tonkotsu_ramen_3,
                R.drawable.takoyaki_4, R.drawable.tempura_5, R.drawable.yakitory_shrimp_6, R.drawable.yakisoba
            )
            recipeViewModel.setRecipes(titles, descriptions, images)
        }
    }

    //setup logout button to navigate back to the login screen
    private fun setLogoutButton(logoutButton: Button) {
            val loginFragment = LoginFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, loginFragment)
                .addToBackStack(null)
                .commit()
        finish()  // End of central activity

    }
}