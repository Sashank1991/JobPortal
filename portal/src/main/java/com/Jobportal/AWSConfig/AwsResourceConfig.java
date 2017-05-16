package com.Jobportal.AWSConfig;

import org.springframework.cloud.aws.jdbc.config.annotation.EnableRdsInstance;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/aws-config.xml")
@EnableRdsInstance(databaseName = "${database-name:275proj1}",
                   dbInstanceIdentifier = "${db-instance-identifier:fileupload}", 
				   password = "${rdsPassword:password}")
public class AwsResourceConfig {

}