package collidables;

import geometricshapes.Point;

/**
 * class which hold the information of collision.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObject;

    /**
     * constructor that receives collision point abd the collision object.
     *
     * @param collisionPoint  the collision point.
     * @param collisionObject the collision object.
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObject) {
        this.collisionPoint = collisionPoint;
        this.collisionObject = collisionObject;
    }

    /**
     * getter.
     * @return collission point member.
     */
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * getter.
     * @return the collision object member.
     */
    public Collidable collisionObject() {
        return this.collisionObject;
    }
}
