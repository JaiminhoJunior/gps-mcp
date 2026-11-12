# Configuração do ambiente de desenvolvimento

## Configuração do JAVA_HOME

### Verificar se o JAVA_HOME está configurado
Para verificar se a variável de ambiente JAVA_HOME está configurada, devemos executar o seguinte comando no terminal:

``` Command Live
echo $JAVA_HOME
```
Esse comando irá mostrar o conteúdo de JAVA_HOME.
Caso nada seja mostrado no terminal, significa que a variável não está configurada. Dessa forma, antes de configura-lá
devemos descobrir onde o **Java** está instalado.
Com o comando abaixo, podemos descobrir onde o **Java** está instalado:

``` Command Line
usr/libexec/java_home
```

Caso o JDK esteja instalado no sistema operacional, a seguinte linha abaixo será apresentada no terminal indicando o caminho para a JDK:

``` Command Line
/Library/Java/JavaVirtualMachines/jdk-25.jdk/Contents/Home
```
### Configurar a variável de ambiente JAVA_HOME
Para configurar a variável de ambiente, primeiramente, precisamos verificar se o arquivo .zshrc existe. Para efetuar essa validação acesse o terminal na pasta do usuário no Mac OS.

Informe o comando abaixo para verificar se existe o arquivo:
``` Comand line
ls -al
```
Na lista retornada com as pastas e o arquivos, verifique se o arquivo .zshrc está presente. Em caso negativo, iremos criá-lo.

### Criando o arquivo .zshrc
Para criar o arquivo, com o terminal aberto na pasta do usuário, informe o seguinte comando:
``` comand line
touch .zshrc
```

Agora, precisamos inseriri as configurações das variáveis de ambiente no arquivo, para isso, iremos abrir o arquivo com o seguinte comando:
``` command line
open .zshrc
```
Dentro do arquivo, iremos inserir os seguintes comandos:
``` Comand line
export JAVA_HOME=$(/usr/libexec/java_home)
export MAVEN_HOME=/Library/Java/Maven/apache-maven-3.9.16/bin
```

Para atualizar o SO com as informações das variáveis de ambiente, devemos utilizar o seguinte comando:
``` Comand line
source .zshrc
```

# Executar os testes

Na linha de comando, insira a seguinte instrução:

``` Command line
./mvnw -Dtest=BoradbandPowerLevelCheckToolTest test
```