package br.com.code.truck.mcp.gps.tools.broadband;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.modelcontextprotocol.client.McpClient;
import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport;
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest;
import io.modelcontextprotocol.spec.McpSchema.CallToolResult;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoradbandPowerLevelCheckToolTest {
    
    @LocalServerPort 
    private int port;

    @Test
    public void validateBroadbandPowerCheckTool() {

        final HttpClientStreamableHttpTransport transport = HttpClientStreamableHttpTransport
            .builder("http://localhost:" + this.port)
            .endpoint("/mcp")
            .build();

        McpSyncClient client = McpClient.sync(transport).build();
        client.initialize();

        CallToolRequest request = CallToolRequest
            .builder("broadband-power-level-check-tool")
            .arguments(Map.of("serviceId", "CTA-69UHDT34DB-013"))
            .build();

        CallToolResult result = client.callTool(request);
        assertTrue(result != null, "Tool não executada!");
        System.out.println(result);
    }
}
