package com.ironmanguide;

import net.runelite.api.Skill;
import net.runelite.api.gameval.ItemID;

public final class ArdougneGnomeGuideData
{
private static final int OAK_TREE = 10820;
private static final int SMALL_FISHING_NET = 303;
private static final int SWAMP_PASTE = 1941;

private ArdougneGnomeGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
GuideStepType.TEXT,
"Train Woodcutting to 15",
"Monk's Friend gives you 2,000 Woodcutting XP, but that is not quite enough to cut oak trees. Cut nearby normal trees until 15 Woodcutting. Light the Logs with your Tinderbox as you go.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.WOODCUTTING,
15
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.TINDERBOX,
1
)
)
.withId(
"early_056_woodcutting_15_trees"
)
.withSection(
"Ardougne and Gnome Stronghold"
)
.withExactResources(
GuideResourceRequirement.forSkillLevel(
ItemID.LOGS,
Skill.WOODCUTTING,
15,
25.0
)
),

new GuideStep(
GuideStepType.TEXT,
"Train Woodcutting to 35",
"Cut the two oak trees just south of Ardougne Zoo until 35 Woodcutting. Light the Oak logs with your Tinderbox as you cut them instead of banking them.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.WOODCUTTING,
35
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.TINDERBOX,
1
)
)
.withId(
"early_057_woodcutting_35_oaks"
)
.withSection(
"Ardougne and Gnome Stronghold"
)
.withExactResources(
GuideResourceRequirement.forSkillLevel(
ItemID.OAK_LOGS,
Skill.WOODCUTTING,
35,
37.5
)
)
.withTargets(
GuideTarget.object(
null,
OAK_TREE
)
),

new GuideStep(
GuideStepType.TEXT,
"Reach 30 Firemaking",
"If you followed the previous step and burned the Oak logs as you cut them, this step should complete immediately. Otherwise, burn Oak logs until 30 Firemaking.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.FIREMAKING,
30
)
)
.withId(
"early_058_firemaking_30"
)
.withSection(
"Ardougne and Gnome Stronghold"
)
.withExactResources(
GuideResourceRequirement.forSkillLevel(
ItemID.OAK_LOGS,
Skill.FIREMAKING,
30,
60.0
)
),

new GuideStep(
GuideStepType.TEXT,
"Train Thieving to 20",
"Steal from the Ardougne bakery stalls until 20 Thieving. Keep and bank at least 15 Bread and every normal Cake you get. Drop the chocolate slices. Keep stealing after 20 if you still have fewer than 15 Bread.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.THIEVING,
20
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.BREAD,
15
).includeBank()
)
.withId(
"early_059_thieving_20_cakes"
)
.withSection(
"Ardougne and Gnome Stronghold"
),

new GuideStep(
GuideStepType.TEXT,
"Train Thieving to 25",
"Steal Silk from the Ardougne silk stalls until 25 Thieving. Keep all of the Silk for now. We will sell most of it later, but 10 pieces will eventually stay in the bank for quests.",

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.THIEVING,
25
)
)
.withId(
"early_060_thieving_25_silk"
)
.withSection(
"Ardougne and Gnome Stronghold"
),

new GuideStep(
GuideStepType.TEXT,
"Prepare for Sheep Herder",
"Have at least 100 coins in your inventory before starting Sheep Herder. Doctor Orbon will charge 100 coins for the protective plague clothing.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.COINS,
100
)
)
.withId(
"early_061_prepare_sheep_herder"
)
.withSection(
"Ardougne and Gnome Stronghold"
),

new GuideStep(
GuideStepType.TEXT,
"Complete Sheep Herder",
"Complete Sheep Herder in Ardougne. Quest Helper will guide you through buying the plague suit, herding one sheep of each colour, poisoning them and incinerating the bones.",

GuideRule.action(
GuideRuleType.QUEST_FINISHED
).quest(
"Sheep Herder"
)
)
.withId(
"early_062_complete_sheep_herder"
)
.withSection(
"Ardougne and Gnome Stronghold"
)
.withQuestHelper(
"Sheep Herder"
),

new GuideStep(
GuideStepType.TEXT,
"Prepare for Sea Slug",
"Take your Swamp paste and a Small fishing net. Keep some coins with you as well because we will buy sardines in Witchaven immediately after the quest.",

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
SWAMP_PASTE,
1
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
SMALL_FISHING_NET,
1
),

GuideRule.action(
GuideRuleType.SKILL_LEVEL
).skill(
Skill.FIREMAKING,
30
)
)
.withId(
"early_063_prepare_sea_slug"
)
.withSection(
"Ardougne and Gnome Stronghold"
),

new GuideStep(
GuideStepType.TEXT,
"Complete Sea Slug",
"Complete Sea Slug with Quest Helper. While you are on the Fishing Platform, use the Small fishing net to catch at least one raw shrimp for the Ardougne diary. Keep one Raw shrimp until the quest is complete, and do not drop the Oyster pearls from the quest reward.",

GuideRule.action(
GuideRuleType.QUEST_FINISHED
).quest(
"Sea Slug"
),

GuideRule.action(
GuideRuleType.HAVE_ITEM
).item(
ItemID.RAW_SHRIMP,
1
).hidden()
)
.withId(
"early_064_complete_sea_slug"
)
.withSection(
"Ardougne and Gnome Stronghold"
)
.withQuestHelper(
"Sea Slug"
)

};
}
}