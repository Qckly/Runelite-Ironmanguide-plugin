package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class QuestGuideStep extends GuideStep
{
private final String questName;
private final QuestRouteType routeType;
private final QuestProgressRequirement progressRequirement;
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
this(
title,
description,
title,
QuestRouteType.FULL,
null,
npcId,
worldPoint,
itemRequirements,
dialogueOptions
);
}

public QuestGuideStep(
String title,
String description,
String questName,
QuestRouteType routeType,
int npcId,
WorldPoint worldPoint,
GuideItemRequirement[] itemRequirements,
String... dialogueOptions)
{
this(
title,
description,
questName,
routeType,
null,
npcId,
worldPoint,
itemRequirements,
dialogueOptions
);
}

public QuestGuideStep(
String title,
String description,
String questName,
QuestRouteType routeType,
QuestProgressRequirement progressRequirement,
int npcId,
WorldPoint worldPoint,
GuideItemRequirement[] itemRequirements,
String... dialogueOptions)
{
super(GuideStepType.QUEST, title, description);

this.questName = questName;
this.routeType = routeType;
this.progressRequirement = progressRequirement;
this.npcId = npcId;
this.worldPoint = worldPoint;
this.itemRequirements = itemRequirements;
this.dialogueOptions = dialogueOptions;
}

public String getQuestName()
{
return questName;
}

public QuestRouteType getRouteType()
{
return routeType;
}

public QuestProgressRequirement getProgressRequirement()
{
return progressRequirement;
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