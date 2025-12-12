package org.example.project.features.search_address

import org.example.project.features.base.Reducer

sealed interface SearchAddressViewEffect : Reducer.ViewEffect {
    data object None : SearchAddressViewEffect
}