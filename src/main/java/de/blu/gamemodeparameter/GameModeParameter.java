package de.blu.gamemodeparameter;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Locale;

public final class GameModeParameter extends JavaPlugin implements Listener {

  @Override
  public void onEnable() {
    Bukkit.getPluginManager().registerEvents(this, this);
  }

  @EventHandler
  public void onCommand(PlayerCommandPreprocessEvent e) {
    Player player = e.getPlayer();
    String message = e.getMessage();

    if (!message.startsWith("/")) {
      return;
    }

    message = message.substring(1);

    String command = message.split(" ")[0];

    if (message.split(" ").length == 1) {
      // No Argument
      return;
    }

    String gameModeParameter = message.split(" ")[1];

    try {
      int gameModeId = Integer.parseInt(gameModeParameter);

      GameMode targetGameMode =
          Arrays.stream(GameMode.values())
              .filter(gameMode -> gameMode.getValue() == gameModeId)
              .findFirst()
              .orElse(null);
      if (targetGameMode == null) {
        return;
      }

      e.setMessage(
          "/"
              + message.replaceFirst(
                  String.valueOf(gameModeId), targetGameMode.name().toLowerCase(Locale.ROOT)));
    } catch (NumberFormatException numberFormatException) {
      return;
    }
  }
}
