package br.com.codetruck.agents;

import br.com.codetruck.agents.attendant.GeneralAttendantAgent;
import com.google.adk.web.AdkWebServer;

public class AgentWebRunner {
    private static final int PORT = 8080;

    public static void main(String... args) {
        System.setProperty("server.port", Integer.toString(PORT));
        AdkWebServer.start(GeneralAttendantAgent.AGENT);
    }
}
