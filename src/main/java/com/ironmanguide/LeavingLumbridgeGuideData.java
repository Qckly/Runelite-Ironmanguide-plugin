package com.ironmanguide;

import net.runelite.api.Skill;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.ObjectID;
import net.runelite.api.gameval.VarbitID;

public final class LeavingLumbridgeGuideData
{
private static final int EMPTY_JUG = 1935;
private static final int ASHES = 592;

private LeavingLumbridgeGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
)
.withTargets(
GuideTarget.npc(
new WorldPoint(3231, 3203, 0),
NpcID.BOB
).areaRadius(6)
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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
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
.withSection(
"Leaving Lumbridge"
)
.withQuestHelper(
"Rune Mysteries"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3104, 3162, 0),
8
)
)

};
}
}