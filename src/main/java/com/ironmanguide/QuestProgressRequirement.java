package com.ironmanguide;

public class QuestProgressRequirement
{
public enum Type
{
VARP,
VARBIT
}

private final Type type;
private final int id;
private final int minimumValue;

public QuestProgressRequirement(
Type type,
int id,
int minimumValue)
{
this.type = type;
this.id = id;
this.minimumValue = minimumValue;
}

public Type getType()
{
return type;
}

public int getId()
{
return id;
}

public int getMinimumValue()
{
return minimumValue;
}
}