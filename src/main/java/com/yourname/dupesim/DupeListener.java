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

        // Check if the player is holding a Number Key (like 1) over the item
        if (event.getClick() == ClickType.NUMBER_KEY) {
            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            // Ensure the item exists, isn't air, and hasn't reached max stack size (64)
            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            // Check if the player's inventory is completely full
            if (player.getInventory().firstEmpty() == -1) {
                
                // 1. DUPE IN CHEST: If the stack in the chest is less than max size, grow it
                if (clickedItem.getAmount() < clickedItem.getMaxStackSize()) {
                    clickedItem.setAmount(clickedItem.getAmount() + 1);
                } 
                // 2. DROP ON GROUND: If the chest slot is already a full stack of 64, drop the extras
                else {
                    ItemStack duplicate = clickedItem.clone();
                    duplicate.setAmount(1); // Drop them 1 by 1 as you hold the key
                    player.getWorld().dropItemNaturally(player.getLocation(), duplicate);
                }
                
                // Play the glitch audio/visual feedback
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 0.2f, 1.5f);
                
                // Cancel the default vanilla packet so the client doesn't desync badly
                event.setCancelled(true);
            }
        }
    }
}
