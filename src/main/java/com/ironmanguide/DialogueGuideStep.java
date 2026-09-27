package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class DialogueGuideStep extends GuideStep
{
private final String[] options;
private final int npcId;
private final WorldPoint worldPoint;

public DialogueGuideStep(
String title,
String description,
String... options)
{
this(
title,
description,
-1,
null,
options
);
}

public DialogueGuideStep(
String title,
String description,
int npcId,
WorldPoint worldPoint,
String... options)
{
super(GuideStepType.DIALOGUE, title, description);

this.npcId = npcId;
this.worldPoint = worldPoint;
this.options = options;
}

public String[] getOptions()
{
return options;
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