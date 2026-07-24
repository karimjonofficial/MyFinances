# Improve Profile Header Visual Separation

Replace the `HorizontalDivider` in the profile screen with a distinct background color for the top user data section to create a cleaner and more modern visual separation.

## Proposed Changes

### UI Components

#### [MODIFY] [ProfileContent.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/profile/ProfileContent.kt)

- Wrap the user data section (Icon, Name, Phone, Branch dropdown) in a `Column` with a background color.
- Use `MaterialTheme.colorScheme.surfaceContainer` for the header background.
- Remove the `HorizontalDivider`.
- Add appropriate padding to the header to ensure content is well-spaced within the new background.
- Apply `RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)` to the header background for a smoother transition to the options list.

## Verification Plan

### Manual Verification
- Deploy the app and navigate to the Profile screen.
- Verify that the top section has a distinct background color that ends where the options list begins.
- Check that the transition between the header and the list looks natural and "prettier" than the divider.
- Ensure the dropdown menu still works correctly within the new layout.
