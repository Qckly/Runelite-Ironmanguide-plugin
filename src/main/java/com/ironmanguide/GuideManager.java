package com.ironmanguide;

public class GuideManager
{
private final GuideStep[] steps;
private int currentStep;

public GuideManager(GuideStep[] steps, int savedStep)
{
this.steps = steps;
this.currentStep =
Math.max(0, Math.min(savedStep, steps.length - 1));
}

public GuideStep getCurrentStep()
{
return steps[currentStep];
}

public GuideStep getStep(int index)
{
return steps[index];
}

public boolean isStepCompleted(int index)
{
return index < currentStep;
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