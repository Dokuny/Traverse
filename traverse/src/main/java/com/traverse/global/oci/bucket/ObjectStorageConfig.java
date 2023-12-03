package com.traverse.global.oci.bucket;

import com.oracle.bmc.ConfigFileReader;
import com.oracle.bmc.ConfigFileReader.ConfigFile;
import com.oracle.bmc.Region;
import com.oracle.bmc.auth.AuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
import com.oracle.bmc.auth.SimplePrivateKeySupplier;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.ObjectStorageClient;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.function.Supplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class ObjectStorageConfig {


	@Bean
	public AuthenticationDetailsProvider authenticationDetailsProvider() throws IOException {
		ClassLoader classLoader = ObjectStorageConfig.class.getClassLoader();



		try (InputStream configStream = classLoader.getResourceAsStream("config/config"); InputStream privateKeyStream = classLoader.getResourceAsStream("config/dokuny.pem");){

			ConfigFile config = ConfigFileReader.parse(configStream, "DEFAULT");

			File privateKey = File.createTempFile("dokuny", ".pem");
			privateKey.deleteOnExit();

			try (FileOutputStream outputStream = new FileOutputStream(privateKey)) {
				int read;
				byte[] bytes = new byte[1024];

				while ((read = privateKeyStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			}

			Supplier<InputStream> privateKeySupplier = new SimplePrivateKeySupplier(privateKey.getPath());

			return SimpleAuthenticationDetailsProvider.builder()
				.tenantId(config.get("tenancy")).userId(config.get("user")).fingerprint(config.get("fingerprint"))
				.privateKeySupplier(privateKeySupplier).region(Region.AP_CHUNCHEON_1).build();
		}
	}

	@Bean
	public ObjectStorage objectStorage(AuthenticationDetailsProvider authenticationDetailsProvider){
		return ObjectStorageClient.builder()
			.region(Region.AP_CHUNCHEON_1)
			.build(authenticationDetailsProvider);
	}


}
