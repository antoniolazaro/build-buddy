# BuildBuddy

A command-line tool to automatically add dependencies to your `pom.xml` or `build.gradle` file inspired on "npm install" from node world.

## Features

- Search for Maven dependencies by `groupId` and `artifactId`.
- Automatically add dependencies to your project's build file (`pom.xml` or `build.gradle`).
- Supports Maven and Gradle projects.

## Requirements

- Java 21 or higher
- Maven (for building the project)

## Installation

### Automated Installation (Linux)

To install BuildBuddy automatically, run the following command:

```bash
curl -s https://raw.githubusercontent.com/antoniolazaro/build-buddy/main/install-build-buddy.sh | sudo bash
```

This script will:  
* Clone the repository to /usr/local/build-buddy.
* Build the project using mvn clean package.
* Create an executable script in /usr/local/bin/build-buddy.
* After installation, you can use the build-buddy command directly.  

### Manual Installation
1. Clone the repository:  
1. git clone https://github.com/antoniolazaro/build-buddy.git
1. cd build-buddy
1. Build the project:  
1. mvn clean package
1. Create a script to run the CLI as a system command:  
1. Linux/Mac: Create a build-buddy.sh file in the project root with the following content or copy the script *_build-buddy.sh_* from the repository:
```bash 
#!/bin/bash
java -jar "$(dirname "$0")/target/build-buddy-1.0-SNAPSHOT.jar" "$@"
```
1. Make the script executable:  
chmod +x build-buddy.sh
1. Add the script to your system's PATH:  
```bash
sudo ln -s "$(pwd)/build-buddy.sh" /usr/local/bin/build-buddy
```
## Usage
Add a Dependency
To add a dependency to your project, use the add command:

```bash
build-buddy add <groupId> <artifactId> [<version>]
```
### Parameters
1. groupId: The group ID of the dependency.
1. artifactId: The artifact ID of the dependency.
1. version (optional): The version of the dependency. If not provided, the latest version will be used.

### Example
```bash
build-buddy add org.springframework.boot spring-boot-starter-web
```

### Output
1. The CLI will search for the dependency and display a list of available versions.
1. Select the desired version by entering the corresponding number.
1. The dependency will be added to your pom.xml or build.gradle file.

## Contributing

Contributions are welcome! Please follow these steps:  
1. Fork the repository.
1. Create a new branch for your feature or bug fix.
1. Submit a pull request.

## License
This project is licensed under the MIT License. See the LICENSE file for details.
