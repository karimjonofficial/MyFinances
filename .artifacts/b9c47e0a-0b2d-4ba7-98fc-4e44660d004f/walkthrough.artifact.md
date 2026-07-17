# Walkthrough: SelectDefaultCategory with Local Selection

I have updated the `SelectDefaultCategory` screen to support local selection with a confirmation button in the bottom bar.

## Changes Made

### UI Layer
- [NEW] [SelectDefaultCategoryScreenModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/defaults/category/SelectDefaultCategoryScreenModel.kt): New state model containing the category map and the initial `defaultId`.
- [MODIFY] [SelectDefaultCategory.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/defaults/category/SelectDefaultCategory.kt):
    - Switched to `StatefulScreen` for standardized loading/failure handling.
    - Added local `selectedId` state using `rememberSaveable`.
    - Implemented a `SingleActionBottomBar` with a "Save" button that is enabled only when a new category is selected.
    - Added a `RadioButton` to each item to indicate the current local selection.

### Application Layer
- [MODIFY] [SelectDefaultCategoryViewModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/application/viewmodels/defaults/category/SelectDefaultCategoryViewModel.kt):
    - Now inherits from `BaseViewModel<SelectDefaultCategoryScreenModel>`, which simplifies data production and state management.
    - Correctly fetches both the categories (via `FolderRepository`) and the current default (via `GetDefaultCategory`).
    - Implemented `select(id: Id)` to persist the choice, navigate back using `Navigator`, and update the success state with the new `defaultId`.
- [MODIFY] [Factory.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/factories/Factory.kt): Updated to inject `GetDefaultCategory` and `Navigator` into the ViewModel.

## Verification Results

- Verified the build succeeds.
- The UI correctly reflects the local selection and enables the "Save" button appropriately.
- The grouping logic remains intact.
