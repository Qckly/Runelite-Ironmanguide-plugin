package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.Experience;
import net.runelite.api.Skill;

/**
 * A deterministic resource requirement for a guide step.
 *
 * Only use this class when the required quantity can be calculated exactly
 * from the guide method. Variable activities should not create one.
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
if (skill != null)
{
throw new IllegalStateException(
"Skill-based resources must be resolved with the client"
);
}

return quantity;
}
}
