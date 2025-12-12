package com.example.ui.theme

import androidx.annotation.FontRes
import androidx.compose.material3.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import org.example.project.R

val Default = Typography()

val AppTypography = Typography(
    displayLarge = Default.displayLarge.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.DisplayLarge.WEIGHT,
            VFConfig.DisplayLarge.WIDTH,
            VFConfig.DisplayLarge.SLANT
        )
    ),
    displayMedium = Default.displayMedium.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.DisplayMedium.WEIGHT,
            VFConfig.DisplayMedium.WIDTH,
            VFConfig.DisplayMedium.SLANT
        )
    ),
    displaySmall = Default.displaySmall.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.DisplaySmall.WEIGHT,
            VFConfig.DisplaySmall.WIDTH,
            VFConfig.DisplaySmall.SLANT
        )
    ),

    headlineLarge = Default.headlineLarge.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.HeadlineLarge.WEIGHT,
            VFConfig.HeadlineLarge.WIDTH,
            VFConfig.HeadlineLarge.SLANT
        )
    ),
    headlineMedium = Default.headlineMedium.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.HeadlineMedium.WEIGHT,
            VFConfig.HeadlineMedium.WIDTH,
            VFConfig.HeadlineMedium.SLANT
        )
    ),
    headlineSmall = Default.headlineSmall.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.HeadlineSmall.WEIGHT,
            VFConfig.HeadlineSmall.WIDTH,
            VFConfig.HeadlineSmall.SLANT
        )
    ),

    titleLarge = Default.titleLarge.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.TitleLarge.WEIGHT,
            VFConfig.TitleLarge.WIDTH,
            VFConfig.TitleLarge.SLANT
        )
    ),
    titleMedium = Default.titleMedium.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.TitleMedium.WEIGHT,
            VFConfig.TitleMedium.WIDTH,
            VFConfig.TitleMedium.SLANT
        )
    ),
    titleSmall = Default.titleSmall.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.TitleSmall.WEIGHT,
            VFConfig.TitleSmall.WIDTH,
            VFConfig.TitleSmall.SLANT
        )
    ),

    bodyLarge = Default.bodyLarge.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.BodyLarge.WEIGHT,
            VFConfig.BodyLarge.WIDTH,
            VFConfig.BodyLarge.SLANT
        )
    ),
    bodyMedium = Default.bodyMedium.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.BodyMedium.WEIGHT,
            VFConfig.BodyMedium.WIDTH,
            VFConfig.BodyMedium.SLANT
        )
    ),
    bodySmall = Default.bodySmall.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.BodySmall.WEIGHT,
            VFConfig.BodySmall.WIDTH,
            VFConfig.BodySmall.SLANT
        )
    ),

    labelLarge = Default.labelLarge.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.LabelLarge.WEIGHT,
            VFConfig.LabelLarge.WIDTH,
            VFConfig.LabelLarge.SLANT
        )
    ),
    labelMedium = Default.labelMedium.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.LabelMedium.WEIGHT,
            VFConfig.LabelMedium.WIDTH,
            VFConfig.LabelMedium.SLANT
        )
    ),
    labelSmall = Default.labelSmall.copy(
        fontFamily = variableFont(
            R.font.roboto,
            VFConfig.LabelSmall.WEIGHT,
            VFConfig.LabelSmall.WIDTH,
            VFConfig.LabelSmall.SLANT
        )
    ),
)


object VFConfig {
    object DisplayLarge { const val WEIGHT = 700; const val WIDTH = 100f; const val SLANT = 0f }
    object DisplayMedium { const val WEIGHT = 600; const val WIDTH = 100f; const val SLANT = 0f }
    object DisplaySmall { const val WEIGHT = 500; const val WIDTH = 100f; const val SLANT = 0f }

    object HeadlineLarge { const val WEIGHT = 700; const val WIDTH = 100f; const val SLANT = 0f }
    object HeadlineMedium { const val WEIGHT = 600; const val WIDTH = 100f; const val SLANT = 0f }
    object HeadlineSmall { const val WEIGHT = 500; const val WIDTH = 100f; const val SLANT = 0f }

    object TitleLarge { const val WEIGHT = 600; const val WIDTH = 100f; const val SLANT = 0f }
    object TitleMedium { const val WEIGHT = 550; const val WIDTH = 100f; const val SLANT = 0f }
    object TitleSmall { const val WEIGHT = 500; const val WIDTH = 100f; const val SLANT = 0f }

    object BodyLarge { const val WEIGHT = 450; const val WIDTH = 100f; const val SLANT = 0f }
    object BodyMedium { const val WEIGHT = 450; const val WIDTH = 100f; const val SLANT = 0f }
    object BodySmall { const val WEIGHT = 400; const val WIDTH = 100f; const val SLANT = 0f }

    object LabelLarge { const val WEIGHT = 600; const val WIDTH = 100f; const val SLANT = 0f }
    object LabelMedium { const val WEIGHT = 550; const val WIDTH = 100f; const val SLANT = 0f }
    object LabelSmall { const val WEIGHT = 500; const val WIDTH = 100f; const val SLANT = 0f }
}

@OptIn(ExperimentalTextApi::class)
private fun variableFont(
    @FontRes fontRes: Int,
    weight: Int,
    width: Float,
    slant: Float
): FontFamily = FontFamily(
    Font(
        fontRes,
        variationSettings = FontVariation.Settings(
            FontVariation.weight(weight),
            FontVariation.width(width),
            FontVariation.slant(slant),
        )
    )
)
