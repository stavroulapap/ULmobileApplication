package com.example.ul_project1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment

class FragmentB: Fragment(R.layout.fragment_b) {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_b, container, false)  // Inflate the layout for this fragment
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonBackToA = view.findViewById<Button>(R.id.button_back_to_a)
        buttonBackToA.setOnClickListener {
            Toast.makeText(requireContext(), "Returning to Fragment A", Toast.LENGTH_SHORT).show()
            requireActivity().supportFragmentManager.popBackStack() // back to FragmentA
        }
    }
}

