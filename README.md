# OSBot API
A cooler OSBot API (work in progress)

# Finders
Finders provide a nicer way for you to search for entities, npcs, and widgets (more to come)

# Finding an NPC
Simple closest (using heustric, not game tiles):
<pre>
getNPCFinder().findClosest("Man");
</pre>

<b>Using FinderConditions</b>
A FinderCondition is a simple lambda to wittle your search.

Closest "Man" NPC with full health:
<pre>
getNPCFinder().findThatMeetsCondition("Man", (npc) -> (npc.getHealth() == 100), FinderDistance.CLOSEST);
</pre>

<b>OR</b>

<pre>
getNPCFinder().findClosest("Man", (npc) -> (npc.getHealth() == 100));
</pre>

# Finding a widget
Finding the "Close" widget for the GE/bank/collect box:
<pre>
getWidgetFinder().findFromAction("Close", (widget) -> (widget.getSpriteIndex1() == 535));
</pre>

# Finding an entity
Works exactly the same as NPCFinder, just with <b>getEntityFinder()</b>

# The FinderDistance enum
<b>CLOSEST</b> - Closest with the Octile Heustric
<b>CLOSEST_GAMETILES</b> - Closest with game tiles (default OSBot #closest method)
<b>FURTHEST</b> - Furthest with the Octile Heustric
<b>FURTHEST_GAMETILES</b> - Furthest with game tiles

# Usage
Instead of making your script class extend <b>Script</b>, make it extend <b>OmniScript</b>