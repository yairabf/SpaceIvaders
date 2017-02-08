package gameopperating;

import collidables.Collidable;
import collidables.CollisionInfo;
import geometricshapes.Line;
import geometricshapes.Point;

import java.util.ArrayList;

/**
 * class that represents the game environment of an object.
 * it has a collection of the collidables and methods regarding them.
 */
public class GameEnvironment {
    private ArrayList collides;

    /**
     * constructor.
     */
    public GameEnvironment() {
        this.collides = new ArrayList();
    }


    /**
     * add a given collidable to the environment.
     *
     * @param c the collidable we are adding.
     */
    public void addCollidable(Collidable c) {
        collides.add(c);
    }

    /**
     * removes a collidable from the collodes list.
     *
     * @param c the collidable to be removed.
     */
    public void removeCollidable(Collidable c) {
        this.collides.remove(c);
    }

    /**
     * the method returns the closest collision point that the object has with a given line.
     *
     * @param trajectory the movement line of the object.
     * @return the collision point.
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        //set the closest collision to be the end of the trajectory.
        Point closestCollision = trajectory.end();
        //initiate the collidable object.
        Collidable object = null;
        //will hold the distance between the location of the ball and the end of it's trajectory.
        double shortDistance = Math.abs(trajectory.start().distance(closestCollision));
        /*runs through the collides and checks each one of them if its closest the ball.*/
        for (Object collide : collides) {
            //initiate the object to hold the collide.
            Collidable currentObject = (Collidable) collide;
            //calculate if the current collide is intersect with the trajectory.
            Point collision = trajectory.closestIntersectionToStartOfLine(currentObject.getCollisionRectangle());
            /*if the collide intersect with the ball trajectory check if its the closest collideable to the ball.*/
            if (collision != null) {
                //calculate the distance between the ball and the current collideable.
                double currentDistance = Math.abs(trajectory.start().distance(collision));
                /*if the distance between the ball and the current collision point is shorter than the previous one
                * update if to be the closest.*/
                if (currentDistance <= shortDistance) {
                    shortDistance = currentDistance;
                    closestCollision = collision;
                    object = currentObject;
                }
            }
        }
        /*if the closest collision is the end of the ball trajectory it means that the ball didn't hit any.*/
        if (closestCollision.equals(trajectory.end()) && object == null) {
            return null;
        }
        return new CollisionInfo(closestCollision, object);
    }
}
