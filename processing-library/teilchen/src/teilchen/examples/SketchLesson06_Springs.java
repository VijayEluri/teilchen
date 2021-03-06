package teilchen.examples;

import processing.core.PApplet;
import teilchen.Particle;
import teilchen.Physics;
import teilchen.force.Spring;

/**
 * this sketch shows 1 how to create a viscous drag to slow motion eventually
 * down. 2 how to create a spring that connects two particles.
 */
public class SketchLesson06_Springs extends PApplet {

    private Physics mPhysics;

    private Particle mRoot;

    public void settings() {
        size(640, 480, P3D);
    }

    public void setup() {
        smooth();
        frameRate(30);

        /* create a particle system */
        mPhysics = new Physics();

        /* create a particle to which we will connect springs */
        mRoot = mPhysics.makeParticle(width / 2, height / 2, 0.0f);
        /* we give the root particle a higher mass so it doesn t move as easily */
        mRoot.mass(30);
    }

    public void draw() {
        /* create a particle at mouse position and connect it to the root particle through a spring */
        if (mousePressed) {
            Particle mParticle = mPhysics.makeParticle(mouseX, mouseY, 0);
            Spring mSpring = mPhysics.makeSpring(mRoot, mParticle);
            /* restlength defines the desired length of the spring. in this case it is the distance between the two particles. */
            float mRestlength = mSpring.restlength();
            /* we modify the restlength to add a bit of energy into the system */
            mSpring.restlength(mRestlength * 1.5f);
        }

        /* update the particle system */
        final float mDeltaTime = 1.0f / frameRate;
        mPhysics.step(mDeltaTime);

        /* draw particles and connecting line */
        background(255);

        /* draw springs */
        noFill();
        stroke(255, 0, 127, 64);
        for (int i = 0; i < mPhysics.forces().size(); i++) {
            if (mPhysics.forces().get(i) instanceof Spring) {
                Spring mSSpring = (Spring) mPhysics.forces().get(i);
                line(mSSpring.a().position().x, mSSpring.a().position().y,
                     mSSpring.b().position().x, mSSpring.b().position().y);
            }
        }
        /* draw particles */
        fill(245);
        stroke(164);
        for (int i = 0; i < mPhysics.particles().size(); i++) {
            ellipse(mPhysics.particles().get(i).position().x,
                    mPhysics.particles().get(i).position().y,
                    12, 12);
        }
    }

    public static void main(String[] args) {
        PApplet.main(new String[]{SketchLesson06_Springs.class.getName()});
    }
}
