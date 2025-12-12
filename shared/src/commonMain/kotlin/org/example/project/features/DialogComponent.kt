package org.example.project.features

interface DialogComponent {
    val title: String
    val message: String
    fun onDismissClicked()
}