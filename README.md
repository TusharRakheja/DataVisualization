# Dolphins of Doubtful Sound
Contains Java code to make an interactive network-based visualization of a community of dolphins living off Doubtful Sound, New Zealand.

**What it does**

* Dolphin.java is the main class. It defines the Dolphin data type, and accesses a small online database modeling communication data amongst a community of 62 dolphins as a graph or a network. 

* The data was put together by Lusseau et al. (2003) [1], and can be found [here](http://networkdata.ics.uci.edu/data/dolphins/).

* It models the data as a network with nodes on a ring. I wanted to try edge-bundling, hence the ring, but as of now it's not been done. Later. 

* Hovering over a certain node will highlight its connections. The size of each node is proportional to its number of connections (or network size).

**To do**

* Edge-bundling in this graph. Fix purple-shade bug. Make it more efficient.

* A new force-directed graph. 

* Maybe an entirely different visualization technique.

**Dependencies**

You will need StdDraw.java from [here](http://introcs.cs.princeton.edu/java/stdlib/).

**References**

[1]  D. Lusseau, K. Schneider, O. J. Boisseau, P. Haase, E. Slooten, and S. M. Dawson, The bottlenose dolphin community of Doubtful Sound features   a large proportion of long-lasting associations, Behavioral Ecology and Sociobiology 54, 396-405 (2003).