package me.DarthChungo.CommandRegex;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.nio.file.Path;

@Plugin( //
    id = "commandregex", //
    name = "CommandRegex", //
    version = "1.0.0", //
    description = "Alias commands using regular expressions", //
    authors = {"DarthChungo"})
public class CommandRegex {
  private CommandManager manager;
  private Logger logger;
  private Path folder;

  @Inject
  public CommandRegex(CommandManager commandManager, Logger logger, @DataDirectory final Path folder) {
    this.manager = commandManager;
    this.logger = logger;
    this.folder = folder;
  }
}
