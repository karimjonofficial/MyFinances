# Remove UiText Usages

This plan removes all remaining usages of the `UiText` abstraction from the project and fixes code that was left in a broken state during the transition.

## User Review Required

> [!IMPORTANT]
> `UiText` will be completely removed from the project. All properties previously using `UiText` (like `dateTime` in `HistoryOrderCardModel`) will be converted to `String`.

## Proposed Changes

### UI Models Fixes

#### [MODIFY] [HistoryOrderCardModel.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/order/list/completed/HistoryOrderCardModel.kt)
- Fix the broken `dateTime` property by setting its type to `String`.

#### [MODIFY] [Map.kt (orders history)](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/application/viewmodels/order/list/completed/Map.kt)
- Update `toUiModel` and `toCardModel` to accept `FormatTime`.
- Fix the broken `dateTime` assignment to use `formatTime.formatTime(createdAt)`.

### Library Cleanup

#### [DELETE] [UiText.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/lib/ui/models/UiText.kt)
- Remove the `UiText` interface and its subclasses.

#### [DELETE] [str.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/lib/ui/extensions/str.kt) (if found during execution)
- If this file exists (it is imported but I couldn't locate it via `ls`), it will be removed.
- All calls to `.str()` in the project will be removed, as they will no longer be necessary for `String` or other types.

### Test Updates

#### [MODIFY] ALL Test Files
- Update all ViewModels tests that were passing `UiText.Str` or `UiText.Res` to pass raw `String` or appropriate values.
- Files identified:
    - `BaseViewModelTest.kt`
    - `OrdersHistoryContentViewModelTest.kt`
    - `OrdersListScreenViewModelTest.kt`
    - `AddProductTitleScreenViewModelTest.kt`
    - ... and others using `UiText`.

## Verification Plan

### Automated Tests
- Build the project to ensure all compilation errors are resolved.
- Run all unit tests to verify that ViewModels still function correctly with raw strings.

### Manual Verification
- Verify the **Orders History** screen to ensure date and time are displayed correctly.
- Check other screens where `UiText` was used (like Profile name/phone) to ensure they still look right.
