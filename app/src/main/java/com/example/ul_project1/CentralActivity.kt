package com.example.ul_project1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CentralActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_central)

        val recipes = listOf(
            Recipe("Black Karaage", getString(R.string.recipe_black_karaage_desc), R.drawable.black_karaage_1),
            Recipe("Seafood Udon", getString(R.string.recipe_seafood_udon_desc), R.drawable.seafood_udon_2),
            Recipe("Tonkotsu Ramen", getString(R.string.recipe_tonkotsu_ramen_desc), R.drawable.tonkotsu_ramen_3),
            Recipe("Takoyaki", getString(R.string.recipe_takoyaki_desc), R.drawable.takoyaki_4),
            Recipe("Tempura", getString(R.string.recipe_tempura_desc), R.drawable.tempura_5),
            Recipe("Yakitori Shrimp", getString(R.string.recipe_yakitori_shrimp_desc), R.drawable.yakitory_shrimp_6),
            Recipe("Yakisoba", getString(R.string.yakisoba_desc), R.drawable.yakisoba)
        )

        val recipesRecyclerView = findViewById<RecyclerView>(R.id.recipelist)
        recipesRecyclerView.adapter = RecipesAdapter(recipes)

        recipesRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container_view)
        val recyclerView = findViewById<RecyclerView>(R.id.recipelist)

        if (fragment != null) {
            recyclerView.visibility = View.VISIBLE //we can see the RecyclerView when we return from fragment by pressing back button
        }
        super.onBackPressed()
    }


    //for every item of the list
    data class Recipe(
        val title: String,
        val description: String,
        val imageId: Int
    )

    class RecipesAdapter(val recipes: List<Recipe>) :
        RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>() {

        //create the boxes on item_recipe.xml
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recipe, parent, false)
            return RecipeViewHolder(view)
        }

        //every box has one item of the list
        override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
            holder.bind(recipes[position])
        }


        override fun getItemCount(): Int {
            return recipes.size
        }

        //from item_recipe.xml
        class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            private val title: TextView = itemView.findViewById(R.id.titleRecipe)
            private val description: TextView = itemView.findViewById(R.id.descriptionRecipe)
            private val image: ImageView = itemView.findViewById(R.id.imageRecipe)
            private val shareButton: ImageView = itemView.findViewById(R.id.shareButton)
            private val likeButton: ImageView = itemView.findViewById(R.id.likeButton)

            fun bind(recipe: Recipe) {
                title.text = recipe.title
                description.text = recipe.description
                image.setImageResource(recipe.imageId)

                //when we press one item-Functionality
                itemView.setOnClickListener {
                    onItemClick(recipe)
                }

                likeButton.setOnClickListener { likeButton.setColorFilter(ContextCompat.getColor(itemView.context, R.color.error_red))
                    Toast.makeText(itemView.context, "Liked: ${recipe.title}", Toast.LENGTH_SHORT).show()
                }

                shareButton.setOnClickListener { shareButton.setColorFilter(ContextCompat.getColor(itemView.context, R.color.blue))
                    Toast.makeText(itemView.context, "Shared: ${recipe.title}", Toast.LENGTH_SHORT).show()
                }
            }


            private fun onItemClick(recipe: Recipe) {

                Toast.makeText(itemView.context, "Item clicked: ${recipe.title}", Toast.LENGTH_SHORT).show()
                val fragment = FragmentWithRecipeDetails()
                val bundle = Bundle().apply {
                    putString("recipeTitle", recipe.title)
                    putString("recipeDescription", recipe.description)
                    putInt("recipeImage", recipe.imageId)
                    putString("recipeInstructions", "Detailed instructions for ${recipe.title} are the following:")
                }

                fragment.arguments = bundle

                val activity = itemView.context as AppCompatActivity
                //we hide RecyclerView before we load the fragment for the first time(before the fragment)
                val recyclerView = activity.findViewById<RecyclerView>(R.id.recipelist)
                recyclerView.visibility = View.GONE

                activity.supportFragmentManager.beginTransaction().replace(R.id.fragment_container_view, fragment) //replace fragment_container_view(activity_central) with FragmentWithRecipeDetails.
                    .addToBackStack(null)  //add Fragment into back stack
                    .commit()
            }
        }
    }
}



