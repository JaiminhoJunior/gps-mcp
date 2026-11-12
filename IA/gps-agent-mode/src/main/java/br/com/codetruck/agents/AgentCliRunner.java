package br.com.codetruck.agents;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import com.google.adk.agents.RunConfig;
import com.google.adk.runner.InMemoryRunner;
import com.google.adk.sessions.Session;
import com.google.genai.types.Content;
import com.google.genai.types.Part;

import br.com.codetruck.agents.attendant.GeneralAttendantAgent;

import com.google.adk.events.Event;
import io.reactivex.rxjava3.core.Flowable;

public class AgentCliRunner {
    public static void main(String... args) {
        RunConfig runConfig = RunConfig.builder().build();
        InMemoryRunner runner = new InMemoryRunner(GeneralAttendantAgent.AGENT);

        Session session = runner
                .sessionService()
                .createSession(runner.appName(), "domCorleone")
                .blockingGet();

        try (Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8)) {
            while (true) {    
                System.out.print("\nVocê > ");
                String userInput = scanner.nextLine();
                if ("quit".equalsIgnoreCase(userInput)) {
                    break;
                }

                Content userMsg = Content.fromParts(Part.fromText(userInput));
                Flowable<Event> events = runner.runAsync(
                    session.userId(), 
                    session.id(), 
                    userMsg, 
                    runConfig
                );

                System.out.print("\nGPS > ");
                events.blockingForEach(event -> {
                    if (event.finalResponse()) {
                        System.out.println(event.stringifyContent());
                    }
                });
            }
        }
    }
}
