package org.palladiosimulator.retriever.gpt.FilePreprocessing;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Stack;

import org.eclipse.emf.common.util.URI;
import org.palladiosimulator.retriever.extraction.blackboard.RetrieverBlackboard;
import org.palladiosimulator.retriever.extraction.discoverers.CsvDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.EcmaScriptDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.JavaDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.JsonDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.PropertiesDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.SqlDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.XmlDiscoverer;
import org.palladiosimulator.retriever.extraction.discoverers.YamlDiscoverer;
import org.palladiosimulator.retriever.extraction.engine.RetrieverConfiguration;
import org.palladiosimulator.retriever.gpt.Helper.FileTypes;
import org.palladiosimulator.retriever.gpt.Helper.MockConfig;

import de.uka.ipd.sdq.workflow.jobs.JobFailedException;
import de.uka.ipd.sdq.workflow.jobs.UserCanceledException;

public class FileDiscoverer {
	private URI inputPath;
	private RetrieverBlackboard retrieverBoard = new RetrieverBlackboard();
	private RetrieverConfiguration retrieverConfiguration = new MockConfig();
	
	public FileDiscoverer(String path) throws JobFailedException, UserCanceledException {
		// Set local input folder
		this.inputPath = URI.createDeviceURI(Paths.get(path).toAbsolutePath().toString());
		retrieverConfiguration.setInputFolder(inputPath);
		// create all possible discoverers
		new JsonDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new YamlDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new JavaDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new CsvDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new EcmaScriptDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new SqlDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new XmlDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
		new PropertiesDiscoverer().create(this.retrieverConfiguration, this.retrieverBoard).execute(null);
	}

	@SuppressWarnings({ "unchecked" })
	public Map<Path, Object> getFile(FileTypes fileType) {
		Map<Path, Object> files;
		switch (fileType) {
		case JSON: {
			files = (Map<Path, Object>) retrieverBoard.getPartition(JsonDiscoverer.DISCOVERER_ID);
			return files;
		}
		case YAML: {
			files = (Map<Path, Object>) retrieverBoard.getPartition(YamlDiscoverer.DISCOVERER_ID);
			return files;
		}
		case JAVA: {
			files = (Map<Path, Object>) retrieverBoard.getPartition(JavaDiscoverer.DISCOVERER_ID);
			return files;
		}
		case CSV: {
			files = (Map<Path, Object>) retrieverBoard.getPartition("org.palladiosimulator.retriever.extraction.discoverers.csv");
			return files;
		}
		case ECMA: {
			files = (Map<Path, Object>) retrieverBoard.getPartition("org.palladiosimulator.retriever.extraction.discoverers.ecmascript");
			return files;
		}
		case SQL: {
			files = (Map<Path, Object>) retrieverBoard.getPartition(SqlDiscoverer.DISCOVERER_ID);
			return files;
		}
		case XML: {
			files = (Map<Path, Object>) retrieverBoard.getPartition(XmlDiscoverer.DISCOVERER_ID);
			return files;
		}
		case PROP: {
			files = (Map<Path, Object>) retrieverBoard.getPartition(PropertiesDiscoverer.DISCOVERER_ID);
			return files;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + fileType);
		}

	}
}
