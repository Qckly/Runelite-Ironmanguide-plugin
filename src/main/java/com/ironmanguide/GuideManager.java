package com.ironmanguide;

public class GuideManager
{
private final GuideStep[] steps;
private int currentStep = 0;

public GuideManager(GuideStep[] steps)
{
this.steps = steps;
}

public GuideStep getCurrentStep()
{
return steps[currentStep];
}

public int getCurrentStepIndex()
{
return currentStep;
}

public int getTotalSteps()
{
return steps.length;
}

public void next()
{
if (currentStep < steps.length - 1)
{
currentStep++;
}
}

public void previous()
{
if (currentStep > 0)
{
currentStep--;
}
}
}