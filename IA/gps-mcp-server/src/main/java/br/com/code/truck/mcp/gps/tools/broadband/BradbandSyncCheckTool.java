package br.com.code.truck.mcp.gps.tools.broadband;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import br.com.code.truck.mcp.gps.tools.Tool;

@Component
public class BradbandSyncCheckTool implements Tool {

    @Override
    @McpTool(name="broadband-sync-check-tool", description = """
        
        OBJETIVO:
        Executa uma validação de sincronismo entre o inventário e a rede. Essa validação
        tem o objetivo de verificar se há divergências entre inventário de rede e o estado real
        dos equipamentos ativos na rua.

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
        [
            {
                "id": "INT-GPON-01-02-05",
                "name": "Interface GPON Slot 2 Porta 5",
                "resourceStatus": "active",
                "@type": "LogicalResource",
                "validFor": {
                "startDateTime": "2026-11-01T00:00:00.000Z"
                },
                "resourceCharacteristic": [
                    {
                        "name": "syncStatus",
                        "value": "SYNCHRONIZED",
                        "valueType": "string"
                    },
                    {
                        "name": "lastDiscoveryTime",
                        "value": "2026-11-09T02:00:00.000Z",
                        "valueType": "dateTime"
                    },
                    {
                        "name": "administrativeState",
                        "value": "unlocked",
                        "valueType": "string"
                    },
                    {
                        "name": "operationalState",
                        "value": "enabled",
                        "valueType": "string"
                    },
                    {
                        "name": "allocatedBandwidth",
                        "value": "1000",
                        "valueType": "integer"
                    }
                ],
                "resourceRelationship": [
                    {
                        "relationshipType": "containedIn",
                        "resource": {
                        "id": "OLT-SPO-CENTRO-01",
                        "href": "https://telecom-operator.com",
                        "name": "OLT Centro SP"
                        }
                    }
                ]
            }
        ]

        OBSERVAÇÃO:
        O status definido como SYNCHRONIZED indica que não há divergência entre o inventário e redes.
    """)
    public String execute(@McpToolParam String serviceId) {
        return String.format("""
        [
            {
                "id": "INT-GPON-01-02-05",
                "name": "Interface GPON Slot 2 Porta 5",
                "resourceStatus": "active",
                "@type": "LogicalResource",
                "validFor": {
                "startDateTime": "2026-11-01T00:00:00.000Z"
                },
                "resourceCharacteristic": [
                    {
                        "name": "syncStatus",
                        "value": "SYNCHRONIZED",
                        "valueType": "string"
                    },
                    {
                        "name": "lastDiscoveryTime",
                        "value": "2026-11-09T02:00:00.000Z",
                        "valueType": "dateTime"
                    },
                    {
                        "name": "administrativeState",
                        "value": "unlocked",
                        "valueType": "string"
                    },
                    {
                        "name": "operationalState",
                        "value": "enabled",
                        "valueType": "string"
                    },
                    {
                        "name": "allocatedBandwidth",
                        "value": "1000",
                        "valueType": "integer"
                    }
                ],
                "resourceRelationship": [
                    {
                        "relationshipType": "containedIn",
                        "resource": {
                        "id": "%s",
                        "href": "https://vivo.com.br",
                        "name": "OLT Curitiba PR"
                        }
                    }
                ]
            }
        ]            
        """, serviceId);
    }

}
