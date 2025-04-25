package org.build.buddy.file.maven;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public final class MavenEditor {

    public static void addDependency(String groupId, String artifactId, String version) throws Exception {
        File pomFile = createFileReference();
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(pomFile);
        Element root = doc.getRootElement();
        Element dependencies = findDependenciesNode(root);
        createDependencyEntry(groupId, artifactId, version, root.getNamespace(), dependencies);
        writeFinalPom(pomFile, doc);
        System.out.println("pom.xml atualizado com sucesso!");
    }

    private static void writeFinalPom(File pomFile, Document doc) throws IOException {
        Format format = Format.getPrettyFormat();
        format.setIndent("  ");
        format.setEncoding("UTF-8");
        XMLOutputter xmlOutputter = new XMLOutputter(format);

        try (FileWriter writer = new FileWriter(pomFile)) {
            xmlOutputter.output(doc, writer);
        }
    }

    private static void createDependencyEntry(String groupId, String artifactId, String version, Namespace namespace, Element dependencies) {
        Element dependency = new Element("dependency", namespace);
        dependency.addContent(new Element("groupId", namespace).setText(groupId));
        dependency.addContent(new Element("artifactId", namespace).setText(artifactId));
        dependency.addContent(new Element("version", namespace).setText(version));
        dependencies.addContent(dependency);
    }

    private static Element findDependenciesNode(Element root) {
        Element dependencies = root.getChild("dependencies", root.getNamespace());
        if (dependencies == null) {
            dependencies = new Element("dependencies", root.getNamespace());
            root.addContent(dependencies);
        }
        return dependencies;
    }

    private static File createFileReference() {
        File pomFile = new File("pom.xml");
        if (!pomFile.exists()) {
            throw new RuntimeException("pom.xml não encontrado.");
        }
        return pomFile;
    }
}
