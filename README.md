# OSBot API
A cooler OSBot API (work in progress)

# Webwalking
Walking from your position to a specified position:
<pre>
getWebWalker().walkPath(Position);
</pre>

Walking given a start and finish:
<pre>
getWebWalker().walkPath(startPosition, finishPosition);
</pre>

# Sleeping
Sleeping is simpler in OmniAPI:
<pre>
sleepUntil(() -> (myPlayer().isAnimating()));
</pre>

<b>sleepUntil()</b> returns <b>TRUE</b> if the sleep was completed BEFORE the time was over, and <b>FALSE</b> if the sleep was completed because the time was over.

# Interfaces (WORK IN PROGRESS)
Interfaces are little extensions upon OmniAPI that utilise the API for certain interfaces (bank, GE etc)

# Basic GE usage
<pre>
getRSGrandExchange().open();
getRSGrandExchange().getAvailableSlots(); //0-8
getRSGrandExchange().createOffer(getInventoryFinder().find("Bronze dagger"), 15); //Create an offer for Bronze Dagger for 15gp
</pre>

# Modules (WORK IN PROGRESS)
Modules are there to mimic OSBots previous Random Solvers while still having interaction consistency with the rest of OmniAPI's interactions.


# Finders
Finders provide a nicer way for you to search for entities, npcs, and widgets (more to come)

# Finding an NPC
Simple closest (using heuristric, not game tiles):
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
<b>CLOSEST</b> - Closest with the Octile Heustric<br />
<b>CLOSEST_GAMETILES</b> - Closest with game tiles (default OSBot #closest method)<br />
<b>FURTHEST</b> - Furthest with the Octile Heustric<br />
<b>FURTHEST_GAMETILES</b> - Furthest with game tiles<br />

# Usage
Instead of making your script class extend <b>Script</b>, make it extend <b>OmniScript</b>