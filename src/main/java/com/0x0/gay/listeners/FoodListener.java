package com.OxO.gay.listeners;
import org.bukkit.event.Listener;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.CommandHelp;
import co.aikar.commands.MessageType;
import co.aikar.commands.annotation.*;
import co.aikar.locales.MessageKey;
import org.bukkit.Statistic;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Arrays;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.event.block.Action;
import org.bukkit.util.Vector;
import org.bukkit.entity.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.RayTraceResult;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.FluidCollisionMode;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.*;
import org.bukkit.inventory.EquipmentSlot;
import java.util.HashMap;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.event.player.*;
import org.bukkit.Sound;
public class FoodListener implements Listener {
    static int tick = 0;
    HashMap<String, Long> startTimes = new HashMap<String, Long>();
    HashMap<String, Long> tickTimes = new HashMap<String, Long>();
    HashMap<Material, Integer> foodvalues = new HashMap<Material, Integer>(); 
    public FoodListener(JavaPlugin instance) {
	instance.getServer().getPluginManager().registerEvents(this, instance);

	instance.getServer().getScheduler().scheduleSyncRepeatingTask(instance, new Runnable(){
		
		@Override
		public void run()
		{
		    tick++;
		}
	    }, 0, 1);
	foodvalues.put(Material.APPLE, 4);
	foodvalues.put(Material.BAKED_POTATO, 5);
	foodvalues.put(Material.BEETROOT, 1);
	foodvalues.put(Material.BEETROOT_SOUP, 6);
	foodvalues.put(Material.BREAD, 5);
	foodvalues.put(Material.CARROT, 3);
	foodvalues.put(Material.COOKED_CHICKEN, 6);
	foodvalues.put(Material.COOKED_COD, 4);
	foodvalues.put(Material.COOKED_MUTTON, 6);
	foodvalues.put(Material.COOKED_PORKCHOP, 8);
	foodvalues.put(Material.COOKED_RABBIT, 5);
	foodvalues.put(Material.COOKED_SALMON, 6);
	foodvalues.put(Material.COOKIE, 2);
	foodvalues.put(Material.GLOW_BERRIES, 2);
	foodvalues.put(Material.GOLDEN_CARROT, 6);
	foodvalues.put(Material.HONEY_BOTTLE, 4);
	foodvalues.put(Material.MELON_SLICE, 2);
	foodvalues.put(Material.MUSHROOM_STEW, 6);
	foodvalues.put(Material.POTATO, 1);
	foodvalues.put(Material.PUFFERFISH, 1);
	foodvalues.put(Material.PUMPKIN_PIE, 8);
	foodvalues.put(Material.RABBIT_STEW, 10);
	foodvalues.put(Material.BEEF, 3);
	foodvalues.put(Material.CHICKEN, 2);
	foodvalues.put(Material.COD, 2);
	foodvalues.put(Material.MUTTON, 2);
	foodvalues.put(Material.PORKCHOP, 3);
	foodvalues.put(Material.RABBIT, 3);
	foodvalues.put(Material.SALMON, 2);
	foodvalues.put(Material.ROTTEN_FLESH, 4);
	foodvalues.put(Material.COOKED_BEEF, 8);
	foodvalues.put(Material.SWEET_BERRIES, 4);
	foodvalues.put(Material.TROPICAL_FISH, 4);

    }
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
	// Only listen to right clicks
	if(event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.RIGHT_CLICK_AIR) {
	    return;
	}
	// Only check the item in main hand
	if(event.getHand() != EquipmentSlot.HAND) {
	    return;
	}
	if(event.getItem() == null || !event.getItem().getType().isEdible()) {
	    return;
	}
	    // and cancel the event so that the item cannot really be used / placed
	// Check if player is in the timeframe

        Player p =  event.getPlayer();
	if(tickTimes.get(event.getPlayer().getName()) != null) {
	    if(Math.abs(tickTimes.get(event.getPlayer().getName()) - tick) > 4)
		startTimes.put(event.getPlayer().getName(), System.currentTimeMillis());
	    if(Math.abs(startTimes.get(event.getPlayer().getName()) - System.currentTimeMillis()) > 1106) {
		startTimes.put(event.getPlayer().getName(), System.currentTimeMillis());
		if(foodvalues.get(event.getItem().getType()) != null) {
		    p.setSaturation((int)(p.getSaturation() + 1D + (foodvalues.get(event.getItem().getType()) / 1.5)));
		    event.getItem().setAmount(event.getItem().getAmount() - 1);
		    p.updateInventory();
		}
	    }
		
	} else
	    startTimes.put(event.getPlayer().getName(), System.currentTimeMillis());
	
	if(p.getFoodLevel() == 20 && p.getSaturation() != 20) {
 	    event.setCancelled(true);
	    event.setUseItemInHand(Event.Result.DENY);
	    p.getWorld().spawnParticle(Particle.ITEM_CRACK, p.getEyeLocation(), 3, 0, 0, 0, 0.5, event.getItem());
	    p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EAT, (float)((float)0.5F + 0.5F * (float)(Math.random() * 3)), (float)((Math.random() - Math.random()) * 0.2F + 1.0F));
	    
	}
	tickTimes.put(event.getPlayer().getName(), (long)tick);
    }
	
    
}
