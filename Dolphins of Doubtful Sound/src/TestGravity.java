
public class TestGravity {
    public static void main(String[] args) {
        System.out.println("YO");
        Dolphin.setRadius(270);
        Dolphin.setDelta(42);
        StdDraw.setCanvasSize(800, 670);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 800);
        Dolphin[] dolphin = new Dolphin[2];
        dolphin[0] = new Dolphin(0, "");
        dolphin[1] = new Dolphin(1, "");
        /*Dolphin d1 = new Dolphin(2, ""), d3 = new Dolphin(3, "");
        d1.position(new Vector2D(400, 300));
        d3.position(new Vector2D(400, 450));
        d1.drawNode2(0.01, 0.01);
        d3.drawNode2(0.01, 0.01);*/
        dolphin[0].position(new Vector2D(400 + 75, 375));
        dolphin[1].position(new Vector2D(400 - 75, 375));
        dolphin[0].velocity(new Vector2D(0, -0.1));
        dolphin[1].velocity(new Vector2D(0, 0.1));
        dolphin[0].addToNetwork(dolphin[1]);
        info(dolphin);
        //dolphin[0].drawNode2(0.01, 0.01);
        //dolphin[1].drawNode2(0.01, 0.01);
        
        while (true) {
            if (StdDraw.mousePressed()) {
                break;
            }
            dolphin[0].accel(new Vector2D(0, 0));
            dolphin[1].accel(new Vector2D(0, 0));
            Vector2D newVec = new Vector2D(dolphin[0].position().minus(dolphin[1].position()).unit().
                    times(dolphin[0].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[1], dolphin[0])));
            dolphin[1].accel().add(newVec);
            newVec.reverse();
            dolphin[0].accel().add(newVec);
            dolphin[0].adjust(1);
            dolphin[1].adjust(1);
            dolphin[1].drawNode2(0.01, 0.01);
            dolphin[0].drawNode2(0.01, 0.01);
        }
    }
    public static void info(Dolphin[] dolphin) {
        System.out.println("M1: " + dolphin[0].mass() + "\tM2: " + dolphin[1].mass());
        System.out.println("V1: " + dolphin[0].velocity().lensq() + "\tV2: " + dolphin[1].velocity().lensq());
        System.out.print("4R^2: " + Dolphin.distSquared(dolphin[0], dolphin[1]));
        System.out.print("\t=> R^2: " + Dolphin.distSquared(dolphin[0], dolphin[1])/4);
        System.out.println("\t=> R: " + Dolphin.dist(dolphin[0], dolphin[1])/2);
        System.out.println("M1V1^2/R: " + dolphin[0].mass()*dolphin[0].velocity().lensq()/(Dolphin.dist(dolphin[0], dolphin[1])/2));
        System.out.println("M2V2^2/R: " + dolphin[1].mass()*dolphin[1].velocity().lensq()/(Dolphin.dist(dolphin[1], dolphin[0])/2));
        System.out.println("M1M2/4R^2: " + dolphin[1].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[0], dolphin[1]));
    }
}
