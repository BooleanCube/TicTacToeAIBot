package bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class Rules extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(msg.equalsIgnoreCase("!rules")) {
            event.getChannel().sendMessage(new EmbedBuilder()
                    .setTitle("Game Rules!")
                    .setAuthor(event.getAuthor().getName(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                    .setDescription("**How to play Tic Tac Toe with me?**\nTo start a game you can use the `!play` command and type \"yes\" to continue and then start playing. After responding \"yes\" to the `!play` command you will have to enter coordinates separated by a space to play against the AI bot! Coordinates are only accepted in this format: `x y` where *x* and *y* range from 0-2 with `0 0` being the top right corner, `2 2` being the bottom left corner, and `1 1` being the center box! This is how the coordinates look on the actual map: \n```\n 0 0 | 0 1 | 0 2 \n-----------------\n 1 0 | 1 1 | 1 2 \n-----------------\n 2 0 | 2 1 | 2 2 \n```")
                    .setFooter("GLHF!")
                    .build()
            ).queue();
        }
    }
}
