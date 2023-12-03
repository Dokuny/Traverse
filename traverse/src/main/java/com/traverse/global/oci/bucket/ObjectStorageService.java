package com.traverse.global.oci.bucket;

import com.oracle.bmc.Region;
import com.oracle.bmc.objectstorage.ObjectStorage;
import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
import com.oracle.bmc.objectstorage.responses.PutObjectResponse;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class ObjectStorageService {

	private final ObjectStorageProps props;
	private final ObjectStorage objectStorage;

	@Async("threadPoolTaskExecutor")
	public CompletableFuture<String> uploadImage(MultipartFile file) throws IOException {

		Map<String, String> metadata = new HashMap<>();

		String objectName =
			new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/" + UUID.randomUUID();

		try (InputStream is = file.getInputStream()) {
			PutObjectRequest request = PutObjectRequest.builder()
				.bucketName(props.bucketName())
				.namespaceName(props.bucketNameSpace())
				.objectName(objectName)
				.contentType(file.getContentType())
				.contentLength(file.getSize())
				.opcMeta(metadata)
				.putObjectBody(is)
				.build();

			PutObjectResponse response = objectStorage.putObject(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return CompletableFuture.completedFuture(getObjectUrl(objectName));
	}

	private String getObjectUrl(String objectName) {
		return String.format("https://objectstorage.%s.oraclecloud.com/n/%s/b/%s/o/%s",
			Region.AP_CHUNCHEON_1.getRegionId(),
			props.bucketNameSpace(),
			props.bucketName(),
			objectName);
	}

}
