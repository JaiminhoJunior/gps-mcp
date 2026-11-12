# GPS Agent Mode

Exemplo de agente conversacional em Java 25 com Google Agent Development Kit (ADK) e Gemini. O agente consulta a API pública Open-Meteo para responder sobre as condições atuais e a previsão dos próximos três dias, sem exigir uma chave para o serviço meteorológico.

## Pré-requisitos

- JDK 25
- Maven 3.9 ou superior (ou use o Maven Wrapper incluído)
- Uma chave de API do Gemini, disponível no [Google AI Studio](https://aistudio.google.com/apikey)

## Configuração

Configure a chave no terminal. Não a inclua no código-fonte:

```bash
export GOOGLE_API_KEY="sua-chave"
```

O arquivo `.env.example` serve como referência. O Maven não carrega arquivos `.env` automaticamente; se criar um `.env`, exporte a variável no terminal antes de executar.

## Executar

Na pasta `gps-agent-mode`:

```bash
./mvnw compile exec:java -Dexec.mainClass="br.com.code.truck.agent.AgentCliRunner"
```

No Windows, use `mvnw.cmd`. Também é possível substituir `./mvnw` por `mvn`.

Exemplo de pergunta:

```text
Você > Como está o tempo em São Paulo e como fica nos próximos dias?
```

Digite `sair` para encerrar. A primeira execução pode demorar enquanto o Maven baixa as dependências. O agente precisa de acesso à internet para chamar o Gemini e a Open-Meteo.

Para iniciar a interface web do Google ADK com o agente de atendimento:

```bash
./mvnw compile exec:java -Dexec.mainClass="br.com.codetruck.agents.AgentWebRunner"
```

Acesse `http://localhost:8080/dev-ui/` no navegador. A interface utiliza o agente `GeneralAttendantAgent`.

## Testes

```bash
./mvnw test
```

Os testes do serviço meteorológico usam um servidor HTTP local e não precisam de chave Gemini nem de acesso à API Open-Meteo.

```bash
export GOOGLE_API_KEY="SUA CHAVE DO GOOGLE"
./mvnw compile exec:java -Dexec.mainClass="br.com.codetruck.agents.AgentCliRunner"


export GOOGLE_API_KEY="SUA CHAVE DO GOOGLE" 
./mvnw compile exec:java -Dexec.mainClass="br.com.codetruck.agents.AgentWebRunner"
```
