package me.DarthChungo.CommandRegex;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.DarthChungo.CommandRegex.ConfigManager.Config.Alias;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.nio.file.Path;
import java.util.regex.Matcher;

@Plugin( //
    id = "commandregex", //
    name = "CommandRegex", //
    version = BuildConstants.version, //
    description = "Alias commands using regular expressions", //
    authors = {"DarthChungo"}, //
    dependencies = {@Dependency(id = "unsignedvelocity")} //
)
public class CommandRegex {
  public final ProxyServer proxy_server;
  private final ConfigManager config_manager;

  public Logger logger;
  public File datadir;

  @Inject
  public CommandRegex(ProxyServer server, Logger log, @DataDirectory final Path folder) {
    proxy_server = server;
    logger = log;
    datadir = folder.toFile();

    if (!datadir.exists()) {
      datadir.mkdirs();
    }

    config_manager = new ConfigManager(this);
    config_manager.load();
  }

  @Subscribe
  public void onProxyInitialization(ProxyInitializeEvent event) {
    proxy_server.getEventManager().register(this, new CommandExecuteHandler(this));
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
