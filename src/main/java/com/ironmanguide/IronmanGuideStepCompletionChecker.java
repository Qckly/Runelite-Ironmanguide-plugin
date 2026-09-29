package com.ironmanguide;

public class IronmanGuideStepCompletionChecker
{
private final IronmanGuideItemChecker itemChecker;

public IronmanGuideStepCompletionChecker(
IronmanGuideItemChecker itemChecker)
{
this.itemChecker = itemChecker;
}

public boolean isComplete(
GuideStep step)
{
if (step instanceof QuestGuideStep)
{
QuestGuideStep questStep =
(QuestGuideStep) step;

GuideItemRequirement[] requirements =
questStep.getItemRequirements();

if (requirements == null
|| requirements.length == 0)
{
return true;
}

for (GuideItemRequirement requirement :
requirements)
{
if (!itemChecker.hasRequiredQuantity(
requirement.getItemId(),
requirement.getQuantity()
))
{
return false;
}
}

return true;
}

return false;
}
}