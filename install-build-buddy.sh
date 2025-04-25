#!/bin/bash

# Variables
REPO_URL="https://github.com/antoniolazaro/build-buddy.git"
INSTALL_DIR="/usr/local/build-buddy"
BIN_PATH="/usr/local/bin/build-buddy"

# Clone the repository
echo "Cloning repository..."
git clone "$REPO_URL" "$INSTALL_DIR" || { echo "Failed to clone repository."; exit 1; }

# Navigate to the project directory
cd "$INSTALL_DIR" || { echo "Failed to navigate to project directory."; exit 1; }

# Build the project
echo "Building the project..."
mvn clean package || { echo "Build failed."; exit 1; }

# Create the executable script
echo "Creating executable script..."
cat <<EOF >"$BIN_PATH"
#!/bin/bash
java -jar "$INSTALL_DIR/target/build-buddy-1.0-SNAPSHOT.jar" "\$@"
EOF

# Make the script executable
chmod +x "$BIN_PATH"

echo "Installation complete. You can now use 'build-buddy' as a command."