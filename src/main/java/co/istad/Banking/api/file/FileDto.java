package co.istad.Banking.api.file;

import lombok.Builder;

@Builder

public record FileDto(String name,
                      String url,
                      String extension,
                      long size) {








}
