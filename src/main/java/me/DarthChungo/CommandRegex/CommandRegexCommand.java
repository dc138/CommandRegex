package me.DarthChungo.CommandRegex;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandSource;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;

public final class CommandRegexCommand {
  public static BrigadierCommand createBrigadierCommand(final CommandRegex plugin) {
    LiteralCommandNode<CommandSource> node = //
        LiteralArgumentBuilder.<CommandSource>literal("commandregex")
            .then(LiteralArgumentBuilder.<CommandSource>literal("version")
                .executes(ctx -> {
                  ctx.getSource()
                      .sendMessage(
                          Component.text("Running commandregex version ").append(
                              Component.text("v" + BuildConstants.version)
                                  .color(NamedTextColor.GOLD)
                                  .decoration(TextDecoration.BOLD, true)));
                  return Command.SINGLE_SUCCESS;
                }))
            .then(LiteralArgumentBuilder.<CommandSource>literal("reload")
                .requires(source -> {
                  return source.hasPermission("commandregex.reload");
                })
                .executes(ctx -> {
                  plugin.reload();

                  ctx.getSource()
                      .sendMessage(Component.text("Reloaded configuration"));

                  return Command.SINGLE_SUCCESS;
                }))
            .build();

    return new BrigadierCommand(node);
  }
}
