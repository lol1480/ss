package com.yourname.dupesim;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new DupeListener(), this);
        getLogger().info("ClassicDupeSim enabled! Use the hotbar-swap method in chests with a full inventory.");
    }

    @Override
    public void onDisable() {
        getLogger().info("ClassicDupeSim disabled.");
    }
}
