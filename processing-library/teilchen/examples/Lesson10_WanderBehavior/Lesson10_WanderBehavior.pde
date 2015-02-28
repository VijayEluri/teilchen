import mathematik.*;

import teilchen.BehaviorParticle;
import teilchen.Physics;
import teilchen.behavior.Motor;
import teilchen.behavior.Wander;
import teilchen.force.ViscousDrag;

import static processing.core.PConstants.OPENGL;

/**
 * this sketch shows how to assign an 'wander' behavior to a particle.
 */
Physics mPhysics;

BehaviorParticle mParticle;

Wander mWander;

Motor mMotor;

void setup() {
    size(640, 480, OPENGL);
    smooth();
    frameRate(120);

    /* physics */
    mPhysics = new Physics();
    mPhysics.add(new ViscousDrag());

    /* create particles */
    mParticle = mPhysics.makeParticle(BehaviorParticle.class);
    mParticle.position().set(width / 2, height / 2);
    mParticle.maximumInnerForce(100);
    mParticle.radius(10);

    /* create behavior */
    mWander = new Wander();
    mParticle.behaviors().add(mWander);

    /* a motor is required to push the particle forward - wander manipulats the direction the particle is pushed in */
    mMotor = new Motor();
    mMotor.auto_update_direction(true); /* the direction the motor pushes into is each step automatically set to the velocity */

    mMotor.strength(25);
    mParticle.behaviors().add(mMotor);
}

void draw() {
    /* update particle system */
    mPhysics.step(1.0f / frameRate);

    /* draw behavior particle */
    background(255);

    fill(1);
    stroke(0, 127);
    line(mParticle.position().x,
         mParticle.position().y,
         mParticle.position().x + mParticle.velocity().x,
         mParticle.position().y + mParticle.velocity().y);
    ellipse(mParticle.position().x, mParticle.position().y,
            mParticle.radius() * 2, mParticle.radius() * 2);
}
