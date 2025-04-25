package org.build.buddy.commandlinner;

import org.apache.commons.lang3.StringUtils;
import org.build.buddy.file.BuildFileStrategy;
import org.build.buddy.repository.MavenRepositoryParser;
import picocli.CommandLine;

import java.util.Scanner;

@CommandLine.Command(name = "spring-dep-cli", mixinStandardHelpOptions = true, version = "1.0",
        description = "Adiciona dependências ao seu pom.xml automaticamente.")
public final class CommandLinerInteractor implements Runnable  {

    @Override
    public void run() {
        System.out.println("Use um comando válido. Exemplo: spring-dep-cli add spring-boot-starter-security");
    }

    @CommandLine.Command(name = "add", description = "Adiciona uma dependência ao projeto")
    public static void add(
            @CommandLine.Parameters(paramLabel = "<nome>", description = "Nome do groupId a buscar") String dependecyOwner,
            @CommandLine.Parameters(paramLabel = "<nome>", description = "Nome da dependência a buscar") String dependencyName,
            @CommandLine.Parameters(paramLabel = "<nome>", description = "Nome da dependência a buscar", defaultValue = "") String dependecyVersion) throws Exception {
        System.out.println("🔍 Buscando dependência: " + dependencyName);

        var docs = MavenRepositoryParser.generateInputFromResponse(dependecyOwner, dependencyName);

        int choice = readOptionFromUser();
        String selectedDoc = docs[choice - 1];

        String groupId = MavenRepositoryParser.extract(selectedDoc, "g");
        String artifactId = MavenRepositoryParser.extract(selectedDoc, "a");

        String version = dependecyVersion;
        if(StringUtils.isEmpty(version)) {
            version = MavenRepositoryParser.extract(selectedDoc, "latestVersion");
        }
        System.out.printf("➡️ Adicionando %s:%s:%s ao pom.xml%n", groupId, artifactId, version);

        BuildFileStrategy.addDependency(groupId, artifactId, version);

        System.out.println("✅ Dependência adicionada com sucesso!");
    }

    private static int readOptionFromUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Selecione o número da dependência para adicionar: ");
        return scanner.nextInt();
    }
}

