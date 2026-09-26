package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class LocationGuideStep extends GuideStep
{
private final WorldPoint worldPoint;

public LocationGuideStep(
String title,
String description,
WorldPoint worldPoint)
{
super(GuideStepType.LOCATION, title, description);
this.worldPoint = worldPoint;
}

public WorldPoint getWorldPoint()
{
return worldPoint;
}
}