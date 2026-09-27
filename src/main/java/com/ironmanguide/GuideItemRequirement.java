package com.ironmanguide;

public class GuideItemRequirement
{
private final int itemId;
private final int quantity;

public GuideItemRequirement(
int itemId,
int quantity)
{
this.itemId = itemId;
this.quantity = quantity;
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