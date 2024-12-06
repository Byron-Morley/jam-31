package com.mygdx.game.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.Color;
import com.mygdx.game.components.*;
import com.mygdx.game.components.player.PlayerComponent;
import com.mygdx.game.dto.physics.Fixture;
import com.mygdx.game.model.status.Action;
import com.mygdx.game.utils.Mappers;
import com.mygdx.game.utils.collisions.CollisionSimulation;
import com.mygdx.game.utils.collisions.interfaces.Collider;
import com.mygdx.game.utils.collisions.interfaces.RectangleCollider;
import com.mygdx.game.utils.shape.RectangleShape;
import com.mygdx.game.utils.shape.Shape;

import static com.mygdx.game.utils.Config.GRAVITY;
import static com.mygdx.game.utils.collisions.Collisions.*;

public class CollisionSystem extends EntitySystem {
    private ComponentMapper<PositionComponent> pm = Mappers.position;
    private ComponentMapper<BodyComponent> bm = Mappers.body;
    private ComponentMapper<PlayerComponent> playm = Mappers.player;
    private ComponentMapper<StatusComponent> sm = Mappers.status;
    private ComponentMapper<ShapeComponent> shapeMapper = Mappers.shape;
    private ComponentMapper<VelocityComponent> velocity = Mappers.velocity;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> colliders;
    private ImmutableArray<Entity> rainbowColliders;
    private boolean collisions;

    @Override
    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class, BodyComponent.class, PositionComponent.class, StatusComponent.class).get());
        colliders = engine.getEntitiesFor(Family.all(PlatformComponent.class, PositionComponent.class, BodyComponent.class).get());
        rainbowColliders = engine.getEntitiesFor(Family.all(RainbowComponent.class, PositionComponent.class, BodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        collisions = false;
//        checkBoundaryCollisions(deltaTime);
        checkStandardCollisions(deltaTime);

    }

    private void checkBoundaryCollisions(float deltaTime) {
        for (Entity player : players) {
            VelocityComponent playerVelocity = velocity.get(player);
            BodyComponent playerBody = bm.get(player);

        }
    }


//    private void checkRainbowCollisions() {
//        for (Entity player : players) {
//            BodyComponent playerBody = bm.get(player);
//            StatusComponent status = sm.get(player);
//
//            for (Entity collider : rainbowColliders) {
//                BodyComponent colliderBody = bm.get(collider);
//                ShapeComponent colliderShape = shapeMapper.get(collider);
//                VelocityComponent playerVelocity = player.getComponent(VelocityComponent.class);
//                Fixture hitbox = playerBody.body.getFixture("hitbox");
//
//                if (colliderShape.getShape() instanceof CurveShape) {
//
//                    CurveShape shape = (CurveShape) colliderShape.getShape();
//
//                    for (int i = 0; i <= shape.getSegmentCount(); ++i) {
//                        Line line = shape.calculateLine(i);
//                        if (lineRect(line.getX(), line.getY(), line.getX2(), line.getY2(), hitbox.getX() + hitbox.getWidth() / 2, hitbox.getY(), 0.1f, hitbox.getHeight())) {
//                            playerVelocity.velocity.y = 0;
//                            Vector2 intersect = intersectPoint(line.getX(), line.getY(), line.getX2(), line.getY2(), hitbox.getX() + hitbox.getWidth(), hitbox.getY(), hitbox.getX() + hitbox.getWidth(), hitbox.getY() + hitbox.getHeight());
//                            playerBody.body.position.y = intersect.y;
//                            status.setAction(Action.STANDING);
//                        }
//                    }
//                }
//            }
//        }
//    }
//

    private void checkStandardCollisions(float deltaTime) {
        for (Entity player : players) {
            BodyComponent playerBody = bm.get(player);
            StatusComponent status = sm.get(player);
            VelocityComponent playerVelocity = velocity.get(player);
            Shape hitbox = playerBody.body.getFixture("HITBOX").getShape();

            for (Entity collider : colliders) {
                BodyComponent colliderBody = bm.get(collider);
                Shape shape = colliderBody.body.getFixture("STATIC").getShape();

                CollisionSimulation collisionSimulation = new CollisionSimulation((Collider) hitbox.withVelocity(playerVelocity.velocity), (Collider) shape);

                if (collisionSimulation.collide(deltaTime)) {
                    collisions = true;
//                    playerBody.body.position.y = colliderBody.body.position.y + colliderBody.body.height;

                    RectangleShape rect = (RectangleShape) hitbox;


//                    if (bottomCollision((RectangleCollider) hitbox,(RectangleCollider) shape)) {
//                        playerBody.body.position.y = colliderBody.body.position.y + colliderBody.body.height +1;
//                        playerVelocity.velocity.y = 0;
//                        playerVelocity.acceleration.y = 0;
//
//                        if (status.getAction().equals(Action.JUMPING) || status.getAction().equals(Action.FALLING)) {
//                            status.setAction(Action.STANDING);
//                        }
//
//                    } else if (topCollision((RectangleCollider) hitbox,(RectangleCollider) shape)) {
//                        playerBody.body.position.y = colliderBody.body.position.y - rect.getHeight();
//                        playerVelocity.velocity.y = 0;
//                        playerVelocity.acceleration.y = 0;
//
//                    } else if (leftCollision((RectangleCollider) hitbox,(RectangleCollider) shape)) {
//                        playerBody.body.position.x = colliderBody.body.position.x + colliderBody.body.width - (playerBody.body.width - rect.getWidth()) / 2;
//                        playerVelocity.velocity.x = 0;
//                        playerVelocity.acceleration.x = 0;
//
//                    } else if (rightCollision((RectangleCollider) hitbox,(RectangleCollider) shape)) {
//                        playerBody.body.position.x = colliderBody.body.position.x - (playerBody.body.width + rect.getWidth()) / 2;
//                        playerVelocity.velocity.x = 0;
//                        playerVelocity.acceleration.x = 0;
//                    }
                }
            }
        }
    }


//    private void checkStandardCollisions() {
//        for (Entity player : players) {
//            BodyComponent playerBody = bm.get(player);
//            StatusComponent status = sm.get(player);
//            VelocityComponent playerVelocity = player.getComponent(VelocityComponent.class);
//            Fixture hitbox = playerBody.body.getFixture("hitbox");
//
//            boolean collisions = false;
//
//            for (Entity collider : colliders) {
//                BodyComponent colliderBody = bm.get(collider);
//
//
//                //COLLISION
//                if (collision(hitbox, colliderBody.body)
//                ) {
//                    collisions = true;
//                    //BOTTOM
//                    if (bottomCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.y = colliderBody.body.position.y + colliderBody.body.height;
//                        playerVelocity.velocity.y = 0;
//                        playerVelocity.acceleration.y = 0;
//
//                        if (status.getAction().equals(Action.JUMPING) || status.getAction().equals(Action.FALLING)) {
//                            status.setAction(Action.STANDING);
//                        }
//
//                        //TOP
//                    } else if (topCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.y = colliderBody.body.position.y - hitbox.getHeight();
//                        playerVelocity.velocity.y = 0;
//                        playerVelocity.acceleration.y = 0;
//
//                        //LEFT
//                    } else if (leftCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.x = colliderBody.body.position.x + colliderBody.body.width - (playerBody.body.width - hitbox.getWidth()) / 2;
//                        playerVelocity.velocity.x = 0;
//                        playerVelocity.acceleration.x = 0;
//
//                        //RIGHT
//                    } else if (rightCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.x = colliderBody.body.position.x - (playerBody.body.width + hitbox.getWidth()) / 2;
//                        playerVelocity.velocity.x = 0;
//                        playerVelocity.acceleration.x = 0;
//                    }
//                }
//
//                if (!(playerBody.body.getBottom() <= colliderBody.body.getTop() &&
//                        playerBody.body.previous.x > colliderBody.body.getTop())) {
//                }
//            }
//
//            if (!(collisions)) {
//                if (status.getAction() != Action.JUMPING) {
//                    playerVelocity.acceleration.y = GRAVITY;
//                }
//            }
//
//        }
//    }
}
