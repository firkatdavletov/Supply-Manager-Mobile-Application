package org.example.project.features.app_introduction

import org.example.project.features.base.Reducer

data class AppIntroductionViewState(
    val title: String
): Reducer.ViewState
