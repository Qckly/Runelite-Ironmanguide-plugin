package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class QuestGuideStep extends GuideStep
{
private final int npcId;
private final WorldPoint worldPoint;
private final GuideItemRequirement[] itemRequirements;
private final String[] dialogueOptions;

public QuestGuideStep(
String title,
String description,
int npcId,
WorldPoint worldPoint,
GuideItemRequirement[] itemRequirements,
String... dialogueOptions)
{
super(GuideStepType.QUEST, title, description);

this.npcId = npcId;
this.worldPoint = worldPoint;
this.itemRequirements = itemRequirements;
this.dialogueOptions = dialogueOptions;
}

public int getNpcId()
{
return npcId;
}

public WorldPoint getWorldPoint()
{
return worldPoint;
}

public GuideItemRequirement[] getItemRequirements()
{
return itemRequirements;
}

public String[] getDialogueOptions()
{
return dialogueOptions;
}
}