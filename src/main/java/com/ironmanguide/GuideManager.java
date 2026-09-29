package com.ironmanguide;

public class GuideManager
{
private final GuideStep[] steps;
private int currentStep;

public GuideManager(
GuideStep[] steps,
int savedStep)
{
this(
steps,
null,
savedStep
);
}

public GuideManager(
GuideStep[] steps,
String savedStepId,
int legacySavedStep)
{
this.steps = steps;

int savedIdIndex =
findStepIndex(savedStepId);

if (savedIdIndex >= 0)
{
this.currentStep =
savedIdIndex;

return;
}

this.currentStep =
Math.max(
0,
Math.min(
legacySavedStep,
steps.length - 1
)
);
}

public GuideStep getCurrentStep()
{
return steps[currentStep];
}

public GuideStep getStep(
int index)
{
return steps[index];
}

public boolean isStepCompleted(
int index)
{
return index < currentStep;
}

public int getCurrentStepIndex()
{
return currentStep;
}

public String getCurrentStepId()
{
GuideStep step =
getCurrentStep();

return step != null
? step.getId()
: null;
}

public int getTotalSteps()
{
return steps.length;
}

public void next()
{
if (currentStep
< steps.length - 1)
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

private int findStepIndex(
String stepId)
{
if (stepId == null
|| stepId.isBlank())
{
return -1;
}

for (
int i = 0;
i < steps.length;
i++
)
{
GuideStep step =
steps[i];

if (step != null
&& stepId.equals(
step.getId()
))
{
return i;
}
}

return -1;
}
}