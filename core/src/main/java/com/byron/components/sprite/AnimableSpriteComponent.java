package com.byron.components.sprite;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.byron.models.status.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AnimableSpriteComponent implements Component {
    private List<Map<Status, Animation>> texturesToAnimations;
    public float stateTime = 0f;

    public AnimableSpriteComponent() {
        this.texturesToAnimations = new ArrayList();
    }

    public List<Map<Status, Animation>> getTexturesToAnimations() {
        return texturesToAnimations;
    }

    public void setTexturesToAnimations(List<Map<Status, Animation>> texturesToAnimations) {
        this.texturesToAnimations = texturesToAnimations;
    }
}
