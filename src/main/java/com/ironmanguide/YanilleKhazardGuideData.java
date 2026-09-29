package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;

public final class YanilleKhazardGuideData
{
private static final int PIE_DISH = 2313;
private static final int JUG_OF_WATER = 1937;
private static final int SWAMP_PASTE = 1941;
private static final int BROTHER_OMAD = 4244;

private YanilleKhazardGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
GuideStepType.TEXT,
"Walk to Yanille",
"Walk east from Castle Wars to Yanille. Enter the city and head toward the cooking shop in the north-central part of town.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(2603, 3092, 0),
18
)
)
.withId(
"early_049_walk_to_yanille"
)
.withSection(
"Yanille and Port Khazard"
)
.withTargets(
GuideTarget.location(
new WorldPoint(2603, 3092, 0),
0
)
),

new GuideStep(
GuideStepType.TEXT,
"Buy a pie dish",
"Go to Frenita's Cookery Shop in Yanille and buy 1 Pie dish. Keep it for later questing.",

GuideRule.action(
GuideRuleType.BUY
).item(
PIE_DISH,
1
)
)
.withId(
"early_050_buy_pie_dish"
)
.withSection(
"Yanille and Port Khazard"
),

new GuideStep(
GuideStepType.TEXT,
"Get a Jug of water",
"Pick up the Jug of water inside Frenita's Cookery Shop. Keep it in your inventory because Monk's Friend will require it shortly.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
JUG_OF_WATER,
1
)
)
.withId(
"early_051_get_jug_of_water"
)
.withSection(
"Yanille and Port Khazard"
)
.withTargets(
GuideTarget.groundItem(
null,
JUG_OF_WATER
)
),

new GuideStep(
GuideStepType.TEXT,
"Walk to Port Khazard",
"Leave Yanille and walk north to Port Khazard.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(2674, 3143, 0),
18
)
)
.withId(
"early_052_walk_to_port_khazard"
)
.withSection(
"Yanille and Port Khazard"
)
.withTargets(
GuideTarget.location(
new WorldPoint(2674, 3143, 0),
0
)
),

new GuideStep(
GuideStepType.TEXT,
"Buy 1 swamp paste",
"Open the Khazard General Store and buy 1 Swamp paste. Keep it for a later quest.",

GuideRule.action(
GuideRuleType.BUY
).item(
SWAMP_PASTE,
1
)
)
.withId(
"early_053_buy_swamp_paste"
)
.withSection(
"Yanille and Port Khazard"
),

new GuideStep(
GuideStepType.TEXT,
"Prepare for Monk's Friend",
"Before starting Monk's Friend, have 1 Jug of water and 1 normal Logs in your inventory. You should already have the Jug of water from Yanille. If you do not have Logs, chop any nearby normal tree.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
JUG_OF_WATER,
1
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.LOGS,
1
)
)
.withId(
"early_054_prepare_monks_friend"
)
.withSection(
"Yanille and Port Khazard"
),

new GuideStep(
GuideStepType.TEXT,
"Complete Monk's Friend",
"Go north-west to the monastery south of Ardougne and complete Monk's Friend. Quest Helper will guide you through the blanket, Brother Omad and Brother Cedric steps.",

GuideRule.action(
GuideRuleType.QUEST_FINISHED
).quest(
"Monk's Friend"
)
)
.withId(
"early_055_complete_monks_friend"
)
.withSection(
"Yanille and Port Khazard"
)
.withQuestHelper(
"Monk's Friend"
)
.withTargets(
GuideTarget.npc(
null,
BROTHER_OMAD
)
)

};
}
}