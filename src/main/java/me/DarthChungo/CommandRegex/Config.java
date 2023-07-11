package me.DarthChungo.CommandRegex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import com.moandjiezana.toml.Toml;

public class Config {
  private final CommandRegex plugin;
  private File file;

  public ConfigClass config;

  Config(CommandRegex p) {
    plugin = p;
    file = new File(plugin.datadir, "config.toml");
  }

  void load() {
    if (!file.exists()) {
      try {
        InputStream stream = plugin.getClass().getResourceAsStream("/" + file.getName());
        Files.copy(stream, file.toPath());

      } catch (IOException e) {
        plugin.logger.error("Error loading config: " + e);
        plugin.logger.error(e.getStackTrace().toString());
      }
    }

    config = new Toml().read(file).to(ConfigClass.class);
  }
}
