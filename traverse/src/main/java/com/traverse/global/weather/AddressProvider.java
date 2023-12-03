package com.traverse.global.weather;

import com.traverse.global.weather.dto.AddressInfo;
import java.util.concurrent.CompletableFuture;

public interface AddressProvider {

	CompletableFuture<AddressInfo> getAddress(Double lat, Double lng);
}
