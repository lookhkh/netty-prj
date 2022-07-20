package util;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.kt.onnuripay.message.common.config.vo.XroshotParameter;
import com.kt.onnuripay.message.kafka.parser.XMLParser;

public class XroshotTestUtil {

	public static XroshotParameter param = new XroshotParameter(
			"new1234", 
			"new1234", 
			"http://info.xroshot.com/catalogs/MAS/recommended/0", 
			"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", 
			"119.205.196.240", 
			"2", 
			"saupblkch1213!",
			"2", 
			"1.0.15"
			);
	
	public static XMLParser getParser() {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		XmlMapper xmlMapper = new XmlMapper(module);
		xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
		
		return new XMLParser(xmlMapper);
	}
}
