# Ironman Guide authoring rules

## Resource quantities

The guide must distinguish deterministic quantities from variable progress.

### Exact quantities

Use `GuideStep.withExactResources(...)` only when the route method makes the required quantity mathematically deterministic.

Example:

```java
.withExactResources(
    GuideResourceRequirement.fromOutput(
        ItemID.LOGS,
        1000,
        15
    )
)
```

For 1,000 arrow shafts at 15 shafts per log, the guide can safely show:

> You will need 67 Logs.

Exact resource totals are informational. They are not inventory-completion requirements and must not imply that the player needs to hold the full amount at once.

### Variable activities

Do not create an exact resource/count requirement when the number depends on gameplay variance.

Examples include:
- Wintertodt games needed for a target Firemaking level.
- Activities where points, XP, kills, drops, success rates, or round length vary.
- Any grind whose exact completion count cannot be guaranteed from the prescribed route.

For those steps, describe the target directly, for example:

> Train Firemaking at Wintertodt until level 99.

Do not display a fabricated exact game count.

If an estimate is ever added in the future, it must be represented separately from exact requirements and clearly labelled as an estimate.

## Rule of thumb

**Exact when calculable. No fake precision when variable.**
