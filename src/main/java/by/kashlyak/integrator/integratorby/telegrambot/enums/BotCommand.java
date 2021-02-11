package by.kashlyak.integrator.integratorby.telegrambot.enums;

public enum BotCommand {
    START("/start"),
    HELP("/help"),
    SETTING("/settings");
    String command;
    public String getCommand(){
        return command;
    }
    BotCommand(String command) {
        this.command = command;
    }
}
