package org.example.project.features.app_introduction

import org.example.project.features.base.Reducer

sealed interface AppIntroductionViewEffect: Reducer.ViewEffect {
    data object None: AppIntroductionViewEffect
}