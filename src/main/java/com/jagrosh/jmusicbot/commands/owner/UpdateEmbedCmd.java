/*
 * Copyright 2017 John Grosh <john.a.grosh@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jagrosh.jmusicbot.commands.owner;

import com.jagrosh.jdautilities.command.CommandEvent;
import com.jagrosh.jmusicbot.Bot;
import com.jagrosh.jmusicbot.commands.OwnerCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.MessageBuilder;

import java.awt.*;

/**
 * @author John Grosh <john.a.grosh@gmail.com>
 */
public class UpdateEmbedCmd extends OwnerCommand {
    public UpdateEmbedCmd(Bot bot) {
        this.name = "update";
        this.help = "posts an update announcement **(BETA)**";
        this.arguments = "<message>";
        this.aliases = bot.getConfig().getAliases(this.name);
        this.guildOnly = true;
    }

    @Override
    protected void execute(CommandEvent event) {
        MessageBuilder builder = new MessageBuilder();
        EmbedBuilder ebuilder = new EmbedBuilder()
                .setColor(Color.red)
                .setTitle("**SirenBot** has updated!")
                .setDescription(event.getArgs());
        event.getChannel().sendMessage(builder.setEmbed(ebuilder.build()).build()).queue();
    }
}
