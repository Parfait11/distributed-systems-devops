package enset.dimba.ebankaiagent.rest;

import enset.dimba.ebankaiagent.agents.EbankAiAgent;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EbankAgentRestController {

    private final EbankAiAgent ebankAiAgent;

    public EbankAgentRestController(EbankAiAgent ebankAiAgent){
        this.ebankAiAgent = ebankAiAgent;
    }

    @GetMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
    public String chat(@RequestParam(name = "query")String query){
        return ebankAiAgent.chat(query);
    }
}
