package com.ironmanguide;

import net.runelite.api.Skill;
import net.runelite.api.gameval.ItemID;

public final class ArdougneGnomeGuideData
{
private static final int OAK_TREE = 10820;

private ArdougneGnomeGuideData()
{
}

public static GuideStep[] getSteps()
{
return new GuideStep[] {

new GuideStep(
GuideStepType.TEXT,
"Train Woodcutting to 35",
"Cut oak trees around Ardougne until 35 Woodcutting. Light the Oak logs with your Tinderbox as you cut them instead of banking them.",

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
"early_056_woodcutting_35_oaks"
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
)

};
}
}