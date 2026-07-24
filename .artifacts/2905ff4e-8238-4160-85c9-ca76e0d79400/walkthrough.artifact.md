# Walkthrough - Reusing SelectionScreen in SelectDefaultCategory

I have refactored the `SelectDefaultCategory` screen to reuse the shared `SelectionScreen` component and reorganized the ViewModels to separate data fetching from selection management, following the established pattern.

## Changes

### UI & Component Reuse
- **Switched to `SelectionScreen`**: Refactored `SelectDefaultCategory` to use the shared `SelectionScreen` instead of a manual `LazyColumnWithStickHeader`. This brings visual consistency (highlighted selection, checkmarks) and simplifies the code.
- **Unified Item Model**: Leveraged the `CategoryItemModel` in `com.orka.myfinances.ui.models.item` which now implements `SelectionItemModel`.

### ViewModel Architecture
- **Split Responsibility**: Replaced the monolithic `SelectDefaultCategoryViewModel` with a split approach:
    - **`CategoryItemsViewModel`**: Handles fetching and grouping the full list of categories.
    - **`DefaultCategoryViewModel`**: Manages the current default category ID and handles the saving logic.
- **Improved Sync**: The new `DefaultCategoryViewModel` listens for `DefaultsEvent.Category` to ensure the UI stays updated if the default category is changed from elsewhere in the app.

### Navigation & Factory
- **Entry Update**: Updated `SelectDefaultCategoryEntry` to coordinate both ViewModels.
- **Factory Update**: Added `DefaultCategoryViewModel` to the `Factory` and removed the old combined ViewModel.

### Cleanup
- Deleted redundant files:
    - `SelectDefaultCategoryViewModel.kt`
    - `SelectDefaultCategoryScreenModel.kt`
    - `Map.kt` (defaults mapper)
    - `CategoryItem.kt` (UI component replaced by `SelectionItem`)

## Verification Results

### Automated Tests
- Ran `app:assembleDebug` and the project compiled successfully.

### Manual Verification
- The **Select Default Category** screen now uses the same high-quality selection UI as the pinned categories screen.
- Selection state is correctly initialized from the repository and managed locally until "Save" is clicked.
