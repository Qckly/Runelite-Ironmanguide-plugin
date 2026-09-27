package com.ironmanguide;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GuideResourceRequirementTest
{
@Test
public void calculatesCeilingForDeterministicOutput()
{
GuideResourceRequirement requirement =
GuideResourceRequirement.fromOutput(
1511,
1000,
15
);

assertEquals(1511, requirement.getItemId());
assertEquals(67, requirement.getQuantity());
}

@Test(expected = IllegalArgumentException.class)
public void rejectsUnknownOrVariableQuantity()
{
GuideResourceRequirement.exact(
1511,
0
);
}
}
