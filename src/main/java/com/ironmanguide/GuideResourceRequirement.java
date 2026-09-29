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

private final int outputItemId;
private final int targetOutput;
private final int outputPerInput;

private GuideResourceRequirement(
int itemId,
int quantity,
Skill skill,
int targetLevel,
double xpPerItem,
int outputItemId,
int targetOutput,
int outputPerInput)
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

this.outputItemId = outputItemId;
this.targetOutput = targetOutput;
this.outputPerInput = outputPerInput;
}

/**
 * Fixed exact quantity.
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
0,
-1,
0,
0
);
}

/**
 * Exact quantity from a fixed output ratio.
 *
 * This represents the full requirement from zero progress.
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
 * Exact REMAINING resource requirement.
 *
 * Example:
 * Goal = 1000 Arrow shafts
 * Each log = 15 Arrow shafts
 *
 * If player already owns 400 shafts:
 * 600 remain / 15 = 40 Logs still required.
 *
 * Existing output in inventory and known bank state
 * is counted automatically.
 */
public static GuideResourceRequirement fromRemainingOutput(
int inputItemId,
int outputItemId,
int targetOutput,
int outputPerInput)
{
if (outputItemId <= 0)
{
throw new IllegalArgumentException(
"outputItemId must be positive"
);
}

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

return new GuideResourceRequirement(
inputItemId,
0,
null,
0,
0,
outputItemId,
targetOutput,
outputPerInput
);
}

/**
 * Exact quantity calculated from current skill XP.
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
xpPerItem,
-1,
0,
0
);
}

/**
 * Resolves how many input resources are still required RIGHT NOW.
 */
public int resolveQuantity(
Client client,
IronmanGuideItemChecker itemChecker)
{
if (outputItemId > 0)
{
if (itemChecker == null)
{
throw new IllegalArgumentException(
"itemChecker is required for output-based resources"
);
}

int currentOutput =
itemChecker.getInventoryQuantity(outputItemId)
+ itemChecker.getBankQuantity(outputItemId);

int remainingOutput =
Math.max(
0,
targetOutput - currentOutput
);

if (remainingOutput == 0)
{
return 0;
}

return (
remainingOutput
+ outputPerInput
- 1
) / outputPerInput;
}

if (skill != null)
{
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

return quantity;
}

/**
 * Whether this resource requirement represents an input item
 * consumed to create a target output.
 *
 * Example:
 * Logs -> Arrow shafts.
 */
public boolean isOutputBased()
{
return outputItemId > 0;
}

/**
 * How many required input items the player currently owns
 * across inventory and known bank state.
 */
public int resolveOwnedInputQuantity(
IronmanGuideItemChecker itemChecker)
{
if (!isOutputBased())
{
throw new IllegalStateException(
"Owned input quantity is only available for output-based resources"
);
}

if (itemChecker == null)
{
throw new IllegalArgumentException(
"itemChecker is required for output-based resources"
);
}

return itemChecker.getOwnedQuantity(
itemId
);
}

/**
 * How many more input items still need to be collected.
 *
 * Example:
 *
 * 16 Logs still required for the remaining Arrow shafts.
 * Player already owns 13 Logs.
 *
 * Missing = 3 Logs.
 */
public int resolveMissingInputQuantity(
Client client,
IronmanGuideItemChecker itemChecker)
{
if (!isOutputBased())
{
throw new IllegalStateException(
"Missing input quantity is only available for output-based resources"
);
}

int requiredQuantity =
resolveQuantity(
client,
itemChecker
);

int ownedQuantity =
resolveOwnedInputQuantity(
itemChecker
);

return Math.max(
0,
requiredQuantity - ownedQuantity
);
}

/**
 * Pure skill calculation for tests.
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

public int getQuantity()
{
if (skill != null || outputItemId > 0)
{
throw new IllegalStateException(
"Dynamic resources must be resolved from live player state"
);
}

return quantity;
}
}