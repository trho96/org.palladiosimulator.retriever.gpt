package org.palladiosimulator.retriever.gpt;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.palladiosimulator.retriever.gpt.ChatGPTAPI.ChatGPTUtility;
import org.palladiosimulator.retriever.gpt.FilePreprocessing.FileDiscoverer;
import org.palladiosimulator.retriever.gpt.FilePreprocessing.JavaExtractor;
import org.palladiosimulator.retriever.gpt.Helper.FileTypes;

import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;
import net.sourceforge.plantuml.SourceStringReader;

public class Main {
private static final String project = "acmeair";
private static final String apiKey = "";

	public static void main(String[] args) throws JobFailedException, UserCanceledException {
		FileDiscoverer fileDiscoverer = new FileDiscoverer("./data/" + project);
		Map<Path, Object> files = fileDiscoverer.getFile(FileTypes.JAVA);
		ChatGPTUtility chat = new ChatGPTUtility(apiKey);

		JavaExtractor javaExtractor = new JavaExtractor(fileDiscoverer);
		String extractedFeatures = javaExtractor.extractDescription();
		String plantUMLResultString = chat.createComponentDiagram(extractedFeatures);
		System.out.println(plantUMLResultString);
		saveResultWithTimestamp(plantUMLResultString);		
		
//		transformToPng(plantUMLResultString);

	}
//Removed due to dependency problem
//	private static void transformToPng(String plantUMLResultString) {
//		File outputFile = new File("output.png");
//
//		// Generate the UML diagram and write to the output file
//		try (OutputStream png = new FileOutputStream(outputFile)) {
//			SourceStringReader reader = new SourceStringReader(plantUMLResultString);
//			// Writes the first diagram to "png" (OutputStream)
//			String desc = reader.generateImage(png);
//			System.out.println("Diagram generated: " + desc);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	 private static void saveResultWithTimestamp(String result) {
	        // Create a formatter for the timestamp
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
	        String timestamp = LocalDateTime.now().format(formatter);

	        // Generate the file name
	        String fileName = "./data/results/" + project + timestamp + ".txt";
	        Path filePath = Paths.get(fileName);

	        try {
	            // Write the result to the file
	            Files.write(filePath, result.getBytes(), StandardOpenOption.CREATE);
	            System.out.println("Result saved to " + filePath.toAbsolutePath());
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}

