package com.OxO.gay;

import co.aikar.commands.PaperCommandManager;
import kr.entree.spigradle.annotations.PluginMain;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServiceRegisterEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Objects;
import com.OxO.gay.listeners.*;

public class Hunger extends JavaPlugin implements Listener {

    @Getter
    @Accessors(fluent = true)
    private static Hunger instance;
    @Getter
    @Setter(AccessLevel.PACKAGE)
    private PaperCommandManager commandManager;

    public Hunger() {
        instance = this;
    }
    public static Hunger getInstance() {
	return instance;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

	new FoodListener(this);
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        getLogger().info("Player joined.");
    }



}
