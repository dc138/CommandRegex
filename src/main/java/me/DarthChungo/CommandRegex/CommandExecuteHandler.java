package me.DarthChungo.CommandRegex;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.command.CommandExecuteEvent;
import com.velocitypowered.api.event.command.CommandExecuteEvent.CommandResult;

public class CommandExecuteHandler {
  private final CommandRegex plugin;

  CommandExecuteHandler(CommandRegex p) {
    plugin = p;
  }

  @Subscribe(order = PostOrder.NORMAL)
  public void onCommandExecute(CommandExecuteEvent event) {
    String replaced_command = plugin.processCommand(event.getCommand());

    if (plugin.proxy_server.getCommandManager().hasCommand(replaced_command)) {
      event.setResult(CommandResult.command(plugin.processCommand(replaced_command)));

    } else {
      event.setResult(CommandResult.command(plugin.processCommand(replaced_command)));
    }
  }
}
