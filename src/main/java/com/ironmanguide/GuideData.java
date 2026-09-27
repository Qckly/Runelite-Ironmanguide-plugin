package com.ironmanguide;

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

new GuideStep(
GuideStepType.LOCATION,
"Tutorial Island",
"Continue through Tutorial Island."
)
};
}
}