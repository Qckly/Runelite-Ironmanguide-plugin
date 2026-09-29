package com.ironmanguide;

import net.runelite.api.Client;
import net.runelite.api.ItemContainer;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.InventoryID;

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

public boolean isStepComplete(
GuideStep step)
{
if (step == null)
{
return false;
}

GuideRule[] rules =
step.getRules();

if (rules == null
|| rules.length == 0)
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

public boolean isRuleComplete(
GuideRule rule)
{
if (rule == null)
{
return false;
}

switch (rule.getType())
{
case SELL:
return confirmedItemAction(
GuideRuleType.SELL,
rule
);

case BUY:
return confirmedItemAction(
GuideRuleType.BUY,
rule
);

case DROP:
return confirmedItemAction(
GuideRuleType.DROP,
rule
);

case PICKUP:
return confirmedItemAction(
GuideRuleType.PICKUP,
rule
);

case ITEM_ON_ITEM:
return rule.getItemId() > 0
&& rule.getSecondaryItemId() > 0
&& stateTracker.isItemPairConfirmed(
rule.getItemId(),
rule.getSecondaryItemId()
);

case BANK_DEPOSIT:
return confirmedItemAction(
GuideRuleType.BANK_DEPOSIT,
rule
);

case BANK_WITHDRAW:
return confirmedItemAction(
GuideRuleType.BANK_WITHDRAW,
rule
);

case NPC_INTERACT:
return rule.getNpcId() > 0
&& stateTracker.getConfirmedQuantity(
GuideRuleType.NPC_INTERACT,
rule.getNpcId()
) >= 1;

case OBJECT_INTERACT:
return rule.getObjectId() > 0
&& stateTracker.getConfirmedQuantity(
GuideRuleType.OBJECT_INTERACT,
rule.getObjectId()
) >= 1;

case DIALOGUE_OPTION:
return rule.getText() != null
&& stateTracker.isTextConfirmed(
GuideRuleType.DIALOGUE_OPTION,
rule.getText()
);

case HAVE_ITEM:
return rule.isBankIncluded()
? itemChecker.hasOwnedQuantity(
rule.getItemId(),
rule.getQuantity()
)
: itemChecker.hasRequiredQuantity(
rule.getItemId(),
rule.getQuantity()
);

case INVENTORY_EMPTY:
return itemChecker.isInventoryEmpty();

case EQUIP:
return hasEquipped(
rule.getItemId(),
rule.getQuantity()
);

case SKILL_LEVEL:
return rule.getSkill() != null
&& client.getRealSkillLevel(
rule.getSkill()
) >= rule.getValue();

case QUEST_STARTED:
return questStateChecker.isStarted(
rule.getQuestName()
);

case QUEST_FINISHED:
return questStateChecker.isFinished(
rule.getQuestName()
);

case VARP_AT_LEAST:
return rule.getVarpId() >= 0
&& client.getVarpValue(
rule.getVarpId()
) >= rule.getValue();

case VARBIT_AT_LEAST:
return rule.getVarbitId() >= 0
&& client.getVarbitValue(
rule.getVarbitId()
) >= rule.getValue();

case LOCATION:
return isAtLocation(rule);

default:
return false;
}
}

private boolean confirmedItemAction(
GuideRuleType type,
GuideRule rule)
{
return rule.getItemId() > 0
&& stateTracker.getConfirmedQuantity(
type,
rule.getItemId()
) >= rule.getQuantity();
}

private boolean hasEquipped(
int itemId,
int quantity)
{
if (itemId <= 0)
{
return false;
}

ItemContainer equipment =
client.getItemContainer(
InventoryID.WORN
);

return equipment != null
&& equipment.count(itemId)
>= quantity;
}

private boolean isAtLocation(
GuideRule rule)
{
Player player =
client.getLocalPlayer();

WorldPoint target =
rule.getWorldPoint();

if (player == null
|| target == null)
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