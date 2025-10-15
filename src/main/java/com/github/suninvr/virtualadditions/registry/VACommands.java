package com.github.suninvr.virtualadditions.registry;

import com.github.suninvr.virtualadditions.command.GildCommand;

public class VACommands {


    static {
        GildCommand.register();
    }

    public static void init(){}
}
