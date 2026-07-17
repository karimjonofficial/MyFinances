# Update SelectDefaultCategory Screen with Local Selection

Modify the `SelectDefaultCategory` screen to support local selection (UI state) before confirming the change via a bottom bar button.

## User Review Required

> [!NOTE]
> The state type will change to `State<SelectDefaultCategoryScreenModel>`. The UI will maintain its own `selectedId` state to track local changes before saving.

## Proposed Changes

### UI Layer

#### [NEW] [SelectDefaultCategoryScreenModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/defaults/category/SelectDefaultCategoryScreenModel.kt)
Create a new model:
```kotlin
data class SelectDefaultCategoryScreenModel(
    val map: Map<String, List<SelectDefaultCategoryItemModel>>,
    val defaultId: Id?
)
```

#### [MODIFY] [SelectDefaultCategoryInteractor.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/defaults/category/SelectDefaultCategoryInteractor.kt)
Ensure `select(id: Id)` is used to persist the selection.

#### [MODIFY] [SelectDefaultCategory.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/defaults/category/SelectDefaultCategory.kt)
- Update to use `StatefulScreen<SelectDefaultCategoryScreenModel>`.
- Use `rememberSaveable` to hold a local `selectedId`, initialized from `model.defaultId`.
- Implement `LazyColumnWithStickHeader` in the success content.
- Add `SingleActionBottomBar` for the save action.
- The button is enabled if `selectedId != model.defaultId`.
- Add a `RadioButton` indicator to list items.

### Application Layer (ViewModel)

#### [MODIFY] [SelectDefaultCategoryViewModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/application/viewmodels/defaults/category/SelectDefaultCategoryViewModel.kt)
- Change base class to `StateFulViewModel<State<SelectDefaultCategoryScreenModel>>`.
- Implement `initialize` and `refresh` to fetch both categories (filtered) and the current `defaultId`.
- Implement `select(id: Id)` to call `setDefaultCategoryId` on the repository and update the `defaultId` in the local state.

### Navigation Layer

#### [MODIFY] [SelectDefaultCategoryEntry.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/navigation/entries/defaults/category/SelectDefaultCategoryEntry.kt)
Update the state observation to use the new `SelectDefaultCategoryScreenModel`.

## Verification Plan

### Manual Verification
- Navigate to the Select Default Category screen.
- Verify that items can be selected locally (showing an indicator).
- Verify that the bottom bar button is enabled only when a *different* category is selected.
- Verify that clicking "Save" updates the default category and updates the button state.
