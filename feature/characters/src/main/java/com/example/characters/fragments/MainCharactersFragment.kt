package com.example.characters.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.characters.viewmodels.MainCharactersViewModel
import com.example.characters.R

class MainCharactersFragment : Fragment() {

    companion object {
        fun newInstance() = MainCharactersFragment()
    }

    private lateinit var viewModel: MainCharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_characters, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainCharactersViewModel::class.java)
        // TODO: Use the ViewModel
    }

}