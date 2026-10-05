package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;
import net.runelite.api.gameval.VarbitID;

public final class DraynorXMarksGuideData
{

private DraynorXMarksGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

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
.withSection(
"Draynor and X Marks"
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
.withSection(
"Draynor and X Marks"
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
.withSection(
"Draynor and X Marks"
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
.withSection(
"Draynor and X Marks"
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
.withSection(
"Draynor and X Marks"
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
.withSection(
"Draynor and X Marks"
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