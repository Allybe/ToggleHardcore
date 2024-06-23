package tech.allydoes.togglehardcore.Text;

import org.bukkit.ChatColor;

public class MessageBuilder {
    public static String getMessage(String message, MessageLevel level) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ToggleHardcore | ");

        switch (level) {
            case ERROR:
                stringBuilder.append(ChatColor.RED + "ERROR: ");
                break;
            case WARNING:
                stringBuilder.append(ChatColor.GOLD + "WARNING: ");
                break;
            case INFO:
                stringBuilder.append(ChatColor.WHITE + "INFO: ");
                break;
        }

        stringBuilder.append(message);
        return stringBuilder.toString();
    }

    public enum MessageLevel {
        ERROR,
        WARNING,
        INFO
    }
}
