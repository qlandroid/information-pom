package org.mybatis.generator;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.exception.XMLParserException;
import org.mybatis.generator.internal.DefaultShellCallback;
import sun.font.TrueTypeFont;

public class MybatisGeneratorMain {
	public static void main(String[] args) throws IOException, XMLParserException, SQLException, 
	InterruptedException, InvalidConfigurationException {

		List<String> warnings = new ArrayList<>();
		boolean overwrite = true;

		URL resource = MyBatisGenerator.class.getResource("/mybatis/generatorConfig.xml");
		File configFile = new File(resource.getFile());
		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
		myBatisGenerator.generate(null);
		System.out.print("生成完成");
	}

}
