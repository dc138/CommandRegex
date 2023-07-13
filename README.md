# CommandRegex

Simple velocity proxy plugin that allows vim-like search and replace functionality on player sent commands through regex.
For example, you might want to allow players to use a `/lobby` command instead of having to type the longer, `/server lobby` command.

**Note:** because this plugin modifies commands sent by the user, it is **NOT** compatible with chat-signing, as chat packets are modified in between client and server.
For this reason, it **requires** that downstream servers have `NoChatReports`, or a similar mod, installed.

## Usage

Download the latest release from the releases tab, and place it in the `plugins/` folder, and then installed the required dependencies:

### Proxy-side dependencies

1. [UnSignedVelocity](https://modrinth.com/plugin/unsignedvelocity): removes signed information from commands and messages sent by clients at the proxy level: allows us to modify player commands.
2. [VPacketEvents](https://modrinth.com/plugin/vpacketevents): UnSignedVelocity dependency.

### Server-side dependencies

1. [NoChatReports](https://modrinth.com/mod/no-chat-reports/): Allows the server to receive non-signed and invalid messages. Only server installation required, no need to also use it in clients.

Start the proxy, and edit the `commandregex/config.toml` file created under the `plugins/` directory.

## Configuration

Sample config file:

```toml
aliases = [
    { accept = "^gmc$", replace = "gamemode creative" }
]
```

The `accept` field represents the regex that must be matched for a certain command to be replaced.
Do **NOT** include the leading `/` character here.
You must use the `^` and `$` beginning and end of string to match the command, unless you want to replace text in the middle of commands.
You may use capture groups in the `accept` regex, which you may then use in the `replace` field by using `\n` where `n` is the capture group number, similar to vim's `:s` command.
For example:

```toml
aliases = [
    { accept = "^go (\w*)$", replace = "server \1" }
]
```

may be used to alias the `server` command to a similar, `go` commands, that captures the first argument, and then uses it to send the player to that server.
Note that this plugin does **not** handle command permissions: it only changes player-sent commands in-place.
This plugin effectively works as an advanced aliaser, leaving commands permissions to the downstream receivers: the proxy in the case of proxy commands, or the server otherwise.

## License

This repository is released under the `MIT` license:

```
CommandRegex, simple velocity proxy plugin that allows vim-like search and replace functionality on player sent commands through regex.
Copyright © 2023 Antonio de Haro

Permission is hereby granted, free of charge, to any person obtaining
a copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included
in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
```

