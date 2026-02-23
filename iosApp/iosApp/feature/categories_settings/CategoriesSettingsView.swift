import SwiftUI
import Shared

struct CategoriesSettingsView: View {
    let component: CategoriesSettingsComponent

    @StateValue private var state: CategoriesSettingsViewState

    init(component: CategoriesSettingsComponent) {
        self.component = component
        _state = StateValue(component.state)
    }

    var body: some View {
        let normalizedQuery = state.searchQuery.trimmingCharacters(in: .whitespacesAndNewlines)
        let filteredCategories =
            normalizedQuery.isEmpty
            ? state.categories
            : state.categories.filter { category in
                category.title.localizedCaseInsensitiveContains(normalizedQuery)
            }

        CategoriesSettingsContent(
            title: state.title,
            categories: filteredCategories,
            searchQuery: state.searchQuery,
            selectedCategoryId: state.selectedCategoryId,
            isLoading: state.isLoading,
            onBack: {
                component.onEvent(event: CategoriesSettingsViewEventOnBackClicked())
            },
            onAddCategory: {
                component.onEvent(event: CategoriesSettingsViewEventOnAddCategoryClicked())
            },
            onSearchQueryChanged: { value in
                component.onEvent(event: CategoriesSettingsViewEventOnSearchQueryChanged(value: value))
            },
            onCategoryClicked: { categoryId in
                component.onEvent(event: CategoriesSettingsViewEventOnCategoryClicked(categoryId: categoryId))
            }
        )
        .navigationBarBackButtonHidden(true)
    }
}
