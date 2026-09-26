package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class NpcGuideStep extends GuideStep
{
private final int npcId;
private final WorldPoint worldPoint;

public NpcGuideStep(
String title,
String description,
int npcId,
WorldPoint worldPoint)
{
super(GuideStepType.NPC, title, description);
this.npcId = npcId;
this.worldPoint = worldPoint;
}

public int getNpcId()
{
return npcId;
}

public WorldPoint getWorldPoint()
{
return worldPoint;
}
}