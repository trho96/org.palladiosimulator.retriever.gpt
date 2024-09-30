package org.palladiosimulator.retriever.gpt.ChatGPTAPI;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.plexpt.chatgpt.ChatGPT;
import com.plexpt.chatgpt.entity.chat.ChatCompletion;
import com.plexpt.chatgpt.entity.chat.ChatCompletionResponse;
import com.plexpt.chatgpt.entity.chat.Message;

public class ChatGPTUtility {
	private ChatGPT chatGPT;

	public ChatGPTUtility(String apiKey) {
		this.chatGPT = ChatGPT.builder().apiKey(apiKey).build().init();
	}

	public String createComponentDiagram(String classDescription) {
		System.out.println("Amount tokens for request: " + countTokens(classDescription));
		int RESPONSE_LENGTH = 2500;
		double TEMPERATURE = 0.3;
		System.out.println("Max length for response: " + RESPONSE_LENGTH);
		System.out.println("Model: " + ChatCompletion.Model.GPT4o);
		String prompt = buildPrompt();
		System.out.println("Final Prompt: \n" + "**************************" + "\n" + prompt + "\n"
				+ "**************************");
		Message finalQuestion = Message.of(prompt + classDescription);
		System.out.println("Waiting for response...");
		ChatCompletion request = ChatCompletion.builder().model(ChatCompletion.Model.GPT4o)
				.messages(List.of(finalQuestion)).maxTokens(RESPONSE_LENGTH).temperature(TEMPERATURE).build();
		ChatCompletionResponse response = chatGPT.chatCompletion(request);
		Message answer = response.getChoices().get(0).getMessage();
		
		String fullCommunication = answer.getContent() + "Prompt:" + prompt + "extracted Features:" + classDescription;
		return fullCommunication;
	}

	public String buildPrompt() {
		return
		// Init the prompt with a specific Role
		"You are going to pretend to be a software architect who’s job is to reverse engineer projects in order to create diagrams of the software architecture. Don’t pay too much attention to implementation details but try to model the most important components. \n"
				+ "\n"
				// How to create an UML Diagram
				+ "Steps to Create a Component Diagram\n" + "Identify Components:\n" + "\n"
				+ "Group related classes into logical components. For example, classes related to customer management can be part of a CustomerService component.\n"
				+ "\n" + "Define Interfaces:\n"
				+ "Identify the methods that act as interfaces for each component. These are the methods that other components will interact with.\n"
				+ "\n" + "Determine Dependencies:\n"
				+ "Analyze the imports section and fields to identify dependencies between components. \n" + "\n"
				+ "Organize Components:\n"
				+ "Organize these components logically in the diagram, showing how they interact with each other using arrows to represent dependencies. Dont show methods in the diagram."
				+ "\n" + "\n"
				// Task description
				+ "Can you generate a component diagram using the following information including class descriptions (imports, extends, implements, package, methods, parameters)"
				+ " based on a spring boot project. 	"
				// Structure of the class description "
				+ "The sourcecode information is structured like this:\n" + "Class:<Classname>\n"
				+ "implements:<Classnames>\n" + "extends:<Classnames>\n" + "Package:<Packagepath>\n"
				+ "imports:<Imports>\n" + "Modifiers:<public/private/protected>\n" + "Field:<Fieldname: Type>\n" + "…\n"
				+ "Method:<Method(parameters): Returntype> \n"
				+"Try to aggregate the different components to bigger components if possible, so we end up with few main components."
				+ "Also use Interfaces as communication points between components. Also Include the relations Requires and Provides for the interfaces and uses. \n"
				+ "Only return the plant uml information without using additional descriptions." + "\n";
	}

	public static int countTokens(String input) {
		Pattern pattern = Pattern.compile("\\w+|\\p{Punct}");
		Matcher matcher = pattern.matcher(input);

		int count = 0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}

}
