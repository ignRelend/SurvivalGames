package me.relend.survivalgames.manager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class KillManager {

    // <Victim, Attacker>
    private HashMap<Player, Player> kills = new HashMap<>();

    public void addKill(Player attacker, Player victim) {
        kills.put(victim, attacker);
    }

    public ArrayList<Player> getKillsFor(Player player) {
        ArrayList<Player> kill = new ArrayList<>();
        for (Player victim : kills.keySet()) {
            if (kills.get(victim).equals(player)) {
                kill.add(victim);
            }
        }
        return kill;
    }
}
