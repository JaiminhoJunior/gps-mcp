package br.com.codetruck.tools;

import java.util.Map;
import com.google.adk.tools.Annotations.Schema;

public class CustomerProductsDetailsTool {
    private static final Map<String, String> DATASOURCE = Map.of(
        "123456", """
            [
                {
                    "type": "Linha Fixa", 
                    "serviceId": "+554130252525", 
                    "status": "Ativo" 
                },
                {
                    "type": "Banda Larga", 
                    "serviceId": "BANDALARGA_1", 
                    "status": "Ativo" 
                },
                {
                    "type": "TV", 
                    "serviceId": "TV_1", 
                    "status": "Ativo" 
                }
            ]
        """,
        "654321", """
            [
                {
                    "type": "Linha Fixa", 
                    "serviceId": "+554130252626", 
                    "status": "Ativo" 
                },
                {
                    "type": "Banda Larga", 
                    "serviceId": "BANDALARGA_2", 
                    "status": "Ativo" 
                }
            ]
        """,
        "987654", """
            [
                {
                    "type": "Linha Fixa", 
                    "serviceId": "+554130252727", 
                    "status": "Cancelada" 
                },
                {
                    "type": "Banda Larga", 
                    "serviceId": "BANDALARGA_3", 
                    "status": "Cancelada" 
                },
                {
                    "type": "TV", 
                    "serviceId": "TV_3", 
                    "status": "Cancelada" 
                }
            ]
        """
    );

    @Schema(description = """
        Busca os produtos do cliente no banco de dados pelo número do documento.
        Essa tool deve ser utilizada quando for necessário encontrar os produtos do cliente para início do atendimento.
        Caso os produtos sejam encontrados, o retorno será em formato JSON:
        [
            {
                "type": "Tipo", 
                "serviceId": "Identificador único", 
                "status": "Situação do produto" 
            }
        ]
        Caso contrário, uma exceção será lançada: IllegalArgumentException
    """)
    public String getCustomerProductsByDocumentNumber(
        @Schema(
            name = "document",
            description = "O número do documento do cliente contendo apenas dígitos numéricos."
        )
        String document) {
        String products = DATASOURCE.get(document);
        if (products == null) {
            throw new IllegalArgumentException(
                String.format("Nenhum produto foi encontrado para o documento %s", document));
        }
        return products;
    }
}
