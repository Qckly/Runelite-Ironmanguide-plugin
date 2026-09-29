package com.ironmanguide;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class EarlyGameGuideData
{
private EarlyGameGuideData()
{
}

public static GuideStep[] getSteps()
{
List<GuideStep> steps =
new ArrayList<>();

append(
steps,
StartingOffGuideData.getSteps()
);

append(
steps,
LumbridgeSetupGuideData.getSteps()
);

append(
steps,
TrainingPreparationGuideData.getSteps()
);

append(
steps,
LeavingLumbridgeGuideData.getSteps()
);

append(
steps,
DraynorXMarksGuideData.getSteps()
);

append(
steps,
FaladorFeroxGuideData.getSteps()
);

append(
steps,
YanilleKhazardGuideData.getSteps()
);

return steps.toArray(
new GuideStep[0]
);
}

private static void append(
List<GuideStep> steps,
GuideStep[] chapter)
{
Collections.addAll(
steps,
chapter
);
}
}