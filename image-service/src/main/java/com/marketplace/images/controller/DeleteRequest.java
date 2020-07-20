package com.marketplace.images.controller;

import lombok.Data;

import java.util.List;

@Data
class DeleteRequest {
    private String key;
    private List<String> keys;
}
