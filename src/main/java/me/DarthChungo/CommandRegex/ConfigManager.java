package me.DarthChungo.CommandRegex;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.moandjiezana.toml.Toml;
import me.DarthChungo.CommandRegex.ConfigManager.Config.Alias;

public class ConfigManager {

  static public class Config {
    static public class Alias {
      public final Pattern accept;
      public final String replace;

      public Alias(Pattern a, String r) {
        accept = a;
        replace = r;
      }
    }

    public final List<Alias> aliases = new ArrayList<>();
  }

  public final Config config = new Config();

  private final CommandRegex plugin;
  private final File file;

  ConfigManager(CommandRegex p) {
    plugin = p;
    file = new File(plugin.datadir, "config.toml");
  }

  public void load() {
    if (!file.exists()) {
      try {
        InputStream stream = plugin.getClass().getResourceAsStream("/" + file.getName());
        Files.copy(stream, file.toPath());

      } catch (Exception e) {
        plugin.logger.error("Error loading config: " + e);
        plugin.logger.error(e.getStackTrace().toString());
      }
    }

    (new Toml()).read(file).to(FileData.class).aliases.stream() //
        .filter(element -> {
          return element.accept != null && element.replace != null;
        }) //
        .forEach(element -> {
          try {
            Pattern regex = Pattern.compile(element.accept);
            config.aliases.add(new Alias(regex, element.replace));

          } catch (Exception e) {
            plugin.logger.warn("Cannot parse regex from input string " + element.accept + ": "
                + e.getMessage().replace("\n", " ") + ", ignoring it...");
          }
        });
  }
}

