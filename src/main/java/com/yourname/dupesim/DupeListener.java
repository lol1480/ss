package com.yourname.dupesim;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class DupeListener implements Listener {

    @EventHandler
    public void onHotbarSwapDupe(InventoryClickEvent event) {
        // Only trigger if clicking inside a container (Chest, Shulker, etc.)
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().equals(event.getWhoClicked().getInventory())) return;

        // Check if the player is using the Number Key swap (holding 1-9)
        if (event.getClick() == ClickType.NUMBER_KEY) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            // Ensure the item exists and isn't air
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            // Check if the player's inventory is completely full (firstEmpty() returns -1)
            if (player.getInventory().firstEmpty() == -1) {
                
                // Simulate the glitch by cloning the item
                ItemStack duplicate = clickedItem.clone();
                
                // Drop the item at the player's feet
                player.getWorld().dropItemNaturally(player.getLocation(), duplicate);
                
                // Visual/Audio feedback that the "glitch" triggered
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.5f, 1.0f);
            }
        }
    }
}
