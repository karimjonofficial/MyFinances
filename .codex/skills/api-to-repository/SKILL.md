---
name: api-to-repository
description: Migrate Kotlin/Android ViewModels from direct API dependencies to repository-backed DTO flows. Use when working in this project to decouple ViewModels from Api or scoped-api base classes, remove officeId/companyId API constructor coupling, move API mappers into data.repositories.<context>, introduce repository DTOs, design repository interfaces from ViewModel requirements, move data event emission out of ViewModels, implement APIs/repositories with lib.data.http helpers, and update application.factories.Factory.kt.
---

# API to Repository

Use this skill to migrate one API context at a time from ViewModel-coupled API access to repository-backed data access.

## Workflow

1. Identify the selected API/context and every ViewModel that uses it.
2. Inspect the API package for `Map.kt` and API model mapping functions.
3. Remove API inheritance from `Api` or any scoped API base class.
4. Remove `officeId`, `companyId`, or equivalent scope IDs from the API constructor when present.
5. Make the remaining API constructor arguments `private`.
6. Create `data.repositories.<context-name>` for the repository layer.
7. Add DTOs for the API models consumed by ViewModels. Prefer the project's established DTO location; in this project that is currently `data.dtos.<context-name>`.
8. Move or recreate the mapper logic from the API package into the repository package so API models stay out of ViewModels.
9. Design the repository from the actual ViewModel requirements, not directly from endpoint shape.
10. Use universal interfaces from `lib.data.repositories` when they satisfy the ViewModel requirements.
11. Add repository-local interfaces in `data.repositories.<context-name>` only when shared interfaces cannot express the needed behavior.
12. Update ViewModels to depend on repository interfaces and repository DTOs.
13. Update ViewModel mapping functions to consume DTOs instead of API models.
14. Move data event emission responsibility from ViewModels into repositories.
15. Wire ViewModels to the repository even if the repository body is temporarily empty during migration.
16. Modify the API to match the repository structure and endpoint requirements.
17. Use extensions from `lib.data.http` while modifying API calls when appropriate.
18. Implement the repository by calling the modified API, mapping API models to DTOs, and emitting repository-owned data events.
19. Fix dependency construction in `application.factories.Factory.kt`.
20. Run the relevant compile/tests and repair migration breakages before finishing.

## Design Rules

- Keep ViewModels free of API models, API mappers, direct API dependencies, `HttpClient`-backed APIs, and data event emission.
- Keep mappers in `data.repositories.<context-name>`. Keep DTOs in the established DTO location; in this project prefer `data.dtos.<context-name>`.
- Keep repository interfaces narrow and caller-driven.
- Use a normal `interface` instead of `fun interface` when the method needs default parameter values.
- Preserve existing ViewModel behavior while moving API access and event emission into the repository.
- Ask for endpoint screenshots or details when endpoint behavior is specific or ambiguous.
- Avoid broad refactors outside the selected API/context, related ViewModels, repository package, and `Factory.kt`.

## Expected Shape

```text
data.repositories.<context-name>/
  <Context>Repository.kt
  <Context>Dto.kt
  Map.kt
```

Adjust names to the project conventions already used nearby.
