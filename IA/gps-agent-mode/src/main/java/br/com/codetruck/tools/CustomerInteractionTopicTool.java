package br.com.codetruck.tools;

import java.util.Map;
import com.google.adk.tools.Annotations.Schema;

public class CustomerInteractionTopicTool {
    private static final Map<String, String> DATASOURCE = Map.of(
        "Banda Larga", """
            [
                { "topic": "Sem navegação" }, 
                { "topic": "Conexão lenta" }, 
                { "topic": "Quedas de conexão" }
            ]
        """,
        "TV", """
            [
                { "topic": "Tela preta" },
                { "topic": "Não assiste canal específico" },
                { "topic": "Não assiste a todos os canais" }
            ]
        """,
        "Linha Fixa", """
            [
                { "topic": "Não completa ligação" },
                { "topic": "Não recebe ligação" },
                { "topic": "Linha muda" },
                { "topic": "Problemas com fatura" }
            ]
        """
    );
    @Schema(description = """
        Busca os motivos de contato pelo tipo de produto.
        Essa tool deve ser utilizanda quando for necessário classificar o atendimento pelo tipo de produto.
        Caso os motivos de contato sejam encontrados, o retorno será em formato JSON:
        [
            { "topic": "Tópico 1" },
            { "topic": "Tópico 2" },
            { "topic": "Tópico 3" },
            { "topic": "Tópico 4" }
        ]
        Caso contrário, uma exceção será lançada: IllegalArgumentException
    """)
    public String getCustomerInteractionTopicValues(
        @Schema(
            name = "productType",
            description = "Tipo do produto informado pelo cliente"
        )
        String productType) {
        final String topics = DATASOURCE.get(productType);
        if (topics == null) {
            throw new IllegalArgumentException(
                String.format("Nenhum motivo de contato foi encontrado para o tipo de produto %s", 
                productType));
        }
        return topics;
    }
}
