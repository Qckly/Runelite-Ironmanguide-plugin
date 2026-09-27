package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;

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
)
.withId(
"early_001_sell_starting_equipment"
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
).quest("X Marks the Spot")
)
.withId(
"early_003_start_x_marks_the_spot"
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
"Drop your Air runes and Mind runes before claiming more from the Magic tutor.",

GuideRule.action(GuideRuleType.DROP)
.item(ItemID.AIRRUNE, 1),

GuideRule.action(GuideRuleType.DROP)
.item(ItemID.MINDRUNE, 1)
)
.withId(
"early_004_drop_starter_runes"
),

new GuideStep(
GuideStepType.TEXT,
"Claim replacement runes",
"Right-click the Magic combat tutor and choose Claim to receive 30 Air runes and 30 Mind runes.",

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.AIRRUNE, 30),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.MINDRUNE, 30)
)
.withId(
"early_005_claim_replacement_runes"
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
"Pick up the Air runes and Mind runes you dropped before claiming the replacement runes.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.AIRRUNE, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.MINDRUNE, 1)
)
.withId(
"early_006_pick_up_dropped_runes"
)
.withTargets(
GuideTarget.groundItem(
new WorldPoint(3216, 3237, 0),
ItemID.AIRRUNE,
ItemID.MINDRUNE
).areaRadius(8)
),

new GuideStep(
GuideStepType.TEXT,
"Collect Lumbridge kitchen supplies",
"Pick up the pot, jug, bowl and knife in the Lumbridge Castle kitchen.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.POT_EMPTY, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(1935, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BOWL_EMPTY, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.KNIFE, 1)
)
.withId(
"early_007_collect_kitchen_supplies"
)
.withTargets(
GuideTarget.groundItem(
new WorldPoint(3206, 3214, 0),
ItemID.POT_EMPTY,
1935,
ItemID.BOWL_EMPTY,
ItemID.KNIFE
).areaRadius(8)
),

new GuideStep(
GuideStepType.TEXT,
"Enter Lumbridge Castle basement",
"Open the trapdoor in the Lumbridge Castle kitchen and climb down into the basement.",

GuideRule.action(GuideRuleType.LOCATION)
.location(
new WorldPoint(3209, 9616, 0),
4
)
)
.withId(
"early_008_enter_lumbridge_basement"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3209, 3216, 0),
ObjectID.QIP_COOK_TRAPDOOR_OPEN
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
"Collect Lumbridge basement supplies",
"Pick up the bucket, cabbage, jug, knife and both pairs of leather boots in the Lumbridge Castle basement.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BUCKET_EMPTY, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.CABBAGE, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(1935, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.KNIFE, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.LEATHER_BOOTS, 2)
)
.withId(
"early_009_collect_basement_supplies"
)
.withTargets(
GuideTarget.groundItem(
new WorldPoint(3209, 9616, 0),
ItemID.BUCKET_EMPTY,
ItemID.CABBAGE,
1935,
ItemID.KNIFE,
ItemID.LEATHER_BOOTS
).areaRadius(12)
),

new GuideStep(
GuideStepType.TEXT,
"Return to Lumbridge Castle",
"Climb the ladder back up to the Lumbridge Castle kitchen.",

GuideRule.action(GuideRuleType.LOCATION)
.location(
new WorldPoint(3209, 3216, 0),
5
)
)
.withId(
"early_010_exit_lumbridge_basement"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3209, 9616, 0),
ObjectID.LADDER_FROM_CELLAR
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
"Fill a jug with water",
"Use one of your empty jugs on a water source at Lumbridge Castle.",

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.JUG_WATER, 1)
)
.withId(
"early_011_fill_jug_with_water"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3221, 3210, 0),
4
)
)
};
}
}
