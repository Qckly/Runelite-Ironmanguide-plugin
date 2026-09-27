package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

public class GuideRuleEvaluator
{
private final Client client;
private final IronmanGuideItemChecker itemChecker;
private final GuideStateTracker stateTracker;
private final IronmanGuideQuestStateChecker questStateChecker;

public GuideRuleEvaluator(
Client client,
IronmanGuideItemChecker itemChecker,
GuideStateTracker stateTracker,
IronmanGuideQuestStateChecker questStateChecker)
{
this.client = client;
this.itemChecker = itemChecker;
this.stateTracker = stateTracker;
this.questStateChecker = questStateChecker;
}

public boolean isStepComplete(GuideStep step)
{
GuideRule[] rules = step.getRules();

if (rules == null || rules.length == 0)
{
return false;
}

for (GuideRule rule : rules)
{
if (!isRuleComplete(rule))
{
return false;
}
}

return true;
}

public boolean isRuleComplete(GuideRule rule)
{
switch (rule.getType())
{
case SELL:
return stateTracker.getConfirmedQuantity(
GuideRuleType.SELL,
rule.getItemId()
) >= rule.getQuantity();

case BUY:
return stateTracker.getConfirmedQuantity(
GuideRuleType.BUY,
rule.getItemId()
) >= rule.getQuantity();

case DROP:
return stateTracker.getConfirmedQuantity(
GuideRuleType.DROP,
rule.getItemId()
) >= rule.getQuantity();

case PICKUP:
return stateTracker.getConfirmedQuantity(
GuideRuleType.PICKUP,
rule.getItemId()
) >= rule.getQuantity();

case HAVE_ITEM:
return itemChecker.hasRequiredQuantity(
rule.getItemId(),
rule.getQuantity()
);

case SKILL_LEVEL:
return rule.getSkill() != null
&& client.getRealSkillLevel(
rule.getSkill()
) >= rule.getValue();

case INVENTORY_EMPTY:
return itemChecker.isInventoryEmpty();

case QUEST_STARTED:
return questStateChecker.isStarted(
rule.getQuestName()
);

case QUEST_FINISHED:
return questStateChecker.isFinished(
rule.getQuestName()
);

case LOCATION:
return isAtLocation(rule);

default:
return false;
}
}

private boolean isAtLocation(GuideRule rule)
{
Player player = client.getLocalPlayer();
WorldPoint target = rule.getWorldPoint();

if (player == null || target == null)
{
return false;
}

if (player.getWorldLocation().getPlane()
!= target.getPlane())
{
return false;
}

return player.getWorldLocation()
.distanceTo2D(target)
<= rule.getRadius();
}
}