package com.byron.systems;

import com.badlogic.ashley.core.*;
import com.badlogic.ashley.utils.ImmutableArray;
import com.byron.components.*;
import com.byron.components.player.PlayerComponent;
import com.byron.models.physics.Fixture;
import com.byron.models.status.Action;
import com.byron.utils.Mappers;

import static com.byron.utils.Collisions.*;


public class CollisionSystem extends EntitySystem {
    private ComponentMapper<PositionComponent> pm = Mappers.position;
    private ComponentMapper<BodyComponent> bm = Mappers.body;
    private ComponentMapper<PlayerComponent> playm = Mappers.player;
    private ComponentMapper<StatusComponent> sm = Mappers.status;
    private ComponentMapper<ShapeComponent> shapeMapper = Mappers.shape;
    private ImmutableArray<Entity> players;
    private ImmutableArray<Entity> colliders;
    private ImmutableArray<Entity> rainbowColliders;

    private float CLOUD = 0.0f;

    @Override
    public void addedToEngine(Engine engine) {
        players = engine.getEntitiesFor(Family.all(PlayerComponent.class, BodyComponent.class, PositionComponent.class, StatusComponent.class).get());
        colliders = engine.getEntitiesFor(Family.all(PlatformComponent.class, PositionComponent.class, BodyComponent.class).get());
    }

    @Override
    public void update(float deltaTime) {
        checkStandardCollisions();
    }



    private void checkStandardCollisions() {
//        for (Entity player : players) {
//            BodyComponent playerBody = bm.get(player);
//            StatusComponent status = sm.get(player);
//
//            for (Entity collider : colliders) {
//                BodyComponent colliderBody = bm.get(collider);
//                VelocityComponent playerVelocity = player.getComponent(VelocityComponent.class);
//                Fixture hitbox = playerBody.body.getFixture("HITBOX");
//
//                //COLLISION
//                if (collision(hitbox, colliderBody.body)
//                ) {
//
//                    //BOTTOM
//                    if (bottomCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.y = colliderBody.body.position.y + colliderBody.body.height;
//                        playerVelocity.velocity.y = 0;
//
//                        if (status.getAction().equals(Action.JUMPING) || status.getAction().equals(Action.FALLING)) {
//                            status.setAction(Action.STANDING);
//                        }
//
//                        //TOP
//                    } else if (topCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.y = colliderBody.body.position.y - hitbox.getHeight();
//                        playerVelocity.velocity.y = 0;
//
//                        //LEFT
//                    } else if (leftCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.x = colliderBody.body.position.x + colliderBody.body.width - (playerBody.body.width - hitbox.getWidth()) / 2;
//                        playerVelocity.velocity.x = 0;
//
//                        //RIGHT
//                    } else if (rightCollision(hitbox, colliderBody.body)) {
//                        playerBody.body.position.x = colliderBody.body.position.x - (playerBody.body.width + hitbox.getWidth()) / 2;
//                        playerVelocity.velocity.x = 0;
//                    }
//                }
//
//                if (!(playerBody.body.getBottom() <= colliderBody.body.getTop() &&
//                        playerBody.body.previous.x > colliderBody.body.getTop())) {
//                }
//            }
//        }
    }
}
