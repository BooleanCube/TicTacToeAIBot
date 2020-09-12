package bot;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException, InterruptedException {
        JDA Bot = JDABuilder.createDefault(Secrets.TOKEN)
                .setChunkingFilter(ChunkingFilter.ALL)
                .setMemberCachePolicy(MemberCachePolicy.ALL)
                .enableIntents(GatewayIntent.GUILD_MEMBERS)
                .setActivity(Activity.playing("Tic Tac Toe! | !help"))
                .addEventListeners(new Help())
                .addEventListeners(new Ping())
                .addEventListeners(new PlayMarker())
                .addEventListeners(new Player())
                .addEventListeners(new Rules())
                .build().awaitReady();
    }
}
