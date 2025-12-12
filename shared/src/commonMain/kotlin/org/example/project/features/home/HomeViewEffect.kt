package org.example.project.features.home

import org.example.project.features.base.Reducer

interface HomeViewEffect: Reducer.ViewEffect {
    data object None: HomeViewEffect
}