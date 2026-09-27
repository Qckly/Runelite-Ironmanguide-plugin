package com.ironmanguide;

public final class GuideData
{
private GuideData()
{
}

public static GuideStep[] getSteps()
{
return EarlyGameGuideData.getSteps();
}
}