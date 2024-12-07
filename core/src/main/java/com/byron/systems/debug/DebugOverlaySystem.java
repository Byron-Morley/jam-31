package com.byron.systems.debug;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.byron.components.BodyComponent;
import com.byron.components.PositionComponent;
import com.byron.components.StatusComponent;
import com.byron.components.VelocityComponent;
import com.byron.components.player.PlayerComponent;
import com.byron.utils.Messages;

import java.text.DecimalFormat;

public class DebugOverlaySystem extends IteratingSystem {
    DecimalFormat decimalFormat;

    public DebugOverlaySystem() {
        super(Family.all(PlayerComponent.class).get());
        decimalFormat = new DecimalFormat("#.##");
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        PositionComponent positionComponent = entity.getComponent(PositionComponent.class);
        MessageManager.getInstance().dispatchMessage(Messages.PLAYER_POSITION, "Position: " + decimalFormat.format(positionComponent.getPosition().x) + ", " + decimalFormat.format(positionComponent.getPosition().y));

        StatusComponent statusComponent = entity.getComponent(StatusComponent.class);
        MessageManager.getInstance().dispatchMessage(Messages.PLAYER_STATUS, statusComponent.getAction().getName());
        MessageManager.getInstance().dispatchMessage(Messages.PLAYER_DIRECTION, statusComponent.getDirection().getName());

        VelocityComponent velocityComponent = entity.getComponent(VelocityComponent.class);
        MessageManager.getInstance().dispatchMessage(Messages.VELOCITY, "Velocity: " + decimalFormat.format(velocityComponent.velocity.x) + ", " + decimalFormat.format(velocityComponent.velocity.y));
        MessageManager.getInstance().dispatchMessage(Messages.ACCELERATION, "Acceleration: " + decimalFormat.format(velocityComponent.acceleration.x) + ", " + decimalFormat.format(velocityComponent.acceleration.y));

        BodyComponent bodyComponent = entity.getComponent(BodyComponent.class);
        MessageManager.getInstance().dispatchMessage(Messages.BODY_POSITION, "Position: " + decimalFormat.format(bodyComponent.body.position.x) + ", " + decimalFormat.format(bodyComponent.body.position.y));
        MessageManager.getInstance().dispatchMessage(Messages.PREVIOUS_POSITION, "Previous: " + decimalFormat.format(bodyComponent.body.previous.x) + ", " + decimalFormat.format(bodyComponent.body.previous.y));
        MessageManager.getInstance().dispatchMessage(Messages.SPEED, "Speed: " + decimalFormat.format(bodyComponent.body.getSpeed().x) + ", " + decimalFormat.format(bodyComponent.body.getSpeed().y));
    }
}
