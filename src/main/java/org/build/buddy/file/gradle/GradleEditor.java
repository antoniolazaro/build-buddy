package org.build.buddy.file.gradle;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public final class GradleEditor {

    public static void addDependency(String groupId, String artifactId, String version) throws Exception {
        File gradleFile = new File("build.gradle");
        if (!gradleFile.exists()) {
            throw new FileNotFoundException("build.gradle não encontrado.");
        }

        List<String> lines = Files.readAllLines(gradleFile.toPath());
        List<String> newLines = new ArrayList<>();
        boolean inDependencies = false;
        boolean added = false;

        String depToAdd = "    implementation '%s:%s:%s'".formatted(groupId, artifactId, version);

        for (String line : lines) {
            newLines.add(line);

            // Identifica início do bloco dependencies
            if (line.trim().startsWith("dependencies")) {
                inDependencies = true;
                continue;
            }

            // Adiciona a dependência no final do bloco
            if (inDependencies && line.trim().equals("}")) {
                if (!added) {
                    newLines.add(depToAdd);
                    added = true;
                }
                inDependencies = false;
            }
        }

        // Se não encontrou o bloco, adiciona no final
        if (!added) {
            newLines.add("");
            newLines.add("dependencies {");
            newLines.add(depToAdd);
            newLines.add("}");
        }

        // Escreve o arquivo de volta
        Files.write(gradleFile.toPath(), newLines);
        System.out.println("Dependência adicionada com sucesso ao build.gradle");
    }
}
