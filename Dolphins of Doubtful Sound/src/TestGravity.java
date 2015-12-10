import java.awt.Font;

public class TestGravity {
    public static void main(String[] args) {
        Dolphin[] dolphin = new Dolphin[2];

        dolphin[0] = new Dolphin(0, "Alpha");
        dolphin[1] = new Dolphin(1, "Beta");

        dolphin[0].position(new Vector2D(400 + 75, 375));
        dolphin[1].position(new Vector2D(400 - 75, 375));

        dolphin[0].velocity(new Vector2D(0, -0.1));
        dolphin[1].velocity(new Vector2D(0, 0.1));

        dolphin[0].addToNetwork(dolphin[1]);

        info(dolphin);
        //plotForces(dolphin);
        simulate(dolphin);
    }
    public static void info(Dolphin[] dolphin) {
        System.out.println("M1: " + dolphin[0].mass() + "\tM2: " + dolphin[1].mass());
        System.out.println("V1: " + dolphin[0].velocity().lensq() + "\tV2: " + dolphin[1].velocity().lensq());
        System.out.print("4R^2: " + Dolphin.distSquared(dolphin[0], dolphin[1]));
        System.out.print("\t=> R^2: " + Dolphin.distSquared(dolphin[0], dolphin[1])/4);
        System.out.println("\t=> R: " + Dolphin.dist(dolphin[0], dolphin[1])/2);
        System.out.println("Centripetal 1 (M1V1^2/R): " + dolphin[0].mass()*dolphin[0].velocity().lensq()/(Dolphin.dist(dolphin[0], dolphin[1])/2));
        System.out.println("Centripetal 2 (M2V2^2/R): " + dolphin[1].mass()*dolphin[1].velocity().lensq()/(Dolphin.dist(dolphin[1], dolphin[0])/2));
        System.out.println("Gravity (M1M2/4R^2): " + dolphin[1].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[0], dolphin[1]));
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

            Vector2D f = new Vector2D(dolphin[0].position().minus(dolphin[1].position()).unit().
                    times(dolphin[0].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[1], dolphin[0])));
            //accel = f / m
            dolphin[1].accel().add(f.invtimes(dolphin[1].mass()));
            f.reverse();
            dolphin[0].accel().add(f.invtimes(dolphin[0].mass()));

            dolphin[0].adjust(1); //Step size controls accuracy.
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
            //dolphin[1].eraseNode2(0.02, 0.02);
            //dolphin[0].eraseNode2(0.02, 0.02);
        }
        System.out.println("Max Gravity: " + graviMax + "\tMin Gravity: " + graviMin);
        System.out.println("Max Centri1: " + centriMax1+"\tMin Centri1: " + centriMin1);
        System.out.println("Max Centri2: " + centriMax2+"\tMin Centri2: " + centriMin2);
    }
    public static void plotForces(Dolphin[] dolphin) {
        /**
         * Display and other constants.
         */
        final int yUp = 680, xUp = 800;
        final int xOffset = 60, yOffset = 60;
        final int fontSize = 9;
        final int legendYOffset = 20;
        final int legendXOffset = 50;
        final double st = 0, et = 8000;
        final double minForce = 0.0;
        final double maxForce = 0.025;
        final double stepSize = 1.0;

        StdDraw.setCanvasSize(xUp, yUp);
        StdDraw.setXscale(0, xUp);
        StdDraw.setYscale(0, yUp);
        StdDraw.line(xOffset, 0, xOffset, yUp);               // Y axis.
        StdDraw.line(0, yOffset, xUp, yOffset);               // X axis.
        Font f = new Font("Sans-Serif", Font.PLAIN, fontSize);
        StdDraw.text(xOffset - 2*fontSize, yOffset - 2*fontSize, "0");
        StdDraw.setFont(f);
        for (int i = xOffset + 50; i <= xUp; i += 50) {
            double time = ((((i - xOffset)*(et - st))/(xUp - xOffset)) + st);
            int Time = (int) time;
            StdDraw.text(i, yOffset - 2*fontSize, Double.toString(Time));
        }
        for (int j = yOffset + 25; j <= yUp; j += 25) {
            double force = ((((j - yOffset)*(maxForce - minForce))/(yUp - yOffset)) + minForce)*1000;
            int Force = (int) force;
            StdDraw.text(xOffset - 2*fontSize, j, Double.toString(Force));
        }
        StdDraw.setFont(new Font("Sans-Serif", Font.PLAIN, (int)(1.5*fontSize)));
        StdDraw.text((xUp + xOffset)/2, yOffset - 4.5*fontSize, "Time(s) ->");
        StdDraw.text(xOffset - 4.5*fontSize, (yUp + yOffset)/2, "Force(mN) ->", 90);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.line(xUp - 3*legendXOffset, yUp - legendYOffset, xUp - 2*legendXOffset, yUp - legendYOffset);
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.line(xUp - 3*legendXOffset, yUp - 2*legendYOffset, xUp - 2*legendXOffset, yUp - 2*legendYOffset);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(xUp - 3*legendXOffset, yUp - 3*legendYOffset, xUp - 2*legendXOffset, yUp - 3*legendYOffset);
        StdDraw.text(xUp - legendXOffset, yUp - legendYOffset, "Gravity");
        StdDraw.text(xUp - legendXOffset, yUp - 2*legendYOffset, "Centripetal 1");
        StdDraw.text(xUp - legendXOffset, yUp - 3*legendYOffset, "Centripetal 2");
        double t = st;
        while (t <= et) {
            if (StdDraw.mousePressed()) {
                break;
            }
            double gForce = dolphin[0].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[1],dolphin[0]);
            double cForce1 = dolphin[0].mass()*dolphin[0].velocity().lensq()/(Dolphin.dist(dolphin[1], dolphin[0]));
            double cForce2 = dolphin[1].mass()*dolphin[1].velocity().lensq()/(Dolphin.dist(dolphin[1], dolphin[0]));
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle((xUp - xOffset)*((t - st)/(et - st)) + xOffset, ((yUp - yOffset)/(maxForce - minForce))*(gForce - minForce) + yOffset, 0.002);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.filledCircle((xUp - xOffset)*((t - st)/(et - st)) + xOffset, ((yUp - yOffset)/(maxForce - minForce))*(cForce1 - minForce) + yOffset, 0.002);
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.filledCircle((xUp - xOffset)*((t - st)/(et - st)) + xOffset, ((yUp - yOffset)/(maxForce - minForce))*(cForce2 - minForce) + yOffset, 0.002);

            dolphin[0].accel(new Vector2D(0, 0));
            dolphin[1].accel(new Vector2D(0, 0));

            Vector2D g = new Vector2D(dolphin[0].position().minus(dolphin[1].position()).unit().
                    times(dolphin[0].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[1], dolphin[0])));

            dolphin[1].accel().add(g);
            g.reverse();
            dolphin[0].accel().add(g);

            dolphin[0].adjust(stepSize);
            dolphin[1].adjust(stepSize);

            t += stepSize;
            //dolphin[1].drawNode2(0.01, 0.01);
            //dolphin[0].drawNode2(0.01, 0.01);
        }
        System.out.println("Gravity eventual: " + dolphin[0].mass()*dolphin[1].mass()/Dolphin.distSquared(dolphin[1], dolphin[0]));
        System.out.print("Centripetal eventual: " + dolphin[0].mass()*dolphin[0].velocity().lensq()/Dolphin.dist(dolphin[1], dolphin[0]));
        System.out.print(" | " + dolphin[1].mass()*dolphin[1].velocity().lensq()/Dolphin.dist(dolphin[1], dolphin[0]));
    }
}
