# Dolphins of Doubtful Sound
Contains Java code to make an interactive network-based visualization of a community of dolphins living off Doubtful Sound, New Zealand.

**What it does**

* Dolphin.java is the main class. It defines the Dolphin data type, and accesses a small online database modeling association data amongst a community of 62 dolphins as a graph or a network. 

* The data was put together by Lusseau et al. (2003) [1], and can be found [here](http://networkdata.ics.uci.edu/data/dolphins/).

* It models the data as a network with nodes on a ring. I wanted to try edge-bundling, hence the ring, but as of now it's not been done. Later. 

* Hovering over a certain node will highlight its associations. The size of each node is proportional to its number of associations (or network size).

**Current ring plot**

![CurrentPlot](https://github.com/TusharRakheja/DataVisualization/blob/master/Dolphins%20of%20Doubtful%20Sound/Images/Sample%20Plot.png)

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    *Input order* &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;				*Partially bundled edges*

**To do**

* Edge-bundling in this graph. Fix purple-shade bug. Make it more efficient. 

* A new force-directed graph.

* Maybe an entirely different visualization technique.

**Progress and problems with ForceDirected graph**

For the experiment described below, the distance between the two dolphins is set = 150 m. So the radius of rotation (expected radius anyway) = 75 m.
The *mass* of both the dolphin nodes is 3 kg.

I'm trying to use a straightforward explicit Euler implementation for gravity. Centripetal force on both nodes is also measured. According to the initial configuration, 
the two bodies should follow a circular trajectory, hence the graphs for all three forces should have been uniform. However, there are weird spikes in gravity, and even 
weirder ones in centripetal force. As a result, the two bodies follow elliptical orbits, and keep drifting apart. To see it, uncomment line 15 in TestGravity.java, and comment out line 14.

`//simulate(dolphin) --> simulate(dolphin)` and `plotForces(dolphin) --> //plotForces(dolphin)`

Here's a plot (Note that the black curve is hidden behind the blue curve, which shows that the centripetal forces on both bodies are the same. Some good news).

![CentripetalvsGravity](https://github.com/TusharRakheja/DataVisualization/blob/master/Dolphins%20of%20Doubtful%20Sound/Images/CentripetalvsGravity.png)

**Empirical Fix**: Apparently, if we change the velocity to 0.1725 m/s each instead of 0.1 m/s each, then what happens is exactly what we want, except that the 
centripetal force's magnitude (initially) is 1.19 mN, whereas the gravitational force comes out to be 0.4 mN. Eventually, the gravity remains about 0.4 mN,
but the centripetal force settles down to about 0.6 mN. I've no idea what's going on.

Any suggestions? The plot for that configuration, as well as the corresponding trajectory, is here (don't need the full plot anyway).

![CentripetalvsGravity_Better](https://github.com/TusharRakheja/DataVisualization/blob/master/Dolphins%20of%20Doubtful%20Sound/Images/CentripetalvsGravity_Better.png)

**Dependencies**

You will need StdDraw.java from [here](http://introcs.cs.princeton.edu/java/stdlib/).

**Additional**

* Adding a text box for network size of current node - Uncomment lines 305-306, 317-319, and 324-325 in Dolphin.java

* Writing Network Size vs Frequency(Network Size) data to a file - Uncomment line 304 in Dolphin.java

* Plotting the aforementioned data in R - PlotDistribution.R in src.

**References**

[1]  D. Lusseau, K. Schneider, O. J. Boisseau, P. Haase, E. Slooten, and S. M. Dawson, The bottlenose dolphin community of Doubtful Sound features   a large proportion of long-lasting associations, Behavioral Ecology and Sociobiology 54, 396-405 (2003).