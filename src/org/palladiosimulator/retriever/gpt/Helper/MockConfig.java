package org.palladiosimulator.retriever.gpt.Helper;

import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.retriever.extraction.engine.RetrieverConfiguration;
import org.palladiosimulator.retriever.extraction.engine.Service;
import org.palladiosimulator.retriever.extraction.engine.ServiceConfiguration;

public class MockConfig implements RetrieverConfiguration {
	
	private URI input;

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Service> ServiceConfiguration<T> getConfig(Class<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getInputFolder() {
		return input;
	}

	@Override
	public URI getOutputFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI getRulesFolder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInputFolder(URI arg0) {
		input = arg0;

	}

	@Override
	public void setOutputFolder(URI arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRulesFolder(URI arg0) {
		// TODO Auto-generated method stub

	}

}
