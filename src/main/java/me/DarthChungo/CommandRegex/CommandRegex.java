package me.DarthChungo.CommandRegex;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.DarthChungo.CommandRegex.ConfigManager.Config.Alias;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Plugin( //
    id = "commandregex", //
    name = "CommandRegex", //
    version = "1.0.0", //
    description = "Alias commands using regular expressions", //
    authors = {"DarthChungo"})
public class CommandRegex {
  private CommandManager command_manager;
  private ConfigManager config_manager;

  public Logger logger;
  public File datadir;

  @Inject
  public CommandRegex(CommandManager commandManager, Logger log, @DataDirectory final Path folder) {
    command_manager = commandManager;
    logger = log;
    datadir = folder.toFile();

    config_manager = new ConfigManager(this);

    if (!datadir.exists()) {
      datadir.mkdirs();
    }

    config_manager.load();

    logger.warn(processCommand("/gmc"));
  }

  public String processCommand(String input) {
    String result = input;

    for (int i = 0; i < config_manager.config.aliases.size(); i++) {
      result = findAndReplace(result, config_manager.config.aliases.get(i));
    }

    return result;
  }

  public String findAndReplace(String input, Alias alias) {
    Matcher matcher = alias.accept.matcher(input);
    StringBuffer output = new StringBuffer(input);

    int offset = 0;

    while (matcher.find()) {
      String replaced = alias.replace;

      for (int i = 0; i <= matcher.groupCount(); i++) {
        replaced = replaced.replace("\\" + i, matcher.group(i));
      }

      output.replace(matcher.start() + offset, matcher.end() + offset, replaced);
      offset += replaced.length() - matcher.group().length();
    }

    return output.toString();
  }
}
