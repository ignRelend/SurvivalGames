package me.relend.survivalgames.manager;

import org.bukkit.Location;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.HashMap;

public class BlockManager {

    private HashMap<Location, Material> brockenBlocks = new HashMap<>();
    private HashMap<Location, Material> placedBlocks = new HashMap<>();

    public boolean canBreak(Material material) {
        ArrayList<Material> breakable = new ArrayList<>();
        breakable.add(Material.GRASS);

        return breakable.contains(material);
    }

    public boolean canPlace(Material material) {
        ArrayList<Material> placeable = new ArrayList<>();
        placeable.add(Material.CRAFTING_TABLE);

        return placeable.contains(material);
    }

    public HashMap<Location, Material> getBrockenBlocks() {
        return brockenBlocks;
    }

    public HashMap<Location, Material> getPlacedBlocks() {
        return placedBlocks;
    }
}
