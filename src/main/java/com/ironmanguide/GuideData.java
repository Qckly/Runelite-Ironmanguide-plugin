package com.ironmanguide;

public final class GuideData
{
private GuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {
new GuideStep("Welcome", "Welcome to the Ironman Guide."),
new GuideStep("Gielinor Guide", "Talk to the Gielinor Guide."),
new GuideStep("Tutorial Island", "Continue through Tutorial Island.")
};
}
}