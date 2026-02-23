package org.example.project.features.categories_settings

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.example.project.domain.models.CategoryModel
import org.example.project.domain.models.ResultModel
import org.example.project.domain.usecase.catalog.GetRemoteCategoriesUseCase
import org.example.project.features.SnackBarManager

class DefaultCategoriesSettingsComponent(
    componentContext: ComponentContext,
    snackBarManager: SnackBarManager,
    private val callbacks: CategoriesSettingsCallbacks,
    private val categoriesStore: CategoriesSettingsStore,
    private val getRemoteCategoriesUseCase: GetRemoteCategoriesUseCase,
) : CategoriesSettingsComponent(
        componentContext = componentContext,
        initialState = CategoriesSettingsViewState(),
        reducer = CategoriesSettingsReducer(),
        snackBarManager = snackBarManager,
    ) {
    private var subscribeJob: Job? = null

    override fun onStart() {
        subscribeToCategories()
        if (categoriesStore.categories.value.isEmpty()) {
            loadCategories()
        }
    }

    override fun onStop() {
        subscribeJob?.cancel()
        subscribeJob = null
    }

    override fun onEvent(event: CategoriesSettingsViewEvent) {
        when (event) {
            CategoriesSettingsViewEvent.OnBackClicked -> {
                callbacks.navigateBack()
            }

            CategoriesSettingsViewEvent.OnAddCategoryClicked -> {
                callbacks.navigateToAddCategory()
            }

            is CategoriesSettingsViewEvent.OnSearchQueryChanged -> {
                reduce(event)
            }

            is CategoriesSettingsViewEvent.OnCategoryClicked -> {
                reduce(event)
                callbacks.navigateToEditCategory(event.categoryId)
            }

            is CategoriesSettingsViewEvent.OnCategoriesLoaded -> {
                reduce(event)
            }

            CategoriesSettingsViewEvent.OnLoading -> {
                reduce(event)
            }

            is CategoriesSettingsViewEvent.OnError -> {
                reduce(event)
                showError(event.error)
            }

            is CategoriesSettingsViewEvent.OnThrowError -> {
                reduce(event)
                showThrowError(event.throwable)
            }
        }
    }

    private fun subscribeToCategories() {
        subscribeJob?.cancel()
        subscribeJob = coroutineScope.launch {
            categoriesStore.categories.collect { categories ->
                onEvent(CategoriesSettingsViewEvent.OnCategoriesLoaded(categories))
            }
        }
    }

    private fun loadCategories() {
        coroutineScope.launch {
            getRemoteCategoriesUseCase
                .invoke(Unit)
                .catch {
                    withContext(Dispatchers.Main) {
                        onEvent(CategoriesSettingsViewEvent.OnThrowError(it))
                    }
                }.collect { resultModel ->
                    when (resultModel) {
                        is ResultModel.Error -> {
                            withContext(Dispatchers.Main) {
                                onEvent(
                                    CategoriesSettingsViewEvent.OnError(
                                        resultModel.message ?: "Не удалось загрузить категории",
                                    ),
                                )
                            }
                        }

                        ResultModel.Loading -> {
                            withContext(Dispatchers.Main) {
                                onEvent(CategoriesSettingsViewEvent.OnLoading)
                            }
                        }

                        is ResultModel.Success<List<CategoryModel>> -> {
                            categoriesStore.setCategories(resultModel.data)
                        }
                    }
                }
        }
    }
}
