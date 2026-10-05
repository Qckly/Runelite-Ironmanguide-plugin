package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;

public final class StartingOffGuideData
{
private static final int EMPTY_JUG = 1935;
private static final int ASHES = 592;

private StartingOffGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
GuideStepType.TEXT,
"Sell starting equipment",
"Sell the bronze dagger, bronze sword, bronze axe, wooden shield and shortbow to the Lumbridge General Store.",

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
)
.withId(
"early_001_sell_starting_equipment"
)
.withSection(
"Starting off"
)
.withTargets(
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
)
.withId(
"early_002_buy_spade"
)
.withSection(
"Starting off"
)
.withTargets(
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
).quest(
"X Marks the Spot"
)
)
.withId(
"early_003_start_x_marks_the_spot"
)
.withSection(
"Starting off"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3228, 3242, 0),
NpcID.VEOS_VISIBLE
).areaRadius(4),

GuideTarget.dialogue(
"I'm looking for a quest.",
"Sounds good, what should I do?",
"Can I help?",
"Yes."
)
),

new GuideStep(
GuideStepType.TEXT,
"Drop starter runes",
"Drop your Air runes and Mind runes before claiming replacement runes from the Magic tutor.",

GuideRule.action(GuideRuleType.DROP)
.item(ItemID.AIRRUNE, 1),

GuideRule.action(GuideRuleType.DROP)
.item(ItemID.MINDRUNE, 1)
)
.withId(
"early_004_drop_starter_runes"
)
.withSection(
"Starting off"
),

new GuideStep(
GuideStepType.TEXT,
"Claim replacement runes",
"Right-click the Magic combat tutor and choose Claim to receive replacement Air and Mind runes.",

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.AIRRUNE, 30),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.MINDRUNE, 30)
)
.withId(
"early_005_claim_replacement_runes"
)
.withSection(
"Starting off"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3216, 3237, 0),
3218
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
"Pick up dropped runes",
"Pick up the Air runes and Mind runes you dropped.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.AIRRUNE, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.MINDRUNE, 1)
)
.withId(
"early_006_pick_up_dropped_runes"
)
.withSection(
"Starting off"
)
.withTargets(
GuideTarget.groundItem(
null,
ItemID.AIRRUNE,
ItemID.MINDRUNE
)
)

};
}
}