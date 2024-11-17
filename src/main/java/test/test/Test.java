package test.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class Test extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("[NotDebug] Hello on.");
    }

    @Override
    public void onDisable() {
        getLogger().info("[NotDebug] Hello off.");
    }

    public void openMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, "Hello Menu!");
        ItemStack diamondSword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta swordMeta = diamondSword.getItemMeta();
        if (swordMeta != null) {
            swordMeta.setDisplayName("Send HELLO!");
            swordMeta.addEnchant(org.bukkit.enchantments.Enchantment.DURABILITY, 1, true);
            diamondSword.setItemMeta(swordMeta);
        }
        inventory.setItem(13, diamondSword);
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        for (int i = 0; i < 27; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, blackGlass);
            }
        }
        player.openInventory(inventory);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("hello")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                openMenu(player);
            } else {
                sender.sendMessage("Only for players.");
            }
            return true;
        }
        return false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Hello menu!")) return;
        event.setCancelled(true);
        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR) return;

        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem().getType() == Material.DIAMOND_SWORD) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "sudo " + player.getName() + " me Hello!");
        }
    }
}