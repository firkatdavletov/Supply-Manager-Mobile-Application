package org.example.project.feature.map_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import org.example.project.databinding.DepartmentMapObjLayoutBinding

class DepartmentMapObjView @JvmOverloads constructor(
    context: Context, attrs:
    AttributeSet? = null,
    defStyleAttr: Int = 0,
    ) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: DepartmentMapObjLayoutBinding = DepartmentMapObjLayoutBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setText(text: String) {
        binding.label.text = text
    }

    fun select(selected: Boolean) {
        binding.label.visibility = if (selected) VISIBLE else INVISIBLE
    }
}