package br.com.code.truck.mcp.gps.tools.resourceinventory;

import java.util.Map;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import br.com.code.truck.mcp.gps.tools.Tool;

@Component
public class ResourceInventoryBlockingCheckTool implements Tool {
    private static final Map<String, String> DATASOURCE = Map.of(
        "+554130252525", """
            [
                {
                    "serviceId": "+554130252525"
                    "resourceId": "RESOURCE_1",
                    "resourceName": "Ligação a três"
                    "status": "Ativo"
                },
                {
                    "serviceId": "+554130252525"
                    "resourceId": "RESOURCE_2",
                    "resourceName": "Identificador de chamadas"
                    "status": "Ativo"
                },
                {
                    "serviceId": "+554130252525"
                    "resourceId": "RESOURCE_3",
                    "resourceName": "Bloqueio total de cobrança"
                    "status": "Inativo"
                }
            ]
        """,
        "BANDALARGA_1", """
            [
                {
                    "serviceId": "BANDALARGA_1"
                    "resourceId": "RESOURCE_3",
                    "resourceName": "Bloqueio total de cobrança"
                    "status": "Inativo"
                }
            ]
        """,
        "+554130252626", """
            [
                {
                    "serviceId": "+554130252626"
                    "resourceId": "RESOURCE_2",
                    "resourceName": "Identificador de chamadas"
                    "status": "Ativo"
                },
                {
                    "serviceId": "+554130252626"
                    "resourceId": "RESOURCE_3",
                    "resourceName": "Bloqueio total de cobrança"
                    "status": "Ativo"
                }
            ]
        """,
        "BANDALARGA_2", """
            [
                {
                    "serviceId": "BANDALARGA_2"
                    "resourceId": "RESOURCE_3",
                    "resourceName": "Bloqueio total de cobrança"
                    "status": "Ativo"
                }
            ]
        """
    );

    @Override
    @McpTool(name = "resource-inventory-blocking-check", description = """
        
        OBJETIVO:
        Executa uma consulta no inventário para verificar se o serviço do cliente possui algum bloqueio.

        QUANDO EXECUTAR:
        Execute esta tool quando o cliente estiver com problemas relacionados aos serviços de telecom
        (Linha fixa, Banda larga e TV).
        Esses problemas podem ser:
        - Sem conexão 
        - Quedas de conexão
        - Conexão lenta
        - Falta de conectividade
        - Linha muda: Ocorre quando há problemas de conexão de banda larga para linhas VOIP.
        - Não completa ou realiza ligações
        - Tela preta
        - Não assiste nenhum canal

        QUANDO NÃO EXECUTAR:
        - NUNCA execute essa tool para um produto ou serviço com status: pendente, inativo, cancelado.
        - NUNCA execute essa tool caso não haja um serviceId.

        RESULTADO:
        Retorna um json no seguinte formato:
        [
            {
                "serviceId": "Id do produto de telecom"
                "resourceId": "Id do recurso no iventário",
                "resourceName": "Nome do recurso no inventário"
                "status": "Situação do recurso (Ativo, Inativo)"
            }
        ]
    """)
    public String execute(@McpToolParam String serviceId) {
        final String resourceInventory = DATASOURCE.get(serviceId);
        return resourceInventory == null ? "[]" : resourceInventory;
    }

}
