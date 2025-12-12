package org.example.project.features.app_introduction

import org.example.project.features.base.Reducer

sealed interface AppIntroductionViewEvent: Reducer.ViewEvent {
    data object OnContinue: AppIntroductionViewEvent
}