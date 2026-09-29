package com.ironmanguide;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class IronmanGuideStepListPanel
extends JPanel
implements Scrollable
{
private static final Color SECTION_ACTIVE =
new Color(
225,
145,
0
);

private static final Color SECTION_INACTIVE =
new Color(
38,
38,
38
);

private static final Color CARD_BACKGROUND =
new Color(
31,
31,
31
);

private static final Color CARD_BORDER =
new Color(
62,
62,
62
);

private static final Color ACTIVE_TEXT =
new Color(
255,
180,
0
);

private static final Color COMPLETE_TEXT =
new Color(
145,
145,
145
);

private static final Color FUTURE_TEXT =
new Color(
210,
210,
210
);

private final GuideManager guideManager;

private final Set<String> collapsedSections =
new HashSet<>();

private String lastCurrentSection;

public IronmanGuideStepListPanel(
GuideManager guideManager)
{
this.guideManager =
guideManager;

setLayout(
new BoxLayout(
this,
BoxLayout.Y_AXIS
)
);

setOpaque(false);

initializeSectionState();

refresh();
}

public void refresh()
{
String currentSection =
getSectionName(
guideManager.getCurrentStepIndex()
);

if (lastCurrentSection == null)
{
initializeSectionState();
}
else if (!lastCurrentSection.equals(
currentSection
))
{
collapsedSections.add(
lastCurrentSection
);

collapsedSections.remove(
currentSection
);

lastCurrentSection =
currentSection;
}

removeAll();

String previousSection =
null;

JPanel currentRow =
null;

for (
int i = 0;
i < guideManager.getTotalSteps();
i++
)
{
GuideStep step =
guideManager.getStep(i);

String section =
getSectionName(i);

if (!section.equals(previousSection))
{
add(
createSectionHeader(
section,
isCurrentSection(i)
)
);

previousSection =
section;
}

if (collapsedSections.contains(
section
))
{
continue;
}

JPanel row =
createStepCard(
step,
i
);

add(row);

if (i ==
guideManager.getCurrentStepIndex())
{
currentRow =
row;
}
}

revalidate();
repaint();

JPanel rowToShow =
currentRow;

if (rowToShow != null)
{
SwingUtilities.invokeLater(() ->
{
rowToShow.scrollRectToVisible(
new Rectangle(
0,
0,
1,
Math.max(
1,
rowToShow
.getPreferredSize()
.height
)
)
);
});
}
}

private void initializeSectionState()
{
collapsedSections.clear();

String currentSection =
getSectionName(
guideManager.getCurrentStepIndex()
);

for (
int i = 0;
i < guideManager.getTotalSteps();
i++
)
{
String section =
getSectionName(i);

if (!section.equals(
currentSection
))
{
collapsedSections.add(
section
);
}
}

lastCurrentSection =
currentSection;
}

private JPanel createSectionHeader(
String text,
boolean active)
{
boolean collapsed =
collapsedSections.contains(
text
);

JPanel panel =
new JPanel(
new BorderLayout()
);

panel.setMaximumSize(
new Dimension(
Integer.MAX_VALUE,
42
)
);

panel.setBackground(
active
? SECTION_ACTIVE
: SECTION_INACTIVE
);

panel.setBorder(
new EmptyBorder(
9,
10,
9,
10
)
);

panel.setCursor(
Cursor.getPredefinedCursor(
Cursor.HAND_CURSOR
)
);

JLabel label =
new JLabel(
"<html><b>"
+ (
collapsed
? "▶ "
: "▼ "
)
+ escapeHtml(text)
+ "</b></html>"
);

label.setForeground(
active
? Color.BLACK
: Color.WHITE
);

panel.add(
label,
BorderLayout.CENTER
);

panel.addMouseListener(
new MouseAdapter()
{
@Override
public void mouseClicked(
MouseEvent event)
{
if (collapsedSections.contains(
text
))
{
collapsedSections.remove(
text
);
}
else
{
collapsedSections.add(
text
);
}

refresh();
}
}
);

return panel;
}

private JPanel createStepCard(
GuideStep step,
int index)
{
boolean completed =
guideManager.isStepCompleted(index);

boolean current =
index ==
guideManager.getCurrentStepIndex();

JPanel card =
new JPanel(
new BorderLayout(
0,
5
)
);

card.setOpaque(true);

card.setBackground(
CARD_BACKGROUND
);

card.setBorder(
BorderFactory.createCompoundBorder(
BorderFactory.createMatteBorder(
0,
0,
1,
0,
CARD_BORDER
),
new EmptyBorder(
9,
8,
9,
8
)
)
);

String prefix;

Color titleColor;

if (completed)
{
prefix = "✓ ";
titleColor =
COMPLETE_TEXT;
}
else if (current)
{
prefix = "";
titleColor =
ACTIVE_TEXT;
}
else
{
prefix = "";
titleColor =
FUTURE_TEXT;
}

JLabel title =
new JLabel(
"<html><body style='width:160px'>"
+ prefix
+ escapeHtml(
step.getTitle()
)
+ "</body></html>"
);

title.setVerticalAlignment(
SwingConstants.TOP
);

title.setForeground(
titleColor
);

card.add(
title,
BorderLayout.NORTH
);

if (current)
{
JLabel description =
new JLabel(
"<html><body style='width:160px'>"
+ escapeHtml(
step.getDescription()
)
+ "</body></html>"
);

description.setForeground(
new Color(
215,
215,
215
)
);

description.setVerticalAlignment(
SwingConstants.TOP
);

description.setBorder(
new EmptyBorder(
3,
0,
2,
0
)
);

card.add(
description,
BorderLayout.CENTER
);
}

return card;
}

private boolean isCurrentSection(
int index)
{
return getSectionName(index)
.equals(
getSectionName(
guideManager.getCurrentStepIndex()
)
);
}

private String getSectionName(
int index)
{
GuideStep step =
guideManager.getStep(index);

String section =
step.getSection();

if (section == null
|| section.isBlank())
{
return "Guide";
}

return section;
}
private String escapeHtml(
String text)
{
if (text == null)
{
return "";
}

return text
.replace("&", "&amp;")
.replace("<", "&lt;")
.replace(">", "&gt;");
}

@Override
public Dimension getPreferredScrollableViewportSize()
{
return getPreferredSize();
}

@Override
public int getScrollableUnitIncrement(
Rectangle visibleRect,
int orientation,
int direction)
{
return 16;
}

@Override
public int getScrollableBlockIncrement(
Rectangle visibleRect,
int orientation,
int direction)
{
return Math.max(
16,
visibleRect.height - 32
);
}

@Override
public boolean getScrollableTracksViewportWidth()
{
return true;
}

@Override
public boolean getScrollableTracksViewportHeight()
{
return false;
}
}