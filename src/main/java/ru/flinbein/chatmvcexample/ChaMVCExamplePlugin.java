package ru.flinbein.chatmvcexample;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.flinbein.chatmvc.handler.CommandHandler;
import ru.flinbein.chatmvcexample.controller.ExampleInventoryController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ChaMVCExamplePlugin extends JavaPlugin {

    CommandHandler commandHandler;
    private final String mvvmCommandPartition = "cmvvm exec";

    @Override
    public void onEnable() {
        // Plugin startup logic
        commandHandler = new CommandHandler(mvvmCommandPartition);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] texts) {
        if (texts.length > 0 && texts[0].equals("exec")) {
            var subArray = Arrays.copyOfRange(texts, 1, texts.length);
            return commandHandler.handleCommand(sender, subArray);
        }

        commandHandler.registerController(sender, new ExampleInventoryController())
                .render("template/inventory/inventory_example.ftlx");
        return true;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] texts) {
        if (texts.length > 0 && texts[0].equals("exec")) {
            var subArray = Arrays.copyOfRange(texts, 1, texts.length);
            return commandHandler.handleTabComplete(sender, subArray);
        }
        return List.of();
    }


}