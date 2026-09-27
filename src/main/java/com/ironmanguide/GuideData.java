package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;

public final class GuideData
{
private GuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {
new GuideStep(
GuideStepType.TEXT,
"Welcome",
"Welcome to the Ironman Guide."
),

new NpcGuideStep(
"NPC Highlight Test",
"Talk to the highlighted cow.",
2790,
null
),

new LocationGuideStep(
"Location Highlight Test",
"Walk to the highlighted tile.",
new WorldPoint(3222, 3218, 0)
),

new ItemGuideStep(
"Item Requirement Test",
"Have a bronze axe in your inventory.",
ItemID.BRONZE_AXE,
1
),

new DialogueGuideStep(
"Dialogue Highlight Test",
"Talk to Father Aereck and choose the highlighted answer.",
NpcID.FATHER_AERECK,
new WorldPoint(3243, 3206, 0),
"I'm looking for a quest!"
)
};
}
}