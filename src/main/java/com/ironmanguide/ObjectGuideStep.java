package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class ObjectGuideStep extends GuideStep
{
private final int objectId;
private final WorldPoint worldPoint;

public ObjectGuideStep(
String title,
String description,
int objectId,
WorldPoint worldPoint)
{
super(GuideStepType.OBJECT, title, description);
this.objectId = objectId;
this.worldPoint = worldPoint;
}

public int getObjectId()
{
return objectId;
}

public WorldPoint getWorldPoint()
{
return worldPoint;
}
}