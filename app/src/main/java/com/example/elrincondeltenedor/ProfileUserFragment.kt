package com.example.elrincondeltenedor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.elrincondeltenedor.R
import com.example.elrincondeltenedor.databinding.ScreenUserProfileBinding

class ProfileUserFragment : Fragment(R.layout.screen_user_profile) {

    private var _binding: ScreenUserProfileBinding? = null
    private val binding get() = _binding!!

    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Acción para cambiar la foto de perfil
        binding.cambiarFoto.setOnClickListener {
            openGallery()
        }

        // Acción para cerrar sesión
        binding.cerrarSesion.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }
    }

    // Método para abrir la galería de imágenes
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    // Manejar el resultado de la selección de la imagen
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val selectedImage: Uri? = data.data
            if (selectedImage != null) {
                binding.fotoPerfil.setImageURI(selectedImage)
            } else {
                Toast.makeText(context, "No se seleccionó ninguna imagen.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
