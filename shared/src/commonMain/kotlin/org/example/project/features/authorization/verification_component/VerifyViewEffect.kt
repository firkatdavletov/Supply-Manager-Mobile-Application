package org.example.project.features.authorization.verification_component

import org.example.project.features.base.Reducer

sealed interface VerifyViewEffect : Reducer.ViewEffect {
    data object None: VerifyViewEffect
}