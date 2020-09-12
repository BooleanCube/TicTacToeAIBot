package bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;

import java.util.concurrent.TimeUnit;

import static bot.AI.*;

public class PlayMarker extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if(Player.memberToBoard.containsKey(event.getMember().getIdLong()) && Player.memberToGuild.get(event.getMember().getIdLong()).getIdLong() == event.getGuild().getIdLong() && Player.memberToChannel.get(event.getMember().getIdLong()).getIdLong() == event.getChannel().getIdLong()) {
            String msg = event.getMessage().getContentRaw();
            if(msg.equalsIgnoreCase("!close")) {
                event.getChannel().sendMessage("Closing our game.. Maybe next time?").queue(mseg -> {
                    mseg.delete().queueAfter(5, TimeUnit.SECONDS);
                });
                Player.usedPlayCommmand.remove(event.getMember().getIdLong());
                Player.memberToChannel.remove(event.getMember().getIdLong());
                Player.memberToGuild.remove(event.getMember().getIdLong());
                Player.memberToMessage.remove(event.getMember().getIdLong());
                Player.memberToBoard.remove(event.getMember().getIdLong());
                return;
            }
            Board board = Player.memberToBoard.get(event.getMember().getIdLong());
            Message toEdit = Player.memberToMessage.get(event.getMember().getIdLong());
            if(msg.length() == 3 && msg.substring(1, 2).equalsIgnoreCase(" ")) {
                event.getMessage().delete().queue();
                Integer[] pos = new Integer[2];
                try {
                    pos[0] = Integer.parseInt(msg.split(" ")[0]);
                    pos[1] = Integer.parseInt(msg.split(" ")[1]);
                } catch(NumberFormatException nfe) {
                    event.getChannel().sendMessage("Invalid Input! Please enter numbers in this format -> `x y`! For Example:\n`0 1` would place it in the top row, and the middle column!").queue(m -> {
                        m.delete().queueAfter(3, TimeUnit.SECONDS);
                    });
                    return;
                }
                if(pos[0]>2 || pos[0]<0 || pos[1]>2 || pos[1]<0) {
                    event.getChannel().sendMessage("Invalid Input! Those coordinates do not exist in the present board!").queue(m -> {
                        m.delete().queueAfter(3, TimeUnit.SECONDS);
                    });
                    return;
                }
                if(board.getBoard()[pos[0]][pos[1]] != Board.CellState.BLANK) {
                    event.getChannel().sendMessage("Invalid Input! There is a seed placed in that cell already!").queue(m -> {
                        m.delete().queueAfter(3, TimeUnit.SECONDS);
                    });
                    return;
                }
                board.place(pos, Board.CellState.O);
                if (board.checkWon() != null) {
                    toEdit.editMessage(new EmbedBuilder()
                            .setAuthor(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                            .setTitle("Tic Tac Toe Game")
                            .setDescription("```\n" + board + "```")
                            .build()
                    ).queue();
                    if (board.checkWon() == ai) {
                        event.getChannel().sendMessage("I WON!").queue();
                    } else if (board.checkWon() == human) {
                        event.getChannel().sendMessage("YOU WON!").queue();
                    } else {
                        event.getChannel().sendMessage("TIE!").queue();
                    }
                    Player.memberToChannel.remove(event.getMember().getIdLong());
                    Player.memberToGuild.remove(event.getMember().getIdLong());
                    Player.memberToMessage.remove(event.getMember().getIdLong());
                    Player.memberToBoard.remove(event.getMember().getIdLong());
                    return;
                }
                board.place(getBestMove(board), Board.CellState.X);
                if (board.checkWon() != null) {
                    toEdit.editMessage(new EmbedBuilder()
                            .setAuthor(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                            .setTitle("Tic Tac Toe Game")
                            .setDescription("```\n" + board + "```")
                            .build()
                    ).queue();
                    if (board.checkWon() == ai) {
                        event.getChannel().sendMessage("I WON!").queue();
                    } else if (board.checkWon() == human) {
                        event.getChannel().sendMessage("YOU WON?!").queue();
                    } else {
                        event.getChannel().sendMessage("TIE!").queue();
                    }
                    Player.memberToChannel.remove(event.getMember().getIdLong());
                    Player.memberToGuild.remove(event.getMember().getIdLong());
                    Player.memberToMessage.remove(event.getMember().getIdLong());
                    Player.memberToBoard.remove(event.getMember().getIdLong());
                    return;
                }
                toEdit.editMessage(new EmbedBuilder()
                        .setAuthor(event.getAuthor().getAsTag(), event.getAuthor().getAvatarUrl(), event.getAuthor().getEffectiveAvatarUrl())
                        .setTitle("Tic Tac Toe Game")
                        .setDescription("```\n" + board + "```")
                        .build()
                ).queue();
            } else {
                event.getChannel().sendMessage("Invalid Input! Please enter numbers in this format -> `x y`! For Example:\n`0 1` would place it in the top row, and the middle column!").queue();
                return;
            }
        }
    }
}
