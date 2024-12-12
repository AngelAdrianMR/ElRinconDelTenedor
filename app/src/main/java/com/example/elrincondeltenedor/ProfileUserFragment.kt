package com.example.elrincondeltenedor

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.appcompat.app.AlertDialog
import com.example.elrincondeltenedor.databinding.ScreenUserProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.UserProfileChangeRequest

class ProfileUserFragment : Fragment(R.layout.screen_user_profile) {

    private var _binding: ScreenUserProfileBinding? = null
    private val binding get() = _binding!!

    private val PICK_IMAGE_REQUEST = 1

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private var username: String = "Usuario desconocido" // Nombre por defecto

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ScreenUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener el nombre de usuario desde Firebase
        val user = auth.currentUser
        username = user?.displayName ?: "Usuario desconocido"
        binding.nombreUsuario.text = username // Mostrar el nombre en el TextView

        // Acción para cambiar la foto de perfil
        binding.cambiarFoto.setOnClickListener {
            openGallery()
        }

        // Acción para cerrar sesión
        binding.cerrarSesion.setOnClickListener {
            findNavController().navigate(R.id.loginFragment)
        }

        // Acción para cambiar el nombre de usuario
        binding.nombreUsuario.setOnClickListener {
            showUsernameChangeDialog()
        }
    }

    // Mostrar un cuadro de diálogo para cambiar el nombre de usuario
    private fun showUsernameChangeDialog() {
        // Crear un cuadro de diálogo para cambiar el nombre
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Cambiar nombre de usuario")

        // Campo de texto para ingresar el nuevo nombre
        val input = EditText(requireContext())
        input.setText(username) // Prellenar el campo con el nombre actual
        builder.setView(input)

        builder.setPositiveButton("Aceptar") { dialog, _ ->
            val newName = input.text.toString().trim()
            if (newName.isNotEmpty()) {
                // Actualizar el nombre en Firebase Authentication
                val user = auth.currentUser
                user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(newName).build())
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Si se actualizó el nombre en Firebase, actualizamos el nombre local
                            username = newName
                            binding.nombreUsuario.text = username
                            Toast.makeText(context, "Nombre de usuario actualizado", Toast.LENGTH_SHORT).show()
                            // También actualizar el nombre en Firestore
                            updateUserNameInFirestore(newName)
                        } else {
                            Toast.makeText(context, "Error al actualizar el nombre", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        builder.show()
    }

    // Función para actualizar el nombre en Firestore
    private fun updateUserNameInFirestore(newName: String) {
        val user = auth.currentUser
        val userRef = db.collection("usuarios").document(user?.uid ?: "desconocido")
        userRef.update("nombre", newName)
            .addOnSuccessListener {
                Toast.makeText(context, "Nombre actualizado en Firestore", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error al actualizar el nombre en Firestore: ${e.message}", Toast.LENGTH_SHORT).show()
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
