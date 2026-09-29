package com.ironmanguide;

import java.util.HashSet;
import java.util.Set;

public final class GuideDataValidator
{
private GuideDataValidator()
{
}

public static GuideStep[] validate(
GuideStep[] steps)
{
if (steps == null)
{
throw new IllegalStateException(
"Guide steps must not be null"
);
}

if (steps.length == 0)
{
throw new IllegalStateException(
"Guide must contain at least one step"
);
}

Set<String> ids =
new HashSet<>();

for (
int index = 0;
index < steps.length;
index++
)
{
GuideStep step =
steps[index];

if (step == null)
{
throw new IllegalStateException(
"Guide step "
+ (index + 1)
+ " is null"
);
}

String id =
step.getId();

if (id == null
|| id.isBlank())
{
throw new IllegalStateException(
"Guide step "
+ (index + 1)
+ " has no stable ID"
);
}

if (!ids.add(id))
{
throw new IllegalStateException(
"Duplicate guide step ID: "
+ id
);
}

String title =
step.getTitle();

if (title == null
|| title.isBlank())
{
throw new IllegalStateException(
"Guide step "
+ id
+ " has no title"
);
}

String section =
step.getSection();

if (section == null
|| section.isBlank())
{
throw new IllegalStateException(
"Guide step "
+ id
+ " has no section"
);
}
}

return steps;
}
}