package org.build.buddy.file;

public interface BuildFileEditor {
    void addDependency(String groupId, String artifactId, String version) throws Exception;
}
