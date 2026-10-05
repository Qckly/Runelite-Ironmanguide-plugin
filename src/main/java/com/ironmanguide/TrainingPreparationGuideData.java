package com.ironmanguide;

import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;

public final class TrainingPreparationGuideData
{
private static final int EMPTY_JUG = 1935;
private static final int ASHES = 592;

private TrainingPreparationGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
"Train Firemaking to 15",
"Use the four log spawns beside the bank to train Firemaking to level 15. Pick up at least 4 ashes while training.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.FIREMAKING,
15
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ASHES,
4
).includeBank()
)
.withId(
"early_019_firemaking_15"
)
.withSection(
"Training and preparation"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3209, 3221, 2),
10
),

GuideTarget.groundItem(
null,
ItemID.LOGS,
ASHES
)
),

new GuideStep(
"Fletch 1,000 arrow shafts",
"Keep collecting the four log spawns and use your Knife on them until you have at least 1,000 Arrow shafts.",

GuideRule.action(
GuideRuleType.ITEM_ON_ITEM
).itemOnItem(
ItemID.KNIFE,
ItemID.LOGS
).guidanceOnly(),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.ARROW_SHAFT,
1000
).includeBank().hidden()
)
.withId(
"early_020_fletch_1000_arrow_shafts"
)
.withSection(
"Training and preparation"
)
.withExactResources(
GuideResourceRequirement.fromRemainingOutput(
ItemID.LOGS,
ItemID.ARROW_SHAFT,
1000,
15
)
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
"Bank four ashes",
"Make sure at least 4 Ashes are stored in the Lumbridge Castle bank.",

GuideRule.action(
GuideRuleType.BANK_HAS_ITEM
).item(
ASHES,
4
)
)
.withId(
"early_021_bank_four_ashes"
)
.withSection(
"Training and preparation"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
"Collect logs for later use",
"Collect enough Logs to have 11 total across your inventory and bank for later use.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.LOGS,
11
).includeBank()
)
.withId(
"early_022_collect_seven_logs"
)
.withSection(
"Training and preparation"
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
"Bank the logs",
"Make sure all 11 Logs are stored in the Lumbridge Castle bank.",

GuideRule.action(
GuideRuleType.BANK_HAS_ITEM
).item(
ItemID.LOGS,
11
)
)
.withId(
"early_023_bank_seven_logs"
)
.withSection(
"Training and preparation"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
"Prepare the Lumbridge route loadout",
"Withdraw all of your Coins, Air runes and Mind runes. Also withdraw the X Marks the Spot clue, Air talisman, Spade, Bread and Shrimp.",

GuideRule.action(
GuideRuleType.ALL_OWNED_IN_INVENTORY
).item(
ItemID.COINS,
1
),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.CLUEQUEST_CLUE1, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.AIR_TALISMAN, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.SPADE, 1),

GuideRule.action(
GuideRuleType.ALL_OWNED_IN_INVENTORY
).item(
ItemID.AIRRUNE,
1
),

GuideRule.action(
GuideRuleType.ALL_OWNED_IN_INVENTORY
).item(
ItemID.MINDRUNE,
1
),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.BREAD, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.SHRIMP, 1)
)
.withId(
"early_024_prepare_lumbridge_route_loadout"
)
.withSection(
"Training and preparation"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
"Return to the ground floor",
"Climb down both staircases and return to the ground floor of Lumbridge Castle.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3208, 3218, 0),
18
)
)
.withId(
"early_025_return_to_ground_floor"
)
.withSection(
"Training and preparation"
)
.withTargets(
GuideTarget.object(
new WorldPoint(3205, 3208, 2),
56231
).areaRadius(1),

GuideTarget.object(
new WorldPoint(3205, 3208, 1),
ObjectID.SPIRALSTAIRSMIDDLE
).areaRadius(1)
),

new GuideStep(
"Train Thieving to 5",
"Pickpocket the Men and Women around Lumbridge Castle until you reach level 5 Thieving.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.THIEVING,
5
)
)
.withId(
"early_026_thieving_5"
)
.withSection(
"Training and preparation"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3215, 3219, 0),
NpcID.MAN2,
NpcID.MAN3,
NpcID.DSKIN_W_ARDOUNGECITIZEN2,
NpcID.AVAN_FITZHARMON_MAN
).areaRadius(12)
)

};
}
}