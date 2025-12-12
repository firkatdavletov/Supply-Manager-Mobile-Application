package org.example.project.features.authorization.sign_in_component

import org.example.project.features.base.Reducer

sealed interface SignViewEffect: Reducer.ViewEffect {
    data object None: SignViewEffect
}