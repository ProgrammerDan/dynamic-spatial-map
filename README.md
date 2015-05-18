# dynamic-spatial-map
Fast 3d lookups, primarily applicable for non-infinite spatial dimensions (such as games) with one dimension singificantly more constrained than the others.

Autogrows with some clever underlying efficiency designs, should be constant time inserts and retrievals if the dimensions are constrainted, and worst case ln(n) if they are not, although I haven't done a formal analysis on the algorithm yet.
