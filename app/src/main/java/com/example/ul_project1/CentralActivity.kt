package com.example.ul_project1

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class CentralActivity : AppCompatActivity() {

    //gets RecipeViewModel
    private val recipeViewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_central)

        val searchView = findViewById<SearchView>(R.id.searchView)
        val recipesRecyclerView = findViewById<RecyclerView>(R.id.recipelist)

        val adapter = RecipesAdapter(emptyList()) //is responsible for displaying the list items
        recipesRecyclerView.adapter = adapter
        recipesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        //monitoring data from the ViewModel
        lifecycleScope.launch {
            recipeViewModel.recipes.collect { recipes ->
                adapter.updateRecipes(recipes)
            }
        }


        val titles = listOf(
            "Black Karaage", "Seafood Udon", "Tonkotsu Ramen", "Takoyaki", "Tempura", "Yakitori Shrimp", "Yakisoba"
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

        //data is sent to the ViewModel via the setRecipes() method
        recipeViewModel.setRecipes(titles, descriptions, images)

        //FOR THE SEARCH
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        //if searchBar is empty, we have the full list
                        recipeViewModel.resetRecipes()
                    } else {
                        //Every time the user types in the SearchView, the ViewModel's filterRecipes() method is called
                        recipeViewModel.filterRecipes(it)
                    }
                }
                return true
            }
        })
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recipelist)

        if (fragment != null) {
            recyclerView.visibility = View.VISIBLE
        }
        super.onBackPressed()
    }
}









