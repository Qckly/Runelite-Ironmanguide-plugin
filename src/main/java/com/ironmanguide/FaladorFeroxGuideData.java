package com.ironmanguide;

import net.runelite.api.coords.WorldPoint;
import net.runelite.api.gameval.ItemID;
import net.runelite.api.gameval.NpcID;

public final class FaladorFeroxGuideData
{
private static final int POOL_OF_REFRESHMENT = 39651;
private static final int CASTLE_WARS_PORTAL = 30386;

private FaladorFeroxGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
"Pick up 1 Snape grass",
"Walk toward Rimmington and the Crafting Guild. Go to the hobgoblin peninsula west of the Crafting Guild and pick up 1 Snape grass. Have about 10 run energy available so you can grab it and get away from the hobgoblins.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.SNAPE_GRASS,
1
)
)
.withId(
"early_042_pick_up_snape_grass"
)
.withSection(
"Falador and Ferox"
)
.withTargets(
GuideTarget.location(
new WorldPoint(2933, 3285, 0),
0
),
GuideTarget.groundItem(
null,
ItemID.SNAPE_GRASS
)
),

new GuideStep(
"Walk to Falador",
"Walk north to Falador.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(2966, 3380, 0),
20
)
)
.withId(
"early_043_reach_falador"
)
.withSection(
"Falador and Ferox"
)
.withTargets(
GuideTarget.location(
new WorldPoint(2966, 3380, 0),
0
)
),

new GuideStep(
"Start The Knight's Sword",
"Go to the White Knights' Castle and talk to the Squire in the courtyard to start The Knight's Sword.",

GuideRule.action(
GuideRuleType.QUEST_STARTED
).quest(
"The Knight's Sword"
)
)
.withId(
"early_044_start_knights_sword"
)
.withSection(
"Falador and Ferox"
)
.withQuestHelper(
"The Knight's Sword"
)
.withTargets(
GuideTarget.npc(
null,
NpcID.SQUIRE
)
),

new GuideStep(
"Minigame teleport to Clan Wars",
"Use the minigame teleport to Clan Wars. You will arrive at the Clan Wars area in Ferox Enclave.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(3130, 3630, 0),
35
)
)
.withId(
"early_045_teleport_clan_wars"
)
.withSection(
"Falador and Ferox"
)
.withTargets(
GuideTarget.location(
new WorldPoint(3130, 3630, 0),
0
)
),

new GuideStep(
"Collect 2 iron bars",
"Leave Ferox Enclave through the west side and pick up 2 Iron bars from the spawn just north-west of the enclave. You are entering the Wilderness, so do not carry anything you are not prepared to lose.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.IRON_BAR,
2
).includeBank()
)
.withId(
"early_046_collect_two_iron_bars"
)
.withSection(
"Falador and Ferox"
)
.withTargets(
GuideTarget.groundItem(
null,
ItemID.IRON_BAR
)
),

new GuideStep(
"Recharge at the Pool of Refreshment",
"Return inside Ferox Enclave and drink from the Pool of Refreshment to restore your run energy and other stats.",

GuideRule.action(
GuideRuleType.OBJECT_INTERACT
).object(
POOL_OF_REFRESHMENT
)
)
.withId(
"early_047_recharge_at_ferox"
)
.withSection(
"Falador and Ferox"
)
.withTargets(
GuideTarget.object(
null,
POOL_OF_REFRESHMENT
)
),

new GuideStep(
"Use the green portal to Castle Wars",
"Use the green Castle Wars portal in Ferox Enclave. The step completes when you arrive in the Castle Wars lobby.",

GuideRule.action(
GuideRuleType.LOCATION
).location(
new WorldPoint(2440, 3090, 0),
20
)
)
.withId(
"early_048_reach_castle_wars"
)
.withSection(
"Falador and Ferox"
)
.withTargets(
GuideTarget.object(
null,
CASTLE_WARS_PORTAL
),
GuideTarget.location(
new WorldPoint(2440, 3090, 0),
0
)
)

};
}
}