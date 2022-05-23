package me.relend.survivalgames.manager;

import org.bukkit.Material;

import java.util.ArrayList;

public class BlockManager {

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
}
