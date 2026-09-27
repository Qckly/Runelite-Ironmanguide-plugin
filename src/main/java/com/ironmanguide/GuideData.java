package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;

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
)
};
}
}