
public class TestGravity {
    public static void main(String[] args) {
        Dolphin[] dolphin = new Dolphin[2];
        dolphin[0] = new Dolphin(0, "");
        dolphin[1] = new Dolphin(1, "");
        dolphin[0].position(new Vector2D(400 + 75, 375));
        dolphin[1].position(new Vector2D(400 - 75, 375));
        dolphin[0].velocity(new Vector2D(0, -0.1));
        dolphin[1].velocity(new Vector2D(0, 0.1));
        dolphin[0].addToNetwork(dolphin[1]); 
        info(dolphin);   
        //graph();
        simulate(dolphin);
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
    public static void simulate(Dolphin[] dolphin) {
        double centriMax1 = 0, centriMin1 = Double.MAX_VALUE;
        double graviMax = 0, graviMin = Double.MAX_VALUE;
        double centriMax2 = 0, centriMin2 = Double.MAX_VALUE;
        Dolphin.setRadius(270);
        Dolphin.setDelta(42);
        StdDraw.setCanvasSize(800, 670);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 670);
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
            double gForce = dolphin[0].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[1],dolphin[0]);
            double cForce1 = dolphin[0].mass()*dolphin[0].velocity().lensq()/(Dolphin.dist(dolphin[1], dolphin[0]));
            double cForce2 = dolphin[1].mass()*dolphin[1].velocity().lensq()/(Dolphin.dist(dolphin[1], dolphin[0]));
            if (gForce > graviMax) {
                graviMax = gForce;
            }
            if (gForce < graviMin) {
                graviMin = gForce;
            }
            if (cForce1 > centriMax1) {
                centriMax1 = cForce1;
            }
            if (cForce1 < centriMin1) {
                centriMin1 = cForce1;
            }
            if (cForce2 < centriMin2) {
                centriMin2 = cForce2;
            }
            if (cForce2 > centriMax2) {
                centriMax2 = cForce2;
            }
        }
        System.out.println("Max Gravity: " + graviMax + "\tMin Gravity: " + graviMin);
        System.out.println("Max Centri1: " + centriMax1+"\tMin Centri1: " + centriMin1);
        System.out.println("Max Centri2: " + centriMax2+"\tMin Centri2: " + centriMin2);
    }
    public static void graph() {
        StdDraw.setCanvasSize(800, 670);
        StdDraw.setXscale(0, 800);
        StdDraw.setYscale(0, 670);
        StdDraw.line(30, 0, 30, 670);
        StdDraw.line(0, 30, 800, 30);
        StdDraw.text(18, 15, "0");
    }
    public static void graphQuantity(double timeShift, double q1, double q2) {
    }
}
