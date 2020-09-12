package bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

public class Help extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(msg.equalsIgnoreCase("!help")) {
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("TicTacToe Help")
                    .setDescription("`help`\n`ping`\n`play`\n`rules`\n`close`")
                    .build()
            ).queue();
        } else if(msg.equalsIgnoreCase("!help help")) {
            event.getChannel().sendMessage("Help for command **help**:\nShows a list of all commands!\nUsage: `!help`").queue();
        } else if(msg.equalsIgnoreCase("!help ping")) {
            event.getChannel().sendMessage("Help for command **ping**:\nGives you the gateway ping of the bot!\nUsage: `!ping`").queue();
        } else if(msg.equalsIgnoreCase("!help play")) {
            event.getChannel().sendMessage("Help for command **play**:\nStarts a game of TicTacToe with you!\nUsage: `!play`").queue();
        } else if(msg.equalsIgnoreCase("!help rules")) {
            event.getChannel().sendMessage("Help for command **rules**:\nShows you the rules to playing my games and teaches you how to play with me!\nUsage: `!rules`").queue();
        } else if(msg.equalsIgnoreCase("!help close")) {
            event.getChannel().sendMessage("Help for command **close**:\nCloses an open game!\nUsage: `!close`").queue();
        }
    }
}
