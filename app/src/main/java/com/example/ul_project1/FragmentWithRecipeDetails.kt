package com.example.ul_project1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.TextView

class FragmentWithRecipeDetails : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_with_recipe_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("recipeTitle")
        val description = arguments?.getString("recipeDescription")
        val instructions = arguments?.getString("recipeInstructions") ?: "No instructions available"
        val imageId = arguments?.getInt("recipeImage") ?: R.drawable.no_image_found //default image if it is  null

        view.findViewById<TextView>(R.id.recipeTitle).text = title
        view.findViewById<TextView>(R.id.recipeDescription).text = description
        view.findViewById<TextView>(R.id.recipeInstructions).text = instructions
        view.findViewById<ImageView>(R.id.recipeImage).setImageResource(imageId)
    }
}
