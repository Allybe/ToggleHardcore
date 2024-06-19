package tech.allydoes.togglehardcore.Text;

public class MessageBuilder {
    public static String getMessage(String message, MessageLevel level) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ToggleHardcore|");

        switch (level) {
            case ERROR:
                stringBuilder.append("§c" + "ERROR: ");
                break;
            case WARNING:
                stringBuilder.append("§6" + "WARNING: ");
                break;
            case INFO:
                stringBuilder.append("§f" + "INFO: ");
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
