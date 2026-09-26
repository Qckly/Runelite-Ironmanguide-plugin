package com.ironmanguide;

public final class GuideData
{
private GuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {
new GuideStep(GuideStepType.TEXT, "Welcome", "Welcome to the Ironman Guide."),
new GuideStep(GuideStepType.NPC, "Gielinor Guide", "Talk to the Gielinor Guide."),
new GuideStep(GuideStepType.LOCATION, "Tutorial Island", "Continue through Tutorial Island.")
};
}
}