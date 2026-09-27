package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.Skill;

/**
 * A deterministic resource requirement for a guide step.
 *
 * Only use this when the required quantity can be calculated exactly.
 * Do not use it for RNG or variable activities.
 */
public final class GuideResourceRequirement
{
private final int itemId;
private final int quantity;

private final Skill skill;
private final int targetLevel;
private final double xpPerItem;

private GuideResourceRequirement(
int itemId,
int quantity,
Skill skill,
int targetLevel,
double xpPerItem)
{
if (itemId <= 0)
{
throw new IllegalArgumentException(
"itemId must be positive"
);
}

this.itemId = itemId;
this.quantity = quantity;
this.skill = skill;
this.targetLevel = targetLevel;
this.xpPerItem = xpPerItem;
}

/**
 * Fixed exact quantity.
 *
 * Example:
 * exactly 7 logs are required.
 */
public static GuideResourceRequirement exact(
int itemId,
int quantity)
{
if (quantity <= 0)
{
throw new IllegalArgumentException(
"quantity must be positive"
);
}

return new GuideResourceRequirement(
itemId,
quantity,
null,
0,
0
);
}

/**
 * Exact quantity calculated from fixed output per input.
 *
 * Example:
 * 1000 arrow shafts / 15 shafts per log = 67 logs.
 */
public static GuideResourceRequirement fromOutput(
int itemId,
int targetOutput,
int outputPerInput)
{
if (targetOutput <= 0)
{
throw new IllegalArgumentException(
"targetOutput must be positive"
);
}

if (outputPerInput <= 0)
{
throw new IllegalArgumentException(
"outputPerInput must be positive"
);
}

int requiredInput =
(targetOutput + outputPerInput - 1)
/ outputPerInput;

return exact(
itemId,
requiredInput
);
}

/**
 * Exact quantity calculated from the player's current skill XP.
 *
 * Example:
 * current Firemaking XP -> level 15 target
 * using normal logs at 40 XP each.
 */
public static GuideResourceRequirement forSkillLevel(
int itemId,
Skill skill,
int targetLevel,
double xpPerItem)
{
if (skill == null)
{
throw new IllegalArgumentException(
"skill is required"
);
}

if (targetLevel < 1
|| targetLevel > Experience.MAX_VIRT_LEVEL)
{
throw new IllegalArgumentException(
"targetLevel is invalid"
);
}

if (xpPerItem <= 0)
{
throw new IllegalArgumentException(
"xpPerItem must be positive"
);
}

return new GuideResourceRequirement(
itemId,
0,
skill,
targetLevel,
xpPerItem
);
}

/**
 * Resolves the exact quantity needed right now.
 */
public int resolveQuantity(Client client)
{
if (skill == null)
{
return quantity;
}

if (client == null)
{
throw new IllegalArgumentException(
"client is required for skill-based resources"
);
}

int currentXp =
Math.max(
0,
client.getSkillExperience(skill)
);

int targetXp =
Experience.getXpForLevel(targetLevel);

return calculateRequiredActions(
currentXp,
targetXp,
xpPerItem
);
}

/**
 * Pure calculation kept separate so it can be tested.
 */
static int calculateRequiredActions(
int currentXp,
int targetXp,
double xpPerItem)
{
if (currentXp < 0)
{
throw new IllegalArgumentException(
"currentXp must not be negative"
);
}

if (targetXp < 0)
{
throw new IllegalArgumentException(
"targetXp must not be negative"
);
}

if (xpPerItem <= 0)
{
throw new IllegalArgumentException(
"xpPerItem must be positive"
);
}

int remainingXp =
Math.max(
0,
targetXp - currentXp
);

if (remainingXp == 0)
{
return 0;
}

return (int) Math.ceil(
remainingXp / xpPerItem
);
}

public int getItemId()
{
return itemId;
}

/**
 * Only for fixed requirements.
 * Dynamic skill requirements must use resolveQuantity(client).
 */
public int getQuantity()
{
if (skill != null)
{
throw new IllegalStateException(
"Skill-based resources must be resolved with the client"
);
}

return quantity;
}
}