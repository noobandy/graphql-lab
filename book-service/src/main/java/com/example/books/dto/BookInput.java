package com.example.books.dto;

import java.util.List;
import java.util.Map;

public record BookInput(String title, String desc, List<Long> categoryIds, List<Long> authorIds,
                        Map<String, String> metadata) { }
