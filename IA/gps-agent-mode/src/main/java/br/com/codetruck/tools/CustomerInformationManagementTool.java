package br.com.codetruck.tools;

import java.util.Map;
import com.google.adk.tools.Annotations.Schema;
import tools.jackson.databind.ObjectMapper;

public class CustomerInformationManagementTool {
    private static final Map<String, Map<String, String>> DATASOURCE = Map.of(
        "123456", Map.of(
            "document", "123456",
            "name", "Tony Stark",
            "status", "Ativo"
        ),
        "654321", Map.of(
            "document", "654321",
            "name", "Bruce Wayne",
            "status", "Ativo"
        ),
        "987654", Map.of(
            "document", "987654",
            "name", "Peter Parker",
            "status", "Inativo"
        )
    );

    @Schema(description = """
        Busca as informações do cliente no banco de dados pelo número do documento.
        Essa tool deve ser utilizada quando for necessário encontrar um cliente para início do atendimento.
        Caso client seja encontrado, o retorno será em formato JSON:
        {
            "document": "Número do documento",
            "name": "Nome do cliente",
            "status": "Ativo"
        }
        Caso contrário, uma exceção será lançada: IllegalArgumentException
    """)
    public String getCustomerByDocumentNumber(
        @Schema(
            name = "document",
            description = "O número do documento do cliente contendo apenas dígitos numéricos."
        ) 
        final String document) {
        Map<String, String> customerInformation = DATASOURCE.get(document);
        if (customerInformation == null) {
            throw new IllegalArgumentException(
                String.format("Nenhum cliente foi encontrado para o documento %s", document));
        }
        return new ObjectMapper().writerWithDefaultPrettyPrinter()
            .writeValueAsString(customerInformation);
    }
}
