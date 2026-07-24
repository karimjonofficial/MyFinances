# Refactor SelectDefaultCategory to reuse SelectionScreen and separate ViewModels

Following the pattern used in `PinnedCategoriesScreen`, I will refactor `SelectDefaultCategory` to use the shared `SelectionScreen` component and split its data fetching and selection management into two separate ViewModels.

## Proposed Changes

### [Component Name]

#### [NEW] [DefaultCategoryViewModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/application/viewmodels/defaults/category/DefaultCategoryViewModel.kt)
- Create a new ViewModel to manage the default category selection state (`Id?`).
- It will implement `SelectDefaultCategoryInteractor` to handle selection persistence.
- It will use `BaseViewModel<Id?>` and listen for `DefaultsEvent.Category` to stay in sync.

#### [MODIFY] [SelectDefaultCategory.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/settings/defaults/category/SelectDefaultCategory.kt)
- Update the signature to accept two separate states: one for the category list (`Map<String, List<CategoryItemModel>>`) and one for the current default ID (`Id?`).
- Replace `StatefulScreen` and `LazyColumnWithStickHeader` with the shared `SelectionScreen`.
- Use `localSelectedId` to manage the single selection state within the screen.

#### [MODIFY] [SelectDefaultCategoryEntry.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/navigation/entries/defaults/category/SelectDefaultCategoryEntry.kt)
- Inject both `CategoryItemsViewModel` (for the list) and `DefaultCategoryViewModel` (for the selection).
- Connect their states and the interactor to the `SelectDefaultCategory` composable.

#### [MODIFY] [Factory.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/factories/Factory.kt)
- Add a provider method for the new `DefaultCategoryViewModel`.
- Remove the provider for the old `SelectDefaultCategoryViewModel`.

#### [DELETE] [SelectDefaultCategoryViewModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/application/viewmodels/defaults/category/SelectDefaultCategoryViewModel.kt)
- Replaced by the split ViewModel approach.

#### [DELETE] [SelectDefaultCategoryScreenModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/settings/defaults/category/SelectDefaultCategoryScreenModel.kt)
- No longer needed as we use separate states for the list and selection.

#### [DELETE] [Map.kt (defaults)](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/application/viewmodels/defaults/category/Map.kt)
- No longer needed as mapping is handled by `CategoryItemsViewModel`.

## Verification Plan

### Automated Tests
- Build the project to verify that all dependencies and imports are correctly updated.

### Manual Verification
- Navigate to the **Select Default Category** screen.
- Verify that the list of categories is displayed correctly (grouped by first letter).
- Verify that selecting a category highlights it and shows a checkmark.
- Verify that clicking "Save" updates the default category and navigates back.
- Ensure `PinnedCategoriesScreen` still works correctly after these changes.
