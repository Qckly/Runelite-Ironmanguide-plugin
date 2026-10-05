package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;

public final class LumbridgeSetupGuideData
{
private static final int EMPTY_JUG = 1935;
private static final int ASHES = 592;

private LumbridgeSetupGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
"Collect kitchen supplies",
"Pick up the pot, jug, bowl and knife in the Lumbridge Castle kitchen.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.POT_EMPTY, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(EMPTY_JUG, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BOWL_EMPTY, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.KNIFE, 1)
)
.withId(
"early_007_collect_kitchen_supplies"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3213, 0),
7
),

GuideTarget.groundItem(
null,
ItemID.POT_EMPTY,
EMPTY_JUG,
ItemID.BOWL_EMPTY,
ItemID.KNIFE
)
),

new GuideStep(
"Enter the basement",
"Open the trapdoor in the Lumbridge Castle kitchen and climb down.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3209, 9616, 0),
4
)
)
.withId(
"early_008_enter_lumbridge_basement"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3209, 3216, 0),
ObjectID.QIP_COOK_TRAPDOOR_OPEN
).areaRadius(4)
),

new GuideStep(
"Collect basement supplies",
"Pick up the bucket, cabbage, jug, knife and both pairs of leather boots in the basement.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BUCKET_EMPTY, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.CABBAGE, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(EMPTY_JUG, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.KNIFE, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.LEATHER_BOOTS, 2)
)
.withId(
"early_009_collect_basement_supplies"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.groundItem(
null,
ItemID.BUCKET_EMPTY,
ItemID.CABBAGE,
EMPTY_JUG,
ItemID.KNIFE,
ItemID.LEATHER_BOOTS
)
),

new GuideStep(
"Return upstairs",
"Climb the basement ladder back into the Lumbridge Castle kitchen.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3209, 3216, 0),
5
)
)
.withId(
"early_010_exit_lumbridge_basement"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3209, 9616, 0),
ObjectID.LADDER_FROM_CELLAR
).areaRadius(4)
),

new GuideStep(
"Fill a jug with water",
"Fill one empty jug with water before continuing.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.JUG_WATER,
1
)
)
.withId(
"early_011_fill_jug_with_water"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3221, 3210, 0),
4
)
),

new GuideStep(
"Collect castle stair spawns",
"Pick up the Bronze arrow by the north staircase and the Mind rune by the south staircase.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BRONZE_ARROW, 1),

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.MINDRUNE, 1)
)
.withId(
"early_012_collect_castle_stair_spawns"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3218, 0),
15
),

GuideTarget.groundItem(
null,
ItemID.BRONZE_ARROW,
ItemID.MINDRUNE
)
),

new GuideStep(
"Collect the bronze dagger",
"Go up one floor and pick up the Bronze dagger inside Lumbridge Castle.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BRONZE_DAGGER, 1)
)
.withId(
"early_013_collect_bronze_dagger"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3205, 3208, 0),
ObjectID.SPIRALSTAIRS
).areaRadius(4),

GuideTarget.groundItem(
null,
ItemID.BRONZE_DAGGER
)
),

new GuideStep(
"Start Rune Mysteries",
"Talk to Duke Horacio and start Rune Mysteries. Stop after starting the quest.",

GuideRule.action(
GuideRuleType.QUEST_STARTED
).quest(
"Rune Mysteries"
)
)
.withId(
"early_014_start_rune_mysteries"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3209, 3222, 1),
NpcID.DUKE_OF_LUMBRIDGE
).areaRadius(5),

GuideTarget.dialogue(
"Have you any quests for me?",
"Yes."
)
),

new GuideStep(
"Go to the top floor",
"Climb to the top floor of Lumbridge Castle where the bank and log spawns are.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3208, 3218, 2),
12
)
)
.withId(
"early_015_go_to_lumbridge_bank"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3205, 3208, 1),
ObjectID.SPIRALSTAIRSMIDDLE
).areaRadius(4)
),

new GuideStep(
"Collect the four log spawns",
"Pick up all four Logs that spawn beside the Lumbridge Castle bank.",

GuideRule.action(
GuideRuleType.PICKUP
).item(
ItemID.LOGS,
4
)
)
.withId(
"early_016_collect_four_logs"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3209, 3221, 2),
10
),

GuideTarget.groundItem(
null,
ItemID.LOGS
)
),

new GuideStep(
"Bank everything",
"Open the Lumbridge Castle bank and deposit everything in your inventory.",

GuideRule.action(
GuideRuleType.INVENTORY_EMPTY
)
)
.withId(
"early_017_bank_everything"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
"Withdraw skilling tools",
"Withdraw your Tinderbox and Knife from the bank.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.TINDERBOX,
1
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.KNIFE,
1
)
)
.withId(
"early_018_withdraw_skilling_tools"
)
.withSection(
"Lumbridge setup"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
)

};
}
}