package com.example.elrincondeltenedor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.elrincondeltenedor.databinding.RestaurantDetailScreenBinding
import com.example.elrincondeltenedor.databinding.SettingScreenBinding

class DetailsFragment : Fragment() {
    private lateinit var _binding: RestaurantDetailScreenBinding
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RestaurantDetailScreenBinding.inflate(inflater, container, false)
        return binding.root
    }
}