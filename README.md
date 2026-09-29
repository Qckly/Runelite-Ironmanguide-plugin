<p align="center">
  <img src="https://raw.githubusercontent.com/Qckly/Runelite-Ironmanguide-plugin/development/src/main/resources/com/ironmanguide/icon.png" width="128" alt="Ironman Guide logo">
</p>

<h1 align="center">Ironman Guide</h1>

<p align="center">
  A step-by-step RuneLite progression guide for Old School RuneScape Ironman accounts.
</p>

<p align="center">
  <strong>One continuous Ironman route, guided in-game like a giant Quest Helper.</strong>
</p>

> **Development status:** active development is currently happening on the [`development`](../../tree/development) branch.  
> The latest tested route contains **55 ordered guide steps**.

---

## About

**Ironman Guide** is a RuneLite plugin built to turn an Ironman progression route into one continuous guided experience.

Instead of constantly switching between a browser guide, the OSRS Wiki, notes, and RuneLite, the plugin aims to keep the route inside the client and guide the player step by step.

The long-term goal is simple:

> Create a fresh Ironman, enable Ironman Guide, and follow the route from early game through late game without already needing to know what comes next.

## Current progress

The latest development route currently contains **55 ordered guide steps**.

Implemented sections:

- Starting off
- Lumbridge setup
- Training and preparation
- Leaving Lumbridge
- Draynor and X Marks
- Falador and Ferox
- Yanille and Port Khazard

Current progression includes early account setup, early skilling preparation, X Marks the Spot, the start of The Knight's Sword, Ferox Enclave, Castle Wars, Yanille, Port Khazard, and Monk's Friend.

## Features

| Feature | Status |
| --- | :---: |
| Continuous step-by-step Ironman route | ✅ |
| Stable persistent step IDs | ✅ |
| Automatic step completion | ✅ |
| NPC highlighting | ✅ |
| Object highlighting | ✅ |
| Ground-item highlighting | ✅ |
| Location guidance | ✅ |
| Inventory item highlighting | ✅ |
| Bank-aware item requirements | ✅ |
| Dialogue guidance | ✅ |
| Quest-state detection | ✅ |
| Quest Helper integration | ✅ |
| Production / Make-X highlighting | ✅ |
| Dynamic exact resource requirements | ✅ |
| Full early-game route | 🚧 |
| Mid-game route | Planned |
| Late-game route | Planned |

## Dynamic resource tracking

When a requirement can be calculated exactly, Ironman Guide can show the player how many resources are still needed instead of displaying a static instruction.

For example, when making 1,000 Arrow shafts:

```text
You will need 67 Logs.
```

As progress changes, the remaining requirement can update as well.

The same system is designed to be reused for future skilling goals where exact resource requirements can be calculated.

## Inventory and bank awareness

Some guide steps require an item to be physically in the inventory, while others only require the player to own the item across inventory and bank.

Ironman Guide keeps those concepts separate so that:

- withdrawal steps do not complete while the required item is still banked;
- long-term resource goals can count banked items;
- hidden completion requirements can track progress without cluttering the player-facing guide.

## Quest Helper integration

Ironman Guide is designed to work **with** RuneLite Quest Helper rather than replace it.

The progression route decides **when** a quest should be done. When appropriate, Ironman Guide can associate the current step with the matching Quest Helper quest while continuing to track the wider Ironman route.

## Visual guidance

Depending on the step, the plugin can guide the player using:

- NPC targets
- object targets
- ground items
- exact locations
- inventory and bank items
- dialogue choices
- production / Make-X interfaces

The goal is to reduce ambiguity: the player should know not only *what* to do, but also *where* and *with what* whenever the game state allows it.

## Guide architecture

The route is split internally into maintainable chapter files while still appearing to the player as one continuous guide.

Every step has a stable ID, for example:

```text
early_020_fletch_1000_arrow_shafts
early_041_finish_x_marks
early_055_complete_monks_friend
```

Saved progress therefore does not rely on a fragile numeric array position when guide data is reorganized.

Guide data is also validated for structural problems such as missing IDs, duplicate IDs, missing sections, missing titles, and null steps.

## Development

### Latest code

Use the [`development`](../../tree/development) branch for the current tested development state.

### Requirements

- JDK 17 for the local development environment
- Gradle wrapper included in the repository
- RuneLite development dependencies

### Run the development client

```powershell
.\gradlew run
```

### Build

```powershell
.\gradlew clean build
```

## Installation

Ironman Guide is **not yet a finished RuneLite Plugin Hub release**.

The repository is currently intended for development and testing. Public installation instructions will be added once the route and release packaging are ready.

## Roadmap

Near-term work:

- continue the early-game route beyond Monk's Friend;
- add more exact resource calculations;
- improve prerequisite and inventory preparation guidance;
- expand Quest Helper integrations;
- improve world and location guidance;
- validate each route block through real gameplay;
- add gameplay screenshots and GIFs;
- prepare for RuneLite Plugin Hub release requirements.

Longer-term goals:

- complete early-game progression;
- mid-game progression;
- late-game progression;
- milestone and prerequisite tracking;
- stronger route validation;
- polished public release documentation.

## Project philosophy

This project is intentionally more than a checklist.

The intended experience is:

```text
Ironman progression route
        +
RuneLite in-game guidance
        +
Quest Helper integration
        +
automatic progress tracking
        +
dynamic resource requirements
```

The end goal is one cohesive guide from a fresh Ironman account onward.

## Screenshots

Screenshots and gameplay examples will be added as the interface and route continue to mature.

## Contributing

The project is still changing quickly, so contribution guidelines are not finalized yet.

Bug reports, route mistakes, incorrect requirements, missed edge cases, and suggestions are especially useful while the guide is being validated through gameplay.

## Disclaimer

Ironman Guide is an independent community project.

It is not affiliated with, endorsed by, or sponsored by Jagex Ltd., Old School RuneScape, RuneLite, or the RuneLite Quest Helper project.

Old School RuneScape and RuneScape are trademarks of Jagex Ltd.

---

<p align="center">
  <strong>Status: Active development</strong>
</p>