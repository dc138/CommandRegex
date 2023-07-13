package me.DarthChungo.CommandRegex;

import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

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

    logger.warn(findAndReplace("/gamemode creative", "^(/g)a(m)emode (c)reative$", "\\1\\2\\3"));
  }

  public String findAndReplace(String input, String accept, String replace) {
    logger.warn("findAndReplace: input=" + input + ", accept=" + accept + ", replace=" + replace);

    Pattern regex = null;

    try {
      regex = Pattern.compile(accept);

    } catch (Exception e) {
      return null;
    }

    Matcher matcher = regex.matcher(input);
    StringBuffer output = new StringBuffer(input);

    int offset = 0;

    while (matcher.find()) {
      String replaced = replace;

      for (int i = 0; i <= matcher.groupCount(); i++) {
        replaced = replaced.replace("\\" + i, matcher.group(i));
      }

      output.replace(matcher.start() + offset, matcher.end() + offset, replaced);
      offset += replaced.length() - matcher.group().length();
    }

    return output.toString();
  }
}
