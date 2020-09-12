package bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class Player extends ListenerAdapter {
    public static HashMap<Long, Board> memberToBoard = new HashMap<>();
    public static HashMap<Long, Message> memberToMessage = new HashMap<>();
    public static HashMap<Long, TextChannel> memberToChannel = new HashMap<>();
    public static HashMap<Long, Guild> memberToGuild = new HashMap<>();
    public static ArrayList<Long> usedPlayCommmand = new ArrayList<>();

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        String msg = event.getMessage().getContentRaw();
        if(msg.equalsIgnoreCase("!play")) {
            event.getChannel().sendMessage("You have requested for a game! But before we start, if this is your first time or you want to know the rules, please take the time to view the rules first by simply using the `!rules` command before we begin our game!\nAre you ready to begin? **(yes/no)**").queue();
            usedPlayCommmand.add(event.getMember().getIdLong());
        }
        if(!usedPlayCommmand.contains(event.getMember().getIdLong()) || msg.equalsIgnoreCase("!play")) {
            return;
        }
        if(msg.equalsIgnoreCase("yes")) {
            event.getChannel().sendMessage("GLHF!").queue(mseg -> {
                mseg.delete().queueAfter(2, TimeUnit.SECONDS);
            });
            Board b = new Board();
            b.place(AI.getBestMove(b), Board.CellState.X);
            MessageEmbed board = new EmbedBuilder()
                    .setAuthor(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                    .setTitle("Tic Tac Toe Game")
                    .setDescription("```\n" + b + "```")
                    .build();
            event.getChannel().sendMessage(board).queue(m -> {
                memberToMessage.put(event.getMember().getIdLong(), m);
            });
            memberToBoard.put(event.getMember().getIdLong(), b);
            memberToChannel.put(event.getMember().getIdLong(), event.getChannel());
            memberToGuild.put(event.getMember().getIdLong(), event.getGuild());
            usedPlayCommmand.remove(event.getMember().getIdLong());
        } else if(msg.equalsIgnoreCase("no")) {
            event.getChannel().sendMessage("Closing our game.. Maybe next time?").queue(mseg -> {
                mseg.delete().queueAfter(5, TimeUnit.SECONDS);
            });
            usedPlayCommmand.remove(event.getMember().getIdLong());
        } else if(msg.equalsIgnoreCase("!close")) {
            event.getChannel().sendMessage("Closing our game.. Maybe next time?").queue(mseg -> {
                mseg.delete().queueAfter(5, TimeUnit.SECONDS);
            });
            usedPlayCommmand.remove(event.getMember().getIdLong());
            memberToChannel.remove(event.getMember().getIdLong());
            memberToGuild.remove(event.getMember().getIdLong());
            memberToMessage.remove(event.getMember().getIdLong());
            memberToBoard.remove(event.getMember().getIdLong());
        } else if(!msg.equalsIgnoreCase("!rules")) {
            event.getChannel().sendMessage("That was an unexpected response! Please try the `!play` command again!").queue();
            usedPlayCommmand.remove(event.getMember().getIdLong());
        }
    }
}
