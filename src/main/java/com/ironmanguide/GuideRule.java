package com.ironmanguide;

import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;

public class GuideRule
{
private final GuideRuleType type;

private int itemId = -1;
private int secondaryItemId = -1;
private int quantity = 1;

private int npcId = -1;
private int objectId = -1;

private int varpId = -1;
private int varbitId = -1;

private String text;
private String questName;

private Skill skill;
private int value;

private WorldPoint worldPoint;
private int radius;

private GuideRule(GuideRuleType type)
{
this.type = type;
}

public static GuideRule action(GuideRuleType type)
{
return new GuideRule(type);
}

public GuideRule item(int itemId, int quantity)
{
this.itemId = itemId;
this.quantity = quantity;
return this;
}

public GuideRule itemOnItem(
int sourceItemId,
int targetItemId)
{
this.itemId = sourceItemId;
this.secondaryItemId = targetItemId;
return this;
}

public GuideRule npc(int npcId)
{
this.npcId = npcId;
return this;
}

public GuideRule object(int objectId)
{
this.objectId = objectId;
return this;
}

public GuideRule varp(
int varpId,
int value)
{
this.varpId = varpId;
this.value = value;
return this;
}

public GuideRule varbit(
int varbitId,
int value)
{
this.varbitId = varbitId;
this.value = value;
return this;
}

public GuideRule text(String text)
{
this.text = text;
return this;
}

public GuideRule quest(String questName)
{
this.questName = questName;
return this;
}

public GuideRule skill(Skill skill, int level)
{
this.skill = skill;
this.value = level;
return this;
}

public GuideRule value(int value)
{
this.value = value;
return this;
}

public GuideRule location(
WorldPoint worldPoint,
int radius)
{
this.worldPoint = worldPoint;
this.radius = radius;
return this;
}

public GuideRuleType getType()
{
return type;
}

public int getItemId()
{
return itemId;
}

public int getSecondaryItemId()
{
return secondaryItemId;
}

public int getQuantity()
{
return quantity;
}

public int getNpcId()
{
return npcId;
}

public int getObjectId()
{
return objectId;
}

public int getVarpId()
{
return varpId;
}

public int getVarbitId()
{
return varbitId;
}

public String getText()
{
return text;
}

public String getQuestName()
{
return questName;
}

public Skill getSkill()
{
return skill;
}

public int getValue()
{
return value;
}

public WorldPoint getWorldPoint()
{
return worldPoint;
}

public int getRadius()
{
return radius;
}
}