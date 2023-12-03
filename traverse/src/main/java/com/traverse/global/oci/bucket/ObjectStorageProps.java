package com.traverse.global.oci.bucket;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oci.object-storage")
public record ObjectStorageProps(
	String bucketName,
	String bucketNameSpace

) {
}
