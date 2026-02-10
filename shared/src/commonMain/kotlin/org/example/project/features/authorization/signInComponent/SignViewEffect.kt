package org.example.project.features.authorization.signInComponent

import org.example.project.features.base.Reducer

sealed interface SignViewEffect : Reducer.ViewEffect {
    data object None : SignViewEffect
}