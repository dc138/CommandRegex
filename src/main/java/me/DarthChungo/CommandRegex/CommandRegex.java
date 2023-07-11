package me.DarthChungo.CommandRegex;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;

@Plugin( //
    id = "commandregex", //
    name = "CommandRegex", //
    version = "1.0.0", //
    description = "Alias commands using regular expressions", //
    authors = {"DarthChungo"})
public class CommandRegex {
  private CommandManager manager;
  private Config config;

  public Logger logger;
  public File datadir;

  @Inject
  public CommandRegex(CommandManager commandManager, Logger log, @DataDirectory final Path folder) {
    manager = commandManager;
    logger = log;
    datadir = folder.toFile();

    config = new Config(this);

    if (!datadir.exists()) {
      datadir.mkdirs();
    }

    config.load();
  }
}
