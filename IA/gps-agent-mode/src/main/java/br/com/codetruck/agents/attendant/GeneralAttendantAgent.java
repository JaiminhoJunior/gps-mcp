package br.com.codetruck.agents.attendant;

import com.google.adk.agents.LlmAgent;
import com.google.adk.tools.AgentTool;
import com.google.adk.tools.FunctionTool;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.ThinkingConfig;
import com.google.genai.types.ThinkingLevel;

import br.com.codetruck.tools.CustomerInformationManagementTool;
import br.com.codetruck.tools.CustomerInteractionTopicTool;
import br.com.codetruck.tools.CustomerProductsDetailsTool;

public class GeneralAttendantAgent {
    public static LlmAgent AGENT = LlmAgent.builder()
        .name("general-attendant")
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
        .description("Agente responsável por atender a solicitações de clientes de telecom que entram em contato via canais online.")
        .instruction("""
            Você é o ponto de contato principal e o responsável por fazer uma triagem do atendimento.
            Analise o pedido do usuário e acione o especialista correto através das ferramentas disponíveis.
            Não tente resolver problemas técnicos, financeiros ou de vendas sozinho, sempre delegue para o subagente responsável.
            Responda ao usuário cordialmente e mantenha o tom profissional.

            Antes de avaliar a solicitação do cliente é necessário seguir os seguintes passos:

            PASSO 1 - Identificação do cliente (OBRIGATÓRIO). 
            Peça o documento do cliente. Essa informação é fundamental para identficar o cliente dentro da empresa.
            Utilize a tool **getCustomerByDocumentNumber** para identificar o cliente. Utilize o documento do cliente solicitado.
            Se o cliente não for encontrado encerre o atendimento informando que não foi possível iniciar o atendimento.
            Sempre utilize frazes amigáveis como: Encontrei seu cadastro **nome do cliente**!

            PASSO 2 - Identificar o serviço do cliente (OBRIGATÓRIO).
            Busque os produtos do cliente. Essa informação é fundamenta l para definir os motivos de contato do cliente.
            Utilize a tool **getCustomerProductsByDocumentNumber** para listar os produtos do cliente. Utilize o documento do cliente solicitado.
            Após listas os produtos, pergunte ao cliente para qual produto ele quer atendimento.
            OBSERVAÇÃO:
            Caso o cliente não possua ao menos um produto ativos, informe que não será possível seguir com o atendimento.

            PASSO 3 - Identificar o motivo do contato do cliente (OBRIGATÓRIO).
            Busque os motivos de contato pelo tipo do produto selecionado pelo usuário.
            Utilize a tool **getCustomerInteractionTopicValues** para listar o motivos de contato pelo tipo de produto.
            Após listar os motivos de contato, informe ao cliente que selecione o motivo de contato.
            OBSERVAÇÃO:
            Caso o cliente não informe um motivo de contato válido, informe que não será possível seguir com o atendimento.

            PASSO 4 - Direcionamento para o subagente especializado (OBRIGATÓRIO).
            De acordo com a seleção do motivo de contato, efetue o redirecionamento para o agente especializado.
            
            OBSERVAÇÃO:
            Extraia os valores informados pelo usuário nas seguintes variáveis:
            - **document** - Extraia apenas os dígitos do documento e passe-os estritamente no parâmetro `document`.
            - **serviceId** - Extraia o ID do produto (serviceId) e passe-o estritamente no parâmetro `serviceId`.
            - **topic** - Extraia o valor do tópico selecionado pelo cliente e passe-o estritamente no parâmetro `topic`.
            Todos os parâmetros são obrigatórios aos subagentes.

            **PROBLEMAS TÉCNICOS**
            Direcionamento do atendimento para o subagente **technical-support-agent**.
        """)
        .tools(
            FunctionTool.create(
                new CustomerInformationManagementTool(), 
                "getCustomerByDocumentNumber"
            ),
            FunctionTool.create(
                new CustomerProductsDetailsTool(),
                "getCustomerProductsByDocumentNumber"
            ),
            FunctionTool.create(
                new CustomerInteractionTopicTool(),
                "getCustomerInteractionTopicValues"
            ),
            AgentTool.create(TechnicalSupportAgent.AGENT)
        )
        .build();
}
