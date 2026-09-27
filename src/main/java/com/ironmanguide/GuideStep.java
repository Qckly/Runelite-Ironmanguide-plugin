package com.ironmanguide;

public class GuideStep
{
private final GuideStepType type;
private final String title;
private final String description;
private final GuideRule[] rules;

private String id;
private String questHelperQuestName;

private GuideTarget[] targets =
new GuideTarget[0];

private GuideResourceRequirement[] exactResources =
new GuideResourceRequirement[0];

public GuideStep(
GuideStepType type,
String title,
String description)
{
this(
type,
title,
description,
new GuideRule[0]
);
}

public GuideStep(
GuideStepType type,
String title,
String description,
GuideRule... rules)
{
this.type = type;
this.title = title;
this.description = description;

this.rules =
rules != null
? rules
: new GuideRule[0];
}

public GuideStep withId(String id)
{
this.id = id;
return this;
}

public GuideStep withQuestHelper(
String questName)
{
this.questHelperQuestName = questName;
return this;
}

public GuideStep withTargets(
GuideTarget... targets)
{
this.targets =
targets != null
? targets
: new GuideTarget[0];

return this;
}

public GuideStep withExactResources(
GuideResourceRequirement... exactResources)
{
this.exactResources =
exactResources != null
? exactResources
: new GuideResourceRequirement[0];

return this;
}

public String getId()
{
return id;
}

public String getQuestHelperQuestName()
{
return questHelperQuestName;
}

public GuideStepType getType()
{
return type;
}

public String getTitle()
{
return title;
}

public String getDescription()
{
return description;
}

public GuideRule[] getRules()
{
return rules;
}

public GuideTarget[] getTargets()
{
return targets;
}
public GuideResourceRequirement[] getExactResources()
{
return exactResources;
}
}