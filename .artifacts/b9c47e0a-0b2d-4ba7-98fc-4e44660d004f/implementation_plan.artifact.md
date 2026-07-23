# Implementation Plan - SelectionScreen in PinnedCategories.kt

This plan covers the implementation of a generic `SelectionScreen` composable in `PinnedCategories.kt`.

## User Review Required

> [!IMPORTANT]
> The `SelectionScreen` is generic and highly customizable via the `itemContent` slot.

## Proposed Changes

### UI Components

#### [MODIFY] [PinnedCategories.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/navigation/PinnedCategories.kt)
- Implement `SelectionScreen<T>` generic composable with the following signature:
```kotlin
@Composable
fun <T> SelectionScreen(
    modifier: Modifier = Modifier,
    title: String,
    items: List<T>,
    isSelected: (T) -> Boolean,
    onSelected: (Boolean, T) -> Unit,
    itemContent: @Composable (Boolean, T) -> Unit
)
```
- Use `Scaffold` from `com.orka.myfinances.lib.ui.components`.
- Use `LazyColumn` to render `items`.
- Each item in the `LazyColumn` will call the `itemContent` slot with the current selection state (via `isSelected`) and the item itself.

## Verification Plan

### Manual Verification
- Verify the code compiles.
- Check that the `Scaffold` title is correctly displayed.
- Verify that the generic slot `itemContent` receives the correct parameters.
