package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

public class GuideTarget
{
private final GuideTargetType type;

private int[] ids = new int[0];
private String[] texts = new String[0];

private WorldPoint worldPoint;
private int radius = 0;

private GuideTarget(GuideTargetType type)
{
this.type = type;
}

public static GuideTarget npc(
WorldPoint worldPoint,
int... npcIds)
{
GuideTarget target =
new GuideTarget(GuideTargetType.NPC);

target.worldPoint = worldPoint;
target.ids = npcIds;

return target;
}

public static GuideTarget object(
WorldPoint worldPoint,
int... objectIds)
{
GuideTarget target =
new GuideTarget(GuideTargetType.OBJECT);

target.worldPoint = worldPoint;
target.ids = objectIds;

return target;
}

public static GuideTarget location(
WorldPoint worldPoint,
int radius)
{
GuideTarget target =
new GuideTarget(GuideTargetType.LOCATION);

target.worldPoint = worldPoint;
target.radius = radius;

return target;
}

public static GuideTarget widget(int widgetId)
{
GuideTarget target =
new GuideTarget(GuideTargetType.WIDGET);

target.ids = new int[] { widgetId };

return target;
}

public static GuideTarget dialogue(
String... options)
{
GuideTarget target =
new GuideTarget(
GuideTargetType.DIALOGUE
);

target.texts =
options != null
? options
: new String[0];

return target;
}

public GuideTarget areaRadius(int radius)
{
this.radius = Math.max(0, radius);
return this;
}

public GuideTargetType getType()
{
return type;
}

public int[] getIds()
{
return ids;
}

public String[] getTexts()
{
return texts;
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