package com.byron.factories;

import com.badlogic.gdx.Gdx;
import com.byron.models.SoundAsset;
import com.byron.models.Agent;
import com.byron.models.Spawn;
import com.byron.models.item.Item;
import com.byron.models.sprite.RawAnimationModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;

public class ModelFactory {
    private static final String AGENTS_MODEL_PATH = "model/entities/agents.json";
    private static final String ITEMS_MODEL_PATH = "model/entities/items.json";
    private static final String ANIMATIONS_MODEL_PATH = "model/entities/animations.json";
    private static final String SOUNDS_MODEL_PATH = "model/sound/sounds.json";
    private static final String ATLAS_MODEL_PATH = "model/textures/atlas.json";
    private static final String DUNGEON_SPAWNS_PATH = "model/gen/spawns.json";


    private static Map<String, Object> cache = new HashMap<>();
    private static ObjectMapper mapper = new ObjectMapper();

    public static List<Spawn> getDungeonSpawns() {
        TypeReference<List<Spawn>> typeRef = new TypeReference<>() {
        };
        return (List<Spawn>) readModel(DUNGEON_SPAWNS_PATH, typeRef);
    }

    public static Set<String> getAtlasSpriteModels() {
        TypeReference<HashSet<String>> typeRef = new TypeReference<>() {
        };
        return (Set<String>) readModel(ATLAS_MODEL_PATH, typeRef);
    }

    public static Map<String, Agent> getAgentsModel() {
        TypeReference<HashMap<String, Agent>> typeRef = new TypeReference<>() {
        };
        return (Map<String, Agent>) readModel(AGENTS_MODEL_PATH, typeRef);
    }

    public static Map<String, Item> getItemsModel() {
        TypeReference<HashMap<String, Item>> typeRef = new TypeReference<>() {
        };
        return (Map<String, Item>) readModel(ITEMS_MODEL_PATH, typeRef);
    }

    public static Map<String, SoundAsset> getSoundsModel() {
        TypeReference<HashMap<String, SoundAsset>> typeRef =
            new TypeReference<>() {
            };
        return (Map<String, SoundAsset>) readModel(SOUNDS_MODEL_PATH, typeRef);
    }

    public static Map<String, RawAnimationModel> getAnimationsModels() {
        TypeReference<HashMap<String, RawAnimationModel>> typeRef =
            new TypeReference<>() {
            };

        return (Map<String, RawAnimationModel>) readModel(ANIMATIONS_MODEL_PATH, typeRef);
    }

    private static Object readModel(String modelPath, TypeReference typeRef) {
        if (!cache.containsKey(modelPath)) {
            try {
                Object o = mapper.readValue(Gdx.files.internal(modelPath).file(), typeRef);
                cache.put(modelPath, o);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return cache.get(modelPath);
    }

    public void dump() {
        cache.clear();
    }
}
