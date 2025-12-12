package org.example.project.features

class DefaultDialogComponent(
    override val title: String,
    override val message: String,
    private val onDismissed: () -> Unit,
) : DialogComponent {

    override fun onDismissClicked() {
        onDismissed()
    }
}