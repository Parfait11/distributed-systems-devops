package enset.dimba.ebankaiagent.discord;


import com.zgamelogic.discord.annotations.DiscordController;
import enset.dimba.ebankaiagent.agents.EbankAiAgent;
import com.zgamelogic.discord.annotations.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@DiscordController
public class EbankDiscordController {
    private EbankAiAgent ebankAiAgent;

    public EbankDiscordController(EbankAiAgent ebankAiAgent){
        this.ebankAiAgent = ebankAiAgent;
    }

    @DiscordMapping()
    private void perform(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        String query = event.getMessage().getContentRaw();
        String response = ebankAiAgent.chat(query);
        event.getChannel().sendMessage(response).queue();
    }
}
