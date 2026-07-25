# Refactor Branch Selection UI to use Button

Refactor the branch selection UI in the `ProfileContent` screen to use a Material 3 `Button` component instead of a manually styled `Row`. This improves interaction handling and aligns with modern Material design patterns.

## Proposed Changes

### UI Screens

#### [MODIFY] [ProfileContent.kt](file:///D:/Dev/Mobile/Learn/MyFinances/app/src/main/java/com/orka/myfinances/ui/screens/profile/ProfileContent.kt)

- Replace the manual `Row` with `FilledTonalButton`.
- Use `ButtonDefaults.filledTonalButtonColors` to preserve the `secondary` color scheme.
- Apply a `RoundedCornerShape(50)` to maintain the "pill" visual style.
- Set appropriate `contentPadding` to ensure the layout remains compact.

## Verification Plan

### Manual Verification
- Deploy the app and navigate to the Profile screen.
- Verify that the branch selection "pill" looks correct and functions as a button.
- Ensure the ripple effect and interaction states (press/focus) are handled correctly.
- Verify that clicking the button still opens the branch selection bottom sheet.
