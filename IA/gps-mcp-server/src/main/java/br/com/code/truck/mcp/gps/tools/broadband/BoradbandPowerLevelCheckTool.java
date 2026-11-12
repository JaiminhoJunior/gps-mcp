package br.com.code.truck.mcp.gps.tools.broadband;

import java.util.Map;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import br.com.code.truck.mcp.gps.tools.Tool;
import tools.jackson.databind.ObjectMapper;

@Component
public class BoradbandPowerLevelCheckTool implements Tool {
    private static final Map<String, Map<String, String>> DATASOURCE = Map.of(
        "BANDALARGA_1", Map.of(
            "serviceId", "BANDALARGA_1",
            "maxLevel", "1000",
            "minLevel", "700",
            "level", "200",
            "status", "NOK"
        ),
        "BANDALARGA_2", Map.of(
            "serviceId", "BANDALARGA_2",
            "maxLevel", "1000",
            "minLevel", "700",
            "level", "900",
            "status", "OK"
        )
    );

    @Override
    @McpTool(name="broadband-power-level-check-tool", description = """
        
        OBJETIVO:
        Executa uma validação da potência da fibra óptica. Essa validação tem o 
        objetivo de verificar se há alguma fissura ou dobra na friba óptica.
        Dobras ou fissuras podem gerar falta de conexão ou conexão instável.

        QUANDO EXECUTAR:
        Execute esta tool quando o cliente estiver com problemas relacionados 
        a conexão com a internet. Esses problemas podem ser: 
        - Quedas de conexão
        - Conexão lenta
        - Falta de conectividade
        - Linha muda: Ocorre quando há problemas de conexão de banda larga para linhas VOIP.

        QUANDO NÃO EXECUTAR:
        - NUNCA execute essa tool quando a tecnologia da banda larga for diferente de GPON.
        - NUNCA execute essa tool para uma banda larga com status: pendente, inativa, cancelada.
        - NUNCA execute essa tool caso não haja um serviceId.

        RESULTADO:
        Retorna um json no seguinte formato:
        {
            'serviceId': 'CTA-69UHDT34DA-013'
            'maxLevel': 1000,
            'minLevel': 700,
            'level': 900,
            'status': 'OK'
        }
    """)
    public String execute(@McpToolParam String serviceId) {
        Map<String, String> broadbandPowerLevelStatus = DATASOURCE.get(serviceId);

        return new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(broadbandPowerLevelStatus);
    }

}
