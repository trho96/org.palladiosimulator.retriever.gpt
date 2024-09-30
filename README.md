# Lab: 'ChatGPT-basierte Extraktion von Architekturmodellen'

**Authors:** Hoang Hai Tran, Tim Le Large  
**Course:** 'Praktikum: Werkzeuge für Agile Modellierung'  
**Mentors:** Yves Kirschner, Kai Marquardt

## Prerequisites and Setup

Before you begin setting up the project, please ensure you complete the following steps:

1. **Install Apache Maven 3.8.7**
   - Download and install Apache Maven 3.8.7 from the [official release notes](https://maven.apache.org/docs/3.8.7/release-notes.html).

2. **Install Java 17**
   - Download and install the latest version of Java 17 from [Adoptium](https://adoptium.net/de/temurin/releases/?version=17).

3. **Download and Install Eclipse Modeling Tools Version 2023-03**
   - Navigate to the [Eclipse Modeling Tools download page](https://www.eclipse.org/downloads/packages/release/2023-03/r/eclipse-modeling-tools) and download the installer or archive.
   - Follow the installation instructions to set up Eclipse Modeling Tools.

4. **Install Retriever-Update-Site**
   - Open Eclipse and navigate to `Help` > `Eclipse Marketplace...`.
   - Click on `Install New Software...` and enter the following update site URL: [Palladio Reverse Engineering Retriever Update Site](https://updatesite.palladio-simulator.com/palladio-reverseengineering-retriever-updatesite/nightly/).
   - Follow the prompts to complete the installation of the necessary plugins.

## Project Configuration

Ensure you have the following dependencies in your `pom.xml` file to set up the project:

- **ChatGPT API Library**
  - **Group ID:** `com.github.plexpt`
  - **Artifact ID:** `chatgpt`
  - **Version:** `5.1.1`

- **PlantUML Library**
  - **Group ID:** `net.sourceforge.plantuml`
  - **Artifact ID:** `plantuml`
  - **Version:** `1.2023.7`

## Using the ChatGPT API in Java

To integrate the ChatGPT API into your Java project, follow these steps:

1. **Add Maven Dependency**
   - Ensure the required dependencies are included in your Maven `pom.xml`.

2. **Configure API Key**
   - Obtain an API key from the ChatGPT provider and configure it in your Java application. Refer to the library's documentation for details on how to set up the API key.

3. **Implement ChatGPT Integration**
   - Use the `chatgpt` library to implement ChatGPT functionality in your Java application. Follow the library's documentation to understand how to create prompts, send requests, and handle responses.

## Reverse Engineering with ChatGPT

This project leverages ChatGPT for reverse engineering software architecture models. Here's how ChatGPT can be used in this context:

1. **Generate Architecture Diagrams**
   - By providing a detailed description of the source code (including class descriptions, imports, methods, and fields), ChatGPT can generate component diagrams and other UML diagrams. The generated diagrams help visualize and understand the architecture of the software.

2. **Process Source Code**
   - Integrate source code parsers (e.g., for Java, JSON, YAML) to extract relevant information. This information is then fed into ChatGPT to create meaningful architectural models.

3. **Integration with PlantUML**
   - Use PlantUML to render the diagrams created by ChatGPT. PlantUML is a tool that can interpret textual descriptions of diagrams and generate visual representations.

4. **Customization**
   - Customize the prompts and configurations used with ChatGPT to tailor the generated diagrams to specific requirements or preferences. This flexibility allows for adapting the reverse engineering process to different project needs.

If you have any questions or need further assistance, please feel free to contact us.

**Best regards,**  
Tim and Hai