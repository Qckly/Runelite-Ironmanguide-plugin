package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;

public final class EarlyGameGuideData
{
private EarlyGameGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {
new GuideStep(
GuideStepType.TEXT,
"Sell starting equipment",
"Sell the starting bronze dagger, bronze sword, bronze axe, wooden shield and shortbow to the Lumbridge General Store.",

GuideRule.action(GuideRuleType.SELL)
.item(ItemID.BRONZE_DAGGER, 1),

GuideRule.action(GuideRuleType.SELL)
.item(ItemID.BRONZE_SWORD, 1),

GuideRule.action(GuideRuleType.SELL)
.item(ItemID.BRONZE_AXE, 1),

GuideRule.action(GuideRuleType.SELL)
.item(ItemID.WOODEN_SHIELD, 1),

GuideRule.action(GuideRuleType.SELL)
.item(ItemID.SHORTBOW, 1)
).withTargets(
GuideTarget.npc(
new WorldPoint(3212, 3246, 0),
NpcID.GENERALSHOPKEEPER1,
NpcID.GENERALASSISTANT1
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
"Buy a spade",
"Buy 1 spade from the Lumbridge General Store.",

GuideRule.action(GuideRuleType.BUY)
.item(ItemID.SPADE, 1)
).withTargets(
GuideTarget.npc(
new WorldPoint(3212, 3246, 0),
NpcID.GENERALSHOPKEEPER1,
NpcID.GENERALASSISTANT1
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
"Start X Marks the Spot",
"Talk to Veos in The Sheared Ram and start X Marks the Spot. Do not continue the whole quest yet.",

GuideRule.action(
GuideRuleType.QUEST_STARTED
).quest("X Marks the Spot")
).withTargets(
GuideTarget.npc(
new WorldPoint(3228, 3242, 0),
NpcID.VEOS_VISIBLE
).areaRadius(4),

GuideTarget.dialogue(
"I'm looking for a quest.",
"Sounds good, what should I do?"
)
)
};
}
}