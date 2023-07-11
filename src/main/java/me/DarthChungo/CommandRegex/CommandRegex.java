package me.DarthChungo.CommandRegex;

import com.moandjiezana.toml.Toml;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Plugin(
        id = "commandregex",
        name = "CommandRegex",
        version = "1.0.0",
        description = "Alias commands using regular expressions",
        authors = { "DarthChungo" }
)
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
