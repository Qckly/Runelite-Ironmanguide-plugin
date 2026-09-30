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
if (rule.isGuidanceOnly())
{
continue;
}

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

case ALL_OWNED_IN_INVENTORY:
return itemChecker.getInventoryQuantity(
rule.getItemId()
) > 0
&& itemChecker.getBankQuantity(
rule.getItemId()
) == 0;

case BANK_HAS_ITEM:
return itemChecker.hasBankRequiredQuantity(
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

/**
 * Returns current numeric progress for item-based guide rules.
 *
 * Action rules such as PICKUP / BUY / SELL / DEPOSIT / WITHDRAW
 * use confirmed progress from the current guide step.
 *
 * HAVE_ITEM uses live inventory or inventory + bank ownership.
 */
public int getRuleProgressQuantity(
GuideRule rule)
{
if (rule == null)
{
return 0;
}

switch (rule.getType())
{
case SELL:
case BUY:
case DROP:
case PICKUP:
case BANK_DEPOSIT:
case BANK_WITHDRAW:
return stateTracker.getConfirmedQuantity(
rule.getType(),
rule.getItemId()
);

case HAVE_ITEM:
return rule.isBankIncluded()
? itemChecker.getOwnedQuantity(
rule.getItemId()
)
: itemChecker.getInventoryQuantity(
rule.getItemId()
);

case BANK_HAS_ITEM:
return itemChecker.getBankQuantity(
rule.getItemId()
);

case EQUIP:
return getEquippedQuantity(
rule.getItemId()
);

default:
return isRuleComplete(rule)
? Math.max(
1,
rule.getQuantity()
)
: 0;
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
return getEquippedQuantity(
itemId
) >= quantity;
}

private int getEquippedQuantity(
int itemId)
{
if (itemId <= 0)
{
return 0;
}

ItemContainer equipment =
client.getItemContainer(
InventoryID.WORN
);

if (equipment == null)
{
return 0;
}

return equipment.count(
itemId
);
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