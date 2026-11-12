package br.com.codetruck.agents.attendant;

import java.util.Map;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.mcp.McpToolset;
import com.google.adk.tools.mcp.StreamableHttpServerParameters;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.ThinkingConfig;
import com.google.genai.types.ThinkingLevel;

public class TechnicalSupportAgent {
    public static LlmAgent AGENT = LlmAgent.builder()
        .name("technical-support-agent")
        .model("gemini-flash-latest")
        .generateContentConfig(
            GenerateContentConfig.builder()
                .thinkingConfig(
                    ThinkingConfig.builder()
                        .thinkingLevel(ThinkingLevel.Known.LOW)
                        .build()
            )
            .build()
        )
        .description("""
            Agente responsável por atender a solicitações de problemas técnicos relacionados aos serviços do 
            cliente como: Banda larga, Linha fixa e TV
        """)
        .instruction("""
            VARIÁVEIS DE ENTRADA:
            - document
            - serviceId
            - topic

            VALIDAÇÕES INICIAIS (OBRIGATÓRIO).
            - Caso o parâmetro **document** não tenha sido recebido. Informe que não será possível continuar o atendimento.
            - Caso o parâmetro **serviceId** não tenha sido recebido. Informe que não será possível continuar o atendimento.
            - Caso o parâmetro **topic** não tenha sido recebido. Informe que não será possível continuar o atendimento.

            PASSO 1 - Checagem de bloqueios (OBRIGATÓRIO)
            Independente do **topic** informado, devemos efetuar a seguinte validação por meio de MCP Tool:
            - Utilize o MCP Tool **resource-inventory-blocking-check** para verificar se algum produto do cliente possui 
            algum bloqueio. Caso haja a existência de bloqueios de cobrança com status ativo, não é necessário continuar 
            com as demais validações téncicas. Informe ao cliente que ele deverá entrar em contato com o Call Center pelo 
            número XPTO e encerre o atendimento. Caso não haja blqueios ativos, continue com os demais passos.
            
            PASSO 2 - Problemas com banda larga.
            Caso o **topic** esteja relacionado a problemas de banda larga, devemos efetuar as seguintes validações
            por meio de MCP Tool:
            - Utilize o MCP Tool **broadband-power-level-check-tool** para validar a potência da fibra óptica.
            - Utilize o MCP Tool **broadband-sync-check-tool** para validar o sincronismo entre inventário de redes e equipamentos.
            Caso os parâmetros de medição não estejam dentro dos padrões, informa ao cliente que será necessário
            mandar um técnico até a casa do cliente para efetuar o reparo.
        """)
        .tools(
            new McpToolset(
                StreamableHttpServerParameters
                .builder()
                .headers(Map.of(
                    "Authorization", "Bearer 123456"
                ))
                .url("http://localhost:8081/mcp")
                .build()
            )
        )
        .build();
}
