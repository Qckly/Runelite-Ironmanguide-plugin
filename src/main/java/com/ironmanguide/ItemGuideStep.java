package com.ironmanguide;

public class ItemGuideStep extends GuideStep
{
private final int itemId;
private final int quantity;

public ItemGuideStep(
String title,
String description,
int itemId,
int quantity)
{
super(GuideStepType.ITEM, title, description);
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