package com.kt.onnuipay.kafka.kafkanetty.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;

@Configuration
public class UtilConfig {

    @Bean
    public XmlMapper getMapper() {
        
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(false);

        
        XmlMapper xmlMapper = new XmlMapper(module);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);

        return new XmlMapper();
    }
}
