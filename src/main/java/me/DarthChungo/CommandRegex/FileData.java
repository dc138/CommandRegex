package me.DarthChungo.CommandRegex;

import java.util.List;

public class FileData {
  public static class AliasEntry {
    public String accept;
    public String replace;
  }

  public List<AliasEntry> aliases;
  public List<String> register;
}
