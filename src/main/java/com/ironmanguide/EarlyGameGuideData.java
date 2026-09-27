package com.ironmanguide;

import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.gameval.VarbitID;

public final class EarlyGameGuideData
{
private static final int EMPTY_JUG = 1935;
private static final int ASHES = 592;

private EarlyGameGuideData()
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
).quest(
"X Marks the Spot"
)
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
"Drop your Air runes and Mind runes before claiming replacement runes from the Magic tutor.",

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
"Right-click the Magic combat tutor and choose Claim to receive replacement Air and Mind runes.",

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
"Pick up the Air runes and Mind runes you dropped.",

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
null,
ItemID.AIRRUNE,
ItemID.MINDRUNE
)
),

new GuideStep(
GuideStepType.TEXT,
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
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.object(
new WorldPoint(3209, 3216, 0),
ObjectID.QIP_COOK_TRAPDOOR_OPEN
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
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
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.object(
new WorldPoint(3209, 9616, 0),
ObjectID.LADDER_FROM_CELLAR
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.location(
new WorldPoint(3221, 3210, 0),
4
)
),

new GuideStep(
GuideStepType.TEXT,
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
GuideStepType.TEXT,
"Collect the bronze dagger",
"Go up one floor and pick up the Bronze dagger inside Lumbridge Castle.",

GuideRule.action(GuideRuleType.PICKUP)
.item(ItemID.BRONZE_DAGGER, 1)
)
.withId(
"early_013_collect_bronze_dagger"
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
GuideStepType.TEXT,
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
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.object(
new WorldPoint(3205, 3208, 1),
ObjectID.SPIRALSTAIRSMIDDLE
).areaRadius(4)
),

new GuideStep(
GuideStepType.TEXT,
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
GuideStepType.TEXT,
"Bank everything",
"Open the Lumbridge Castle bank and deposit everything in your inventory.",

GuideRule.action(
GuideRuleType.INVENTORY_EMPTY
)
)
.withId(
"early_017_bank_everything"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
GuideStepType.TEXT,
"Train Firemaking to 15",
"Use the four log spawns beside the bank to train Firemaking to level 15. Pick up at least 4 ashes while training.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.FIREMAKING,
15
),

GuideRule.action(
GuideRuleType.PICKUP
).item(
ASHES,
4
)
)
.withId(
"early_019_firemaking_15"
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
GuideStepType.TEXT,
"Fletch 1,000 arrow shafts",
"Keep collecting the four log spawns and use your Knife on them until you have at least 1,000 Arrow shafts.\n\nYou will need 67 Logs.",

GuideRule.action(
GuideRuleType.ITEM_ON_ITEM
).itemOnItem(
ItemID.KNIFE,
ItemID.LOGS
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.ARROW_SHAFT,
1000
).hidden()
)
.withId(
"early_020_fletch_1000_arrow_shafts"
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
GuideStepType.TEXT,
"Bank four ashes",
"Deposit at least 4 Ashes into the Lumbridge Castle bank.",

GuideRule.action(
GuideRuleType.BANK_DEPOSIT
).item(
ASHES,
4
)
)
.withId(
"early_021_bank_four_ashes"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
GuideStepType.TEXT,
"Collect seven logs",
"Pick up 7 more Logs from the spawns beside the bank for later use.",

GuideRule.action(
GuideRuleType.PICKUP
).item(
ItemID.LOGS,
7
)
)
.withId(
"early_022_collect_seven_logs"
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
GuideStepType.TEXT,
"Bank seven logs",
"Deposit the 7 Logs you just collected.",

GuideRule.action(
GuideRuleType.BANK_DEPOSIT
).item(
ItemID.LOGS,
7
)
)
.withId(
"early_023_bank_seven_logs"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
GuideStepType.TEXT,
"Prepare the Lumbridge route loadout",
"Withdraw your coins, X Marks the Spot clue, Air talisman, Spade, Air runes, Mind runes, Bread and Shrimps.",

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.COINS, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.CLUEQUEST_CLUE1, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.AIR_TALISMAN, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.SPADE, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.AIRRUNE, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.MINDRUNE, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.BREAD, 1),

GuideRule.action(GuideRuleType.HAVE_ITEM)
.item(ItemID.SHRIMP, 1)
)
.withId(
"early_024_prepare_lumbridge_route_loadout"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3208, 3220, 2),
8
)
),

new GuideStep(
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.object(
null,
ObjectID.SPIRALSTAIRSTOP,
ObjectID.SPIRALSTAIRSMIDDLE
)
),

new GuideStep(
GuideStepType.TEXT,
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
.withTargets(
GuideTarget.npc(
new WorldPoint(3215, 3219, 0),
NpcID.MAN2,
NpcID.MAN3,
NpcID.DSKIN_W_ARDOUNGECITIZEN2,
NpcID.AVAN_FITZHARMON_MAN
).areaRadius(12)
),

new GuideStep(
GuideStepType.TEXT,
"Dig north of Bob's Axes",
"Dig on the X Marks the Spot tile north of Bob's Brilliant Axes.",

GuideRule.action(
GuideRuleType.VARBIT_AT_LEAST
).varbit(
VarbitID.CLUEQUEST,
3
)
)
.withId(
"early_027_x_marks_bob"
)
.withQuestHelper(
"X Marks the Spot"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3230, 3209, 0),
2
)
),

new GuideStep(
GuideStepType.TEXT,
"Buy a steel axe",
"Buy 1 Steel axe from Bob's Brilliant Axes. Full stock should cost about 200 coins.",

GuideRule.action(
GuideRuleType.BUY
).item(
ItemID.STEEL_AXE,
1
)
)
.withId(
"early_028_buy_steel_axe"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3232, 3203, 0),
6
)
),

new GuideStep(
GuideStepType.TEXT,
"Start The Restless Ghost",
"Talk to Father Aereck in Lumbridge Church and start The Restless Ghost.",

GuideRule.action(
GuideRuleType.QUEST_STARTED
).quest(
"The Restless Ghost"
)
)
.withId(
"early_029_start_restless_ghost"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3243, 3206, 0),
NpcID.FATHER_AERECK
).areaRadius(5),

GuideTarget.dialogue(
"I'm looking for a quest!",
"Yes."
)
),

new GuideStep(
GuideStepType.TEXT,
"Dig behind Lumbridge Castle",
"Dig the next X Marks the Spot clue just behind Lumbridge Castle, outside the kitchen.",

GuideRule.action(
GuideRuleType.VARBIT_AT_LEAST
).varbit(
VarbitID.CLUEQUEST,
4
)
)
.withId(
"early_030_x_marks_castle"
)
.withQuestHelper(
"X Marks the Spot"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3203, 3213, 0),
2
)
),

new GuideStep(
GuideStepType.TEXT,
"Get raw rat meat",
"Kill a Giant rat near Lumbridge Swamp and pick up its Raw rat meat. Wind Strike can be used safely from range.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.RAW_RAT_MEAT,
1
)
)
.withId(
"early_031_get_raw_rat_meat"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3195, 3185, 0),
NpcID.GIANTRAT,
NpcID.GIANTRAT2,
NpcID.GIANTRAT3,
NpcID.GIANTRAT_GREY
).areaRadius(25)
),

new GuideStep(
GuideStepType.TEXT,
"Run west through the swamp",
"Follow the fence west toward the Lumbridge Swamp cave entrance. Keep some run energy available in case something attacks you.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3169, 3172, 0),
10
)
)
.withId(
"early_032_reach_swamp_cave"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3169, 3172, 0),
10
)
),

new GuideStep(
GuideStepType.TEXT,
"Collect 5 swamp tar",
"Pick up 5 Swamp tar around the Lumbridge Swamp cave entrance.",

GuideRule.action(
GuideRuleType.PICKUP
).item(
ItemID.SWAMP_TAR,
5
)
)
.withId(
"early_033_collect_swamp_tar"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3169, 3172, 0),
15
),

GuideTarget.groundItem(
null,
ItemID.SWAMP_TAR
)
),

new GuideStep(
GuideStepType.TEXT,
"Get the Ghostspeak amulet",
"Talk to Father Urhney, receive the Ghostspeak amulet and equip it.",

GuideRule.action(
GuideRuleType.EQUIP
).item(
ItemID.AMULET_OF_GHOSTSPEAK,
1
)
)
.withId(
"early_034_get_ghostspeak_amulet"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3147, 3175, 0),
NpcID.FATHER_URHNEY
).areaRadius(5),

GuideTarget.dialogue(
"Father Aereck sent me to talk to you.",
"He's got a ghost haunting his graveyard."
)
),

new GuideStep(
GuideStepType.TEXT,
"Take the Air talisman to Sedridor",
"Go to the Wizards' Tower basement. Give Sedridor the Air talisman and continue until he gives you the Research package.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.RESEARCH_PACKAGE,
1
)
)
.withId(
"early_035_rune_mysteries_package"
)
.withQuestHelper(
"Rune Mysteries"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3104, 3162, 0),
8
)
),

new GuideStep(
GuideStepType.TEXT,
"Run to Draynor Village",
"Leave the Wizards' Tower and head north-west into Draynor Village.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3091, 3252, 0),
18
)
)
.withId(
"early_036_reach_draynor"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3091, 3252, 0),
18
)
),

new GuideStep(
GuideStepType.TEXT,
"Dig the Draynor clue",
"Dig the next X Marks the Spot clue south-west of the wheat field east of Draynor Village.",

GuideRule.action(
GuideRuleType.VARBIT_AT_LEAST
).varbit(
VarbitID.CLUEQUEST,
5
)
)
.withId(
"early_037_x_marks_draynor"
)
.withQuestHelper(
"X Marks the Spot"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3108, 3262, 0),
2
)
),

new GuideStep(
GuideStepType.TEXT,
"Pick up 2 cheese",
"Pick up 2 Cheese from the spawn inside Aggie's house in Draynor Village.",

GuideRule.action(
GuideRuleType.PICKUP
).item(
ItemID.CHEESE,
2
)
)
.withId(
"early_038_pick_up_cheese"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3086, 3257, 0),
6
),

GuideTarget.groundItem(
null,
ItemID.CHEESE
)
),

new GuideStep(
GuideStepType.TEXT,
"Buy 5 jugs of wine",
"Buy 5 Jugs of wine from Fortunato. Keep them as emergency food.",

GuideRule.action(
GuideRuleType.BUY
).item(
ItemID.JUG_WINE,
5
)
)
.withId(
"early_039_buy_wine"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3085, 3251, 0),
NpcID.RAG_WINE_MERCHANT
).areaRadius(5)
),

new GuideStep(
GuideStepType.TEXT,
"Dig inside the pig pen",
"Dig the final X Marks the Spot clue inside Martin the Master Gardener's pig pen.",

GuideRule.action(
GuideRuleType.VARBIT_AT_LEAST
).varbit(
VarbitID.CLUEQUEST,
6
)
)
.withId(
"early_040_x_marks_pig_pen"
)
.withQuestHelper(
"X Marks the Spot"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3078, 3259, 0),
2
)
),

new GuideStep(
GuideStepType.TEXT,
"Finish X Marks the Spot",
"Take the Ancient casket to Veos in Port Sarim and finish X Marks the Spot. Do not use the XP lamp yet.",

GuideRule.action(
GuideRuleType.QUEST_FINISHED
).quest(
"X Marks the Spot"
)
)
.withId(
"early_041_finish_x_marks"
)
.withQuestHelper(
"X Marks the Spot"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3054, 3245, 0),
NpcID.VEOS_VISIBLE,
NpcID.VEOS_VISIBLE_TRAVEL
).areaRadius(6)
)

};
}
}