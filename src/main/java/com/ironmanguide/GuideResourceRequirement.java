package com.ironmanguide;

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

private GuideResourceRequirement(
int itemId,
int quantity)
{
if (itemId <= 0)
{
throw new IllegalArgumentException(
"itemId must be positive"
);
}

if (quantity <= 0)
{
throw new IllegalArgumentException(
"quantity must be positive"
);
}

this.itemId = itemId;
this.quantity = quantity;
}

public static GuideResourceRequirement exact(
int itemId,
int quantity)
{
return new GuideResourceRequirement(
itemId,
quantity
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

public int getItemId()
{
return itemId;
}

public int getQuantity()
{
return quantity;
}
}
