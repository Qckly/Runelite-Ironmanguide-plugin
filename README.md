<p align="center">
  <img src="docs/logo.png" width="180" alt="Ironman Guide logo">
</p>

<h1 align="center">Ironman Guide</h1>

<p align="center">
  A step-by-step RuneLite progression guide for Old School RuneScape Ironman accounts.
</p>

<p align="center">
  <strong>One continuous Ironman route, guided in-game like one giant Quest Helper.</strong>
</p>

---

## About

**Ironman Guide** is a RuneLite plugin that turns a long-form Ironman progression route into one continuous in-game guide.

Instead of constantly switching between a browser guide, the OSRS Wiki, notes, quest guides, and RuneLite, the project aims to keep the route inside the client and tell the player:

- what to do next;
- where to go;
- which NPC, object, item, or interface matters;
- what should be in the inventory or bank;
- how many resources are actually required;
- when a step is already complete from the player's current game state.

The long-term goal is:

> Create a fresh Ironman, enable Ironman Guide, and follow a complete progression route from the early game toward late-game account goals without already needing to know the route.

The project is currently in **active development** and is being validated through real gameplay.

## Route / guide source

The progression route is currently based primarily on the community **OSRS Ironman Guide** at:

**https://ironman.guide/guide/early-game**

The current implementation follows the early route beginning with **Section 1.1 — Early quests, wintertodt and ardy cloak 1**.

Ironman Guide is **not intended to be a direct text mirror** of the website. The source route is adapted into plugin-friendly steps so RuneLite can track and guide the player automatically.

That means a single website instruction may be split into several plugin steps when needed for:

- inventory preparation;
- bank withdrawals or deposits;
- exact resource targets;
- NPC/object highlighting;
- navigation;
- dialogue guidance;
- quest hand-offs;
- automatic completion checks.

Steps may also be clarified or adjusted when required to work with the current game state or current RuneLite APIs.

The original route remains the progression reference; this plugin is the in-game execution layer.

## Current progress

The plugin currently contains **64 ordered guide steps** across the following route sections:

- Starting off
- Lumbridge setup
- Training and preparation
- Leaving Lumbridge
- Draynor and X Marks
- Falador and Ferox
- Yanille and Port Khazard
- Ardougne and Gnome Stronghold

The implemented route currently reaches **Sea Slug**.

Current progression includes, among other things:

- early Lumbridge account setup;
- X Marks the Spot;
- Rune Mysteries preparation;
- early Firemaking and Fletching;
- The Restless Ghost setup;
- early Thieving;
- The Knight's Sword start;
- Ferox Enclave and Castle Wars routing;
- Monk's Friend;
- 35 Woodcutting;
- 30 Firemaking;
- 25 Thieving;
- Sheep Herder;
- Sea Slug.

The original guide continues far beyond this point and will be implemented progressively.

## Core features

| Feature | Status |
| --- | :---: |
| Continuous step-by-step Ironman route | ✅ |
| Stable persistent step IDs | ✅ |
| Automatic step completion | ✅ |
| NPC highlighting | ✅ |
| Object highlighting | ✅ |
| Ground-item highlighting | ✅ |
| Inventory item highlighting | ✅ |
| Dialogue guidance | ✅ |
| Location / area guidance | ✅ |
| Minimap guidance | ✅ |
| World map guidance | ✅ |
| Optional Shortest Path routing | ✅ |
| Bank-aware item requirements | ✅ |
| Current-step bank filter | ✅ |
| Quest-state detection | ✅ |
| Quest Helper integration | ✅ |
| Production / Make-X highlighting | ✅ |
| Dynamic exact resource requirements | ✅ |
| Full early-game route | 🚧 |
| Mid-game route | Planned |
| Late-game route | Planned |

## Integrations and other RuneLite plugins

Ironman Guide intentionally works with existing RuneLite systems instead of rebuilding everything from scratch.

### Quest Helper

**Quest Helper** is used for dedicated quest execution.

Project:

**https://github.com/Zoinkwiz/quest-helper**

Plugin Hub:

**https://runelite.net/plugin-hub/show/quest-helper**

Ironman Guide decides **when a quest belongs in the overall Ironman route**. When the route reaches a supported quest, the plugin can hand that quest over to Quest Helper.

For example:

```text
Ironman Guide
    ↓
Prepare required items / stats
    ↓
Start quest step
    ↓
Quest Helper handles detailed quest execution
    ↓
Ironman Guide detects completion
    ↓
Continue the account route
```

This allows Ironman Guide to focus on the entire account journey while Quest Helper continues doing what it does best: detailed quest-by-quest guidance.

### Shortest Path

**Shortest Path** is an optional navigation integration.

Plugin Hub:

**https://runelite.net/plugin-hub/show/shortest-path**

Enable:

```text
Ironman Guide settings
→ Navigation
→ Use Shortest Path plugin
```

When enabled, Ironman Guide sends the current guide destination to the Shortest Path plugin, which can draw the route to that target.

When disabled, Ironman Guide uses its own direction-arrow guidance.

The target itself can still use Ironman Guide's cyan area, NPC, or object highlighting regardless of which navigation option is selected.

### Bank Tags

Ironman Guide uses RuneLite's built-in **Bank Tags** services for its current-step bank filter.

When a step requires specific items from the bank, the Ironman Guide bank button can filter the bank down to the items relevant to the current step.

Bank Tags is a RuneLite core plugin dependency and does not require a separate Plugin Hub installation.

## Visual guidance

Depending on the current step, Ironman Guide can highlight or guide the player toward:

- NPCs;
- game objects;
- ground items;
- exact locations;
- larger target areas;
- inventory items;
- bank items;
- dialogue choices;
- production / Make-X interfaces.

The target/highlight system is separate from navigation.

For example, a step may use:

```text
Shortest Path route
        +
cyan destination area
        +
highlighted NPC
```

The objective is to remove as much ambiguity as possible: the player should know both **what** to do and **where/how** to do it.

## Automatic progression

Ironman Guide attempts to complete steps from real game state whenever possible.

Examples include:

- skill level reached;
- quest started or finished;
- required item owned;
- required quantity stored in the bank;
- required item physically present in inventory;
- required item equipped;
- target location reached;
- tracked action performed.

The guide then advances to the next stable step automatically.

Manual navigation through the step list is still useful during development and testing.

## Inventory and bank awareness

The plugin treats different item requirements differently.

For example:

```text
HAVE_ITEM
```

means the item must be in the inventory unless that rule explicitly allows the bank.

```text
BANK_HAS_ITEM
```

means the required quantity must be stored in the bank.

```text
ALL_OWNED_IN_INVENTORY
```

means the player's entire currently owned stack should be withdrawn.

This distinction prevents situations where the guide says a preparation step is complete while the required item is still sitting in the bank.

## Dynamic resource tracking

When a resource requirement can be calculated, Ironman Guide can display the actual amount needed instead of relying on vague text.

Example:

```text
Fletch 1,000 Arrow shafts
You will need 67 Logs.
```

The resource engine can take current progress into account, so previously collected resources are not ignored.

The same system is intended to support deterministic training goals such as:

- Woodcutting;
- Firemaking;
- Fletching;
- Crafting;
- Smithing;
- other resource-based progression steps.

## Cumulative route resources

Resource targets can represent the **total amount needed by the route**, not just the amount mentioned in one sentence.

Example:

```text
Earlier route requirement: 4 Logs
Later route requirement:   +7 Logs
Cumulative target:          11 Logs
```

If the player already owns 6 Logs, the plugin can show progress against the 11-log route target instead of asking the player to collect another full 11.

This is especially important for an Ironman route where items are gathered, banked, reused, and consumed across many different steps.

## Step design

The guide is designed around a few different kinds of requirements:

### State

A condition that can be recomputed from the live game state.

Examples:

- skill level;
- item quantity;
- bank quantity;
- equipped item;
- quest state;
- location.

These should not be permanently stored when RuneLite can reliably calculate them again.

### Action

An action matters because performing the action itself is part of the route.

Examples may include:

- buying an item;
- selling an item;
- dropping an item;
- specific tracked interactions.

### Resource / stockpile

A cumulative route resource target.

This is used when later parts of the route depend on resources collected earlier.

### Guidance

Some rules exist only to tell or show the player what to do and should not block completion.

For example, highlighting a Knife → Logs interaction can be useful even when the actual completion condition is the number of Arrow shafts produced.

## Guide structure

Internally, the route is split into maintainable chapter files, but the player experiences it as one continuous guide.

Current chapter files include:

```text
StartingOffGuideData
LumbridgeSetupGuideData
TrainingPreparationGuideData
LeavingLumbridgeGuideData
DraynorXMarksGuideData
FaladorFeroxGuideData
YanilleKhazardGuideData
ArdougneGnomeGuideData
```

Each step has a stable ID, for example:

```text
early_020_fletch_1000_arrow_shafts
early_041_finish_x_marks
early_064_complete_sea_slug
```

Saved progress therefore does not depend entirely on a fragile numeric array position when guide data is reorganized.

The guide data is also validated for structural problems such as:

- missing step IDs;
- duplicate step IDs;
- missing sections;
- missing titles;
- null steps.

## Development

### Requirements

- JDK 17 recommended for the local development environment
- RuneLite development dependencies
- Gradle wrapper included in this repository

### Run the development client

On Windows:

```powershell
.\gradlew run
```

### Build

```powershell
.\gradlew clean build
```

## Installation

Ironman Guide is **not yet a finished RuneLite Plugin Hub release**.

The repository is currently intended for development and gameplay testing.

Until the Plugin Hub release is ready, users should treat this as an experimental development project.

## Roadmap

Near-term work:

- continue implementing the source Ironman route beyond Sea Slug;
- validate every new route block through real gameplay;
- expand exact resource calculations;
- improve prerequisite and inventory preparation;
- add more NPC/object/item targets;
- improve travel and navigation handling;
- improve Quest Helper hand-offs;
- add screenshots and gameplay examples;
- prepare Plugin Hub packaging and release documentation.

Longer-term goals:

- complete the full early-game route;
- continue into mid-game progression;
- continue into late-game progression;
- account milestone tracking;
- stronger route validation;
- broader state detection;
- polished public release.

## Project philosophy

This project is intentionally more than a checklist.

The intended experience is:

```text
Ironman progression route
        +
RuneLite in-game guidance
        +
Quest Helper
        +
Shortest Path
        +
bank/inventory awareness
        +
automatic progress tracking
        +
dynamic resource requirements
```

The end goal is one cohesive guide from a fresh Ironman account onward.

## Contributing / testing

The project is still changing quickly, so contribution guidelines are not finalized yet.

The most useful reports during development are:

- incorrect route instructions;
- incorrect item quantities;
- wrong NPC/object/location targets;
- steps completing too early or too late;
- steps that fail to auto-complete;
- navigation problems;
- bank-filter problems;
- Quest Helper integration problems;
- differences between the source guide and current OSRS behavior.

When reporting an issue, include the guide step title/ID and what happened in-game if possible.

## Credits

Progression route reference:

- **OSRS Ironman Guide** — https://ironman.guide/guide/early-game

Major RuneLite integrations / projects used by Ironman Guide:

- **RuneLite** — https://runelite.net/
- **Quest Helper** by Zoinkwiz and contributors — https://github.com/Zoinkwiz/quest-helper
- **Shortest Path** — https://runelite.net/plugin-hub/show/shortest-path
- **Bank Tags** — built into RuneLite

Ironman Guide is an independent implementation and is not an official extension of any of the projects above.

## Disclaimer

Ironman Guide is an independent community project.

It is not affiliated with, endorsed by, or sponsored by Jagex Ltd., Old School RuneScape, RuneLite, the OSRS Ironman Guide website, Quest Helper, or Shortest Path.

Old School RuneScape and RuneScape are trademarks of Jagex Ltd.

---

<p align="center">
  <strong>Status: Active development — 64 route steps implemented</strong>
</p>
